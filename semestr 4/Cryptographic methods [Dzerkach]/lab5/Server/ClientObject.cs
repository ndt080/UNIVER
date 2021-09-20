using System;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Numerics;
using System.Security.Cryptography;
using System.Text;

namespace Server
{
    public class ClientObject
    {
        public string Id { get; set; }
        public string PublicKey { get; }
        private string PrivateKey;
        private string RndNumber { get; }

        public NetworkStream Stream { get; set; }
        private readonly TcpClient _client;
        private readonly ServerObject _server;
        private static UnicodeEncoding _encoder;
        private static Random _rnd;

        public ClientObject(TcpClient tcpClient, ServerObject serverObject)
        {
            var rsa = new RSACryptoServiceProvider();
            _rnd = new Random();
            _encoder = new UnicodeEncoding();

            PrivateKey = rsa.ToXmlString(true);
            PublicKey = rsa.ToXmlString(false);

            Id = Guid.NewGuid().ToString();
            RndNumber = _rnd.Next().ToString();

            _client = tcpClient;
            _server = serverObject;
            serverObject.AddConnection(this);
        }

        public void Process()
        {
            try
            {
                Stream = _client.GetStream();

                var message = GetMessage();
                var clientId = message;
                
                if (!_server.Authenticate(this.Id, clientId))
                {
                    Console.WriteLine("Authentication failed");
                    _server.RemoveConnection(this.Id);
                    Close();
                    Environment.Exit(0);
                }
                
                if(clientId != "no")
                    message = Id + " связался с " + clientId;
                else
                    message = Id + " подключен";
                _server.BroadcastMessage(message, Id, false);
                Console.WriteLine(message);

                while (true)
                {
                    try
                    {
                        var isFile = false;
                        message = GetMessage();
                        if (message.Length > 9 && message[..6] == " " && File.Exists(message.Substring(6, message.Length - 6)))
                        {
                            isFile = true;
                            var s = File.ReadAllText(message.Substring(6, message.Length - 6));
                            var signing = Sign(s);
                            var h = (int)BigInteger.ModPow(HashFunction(s).GetHashCode(), 1, signing.p - 2);

                            if (h < BigInteger.Zero)
                                h += signing.p - 2;

                            if (!_server.FileCheck(signing, h))
                            {
                                Console.WriteLine("File sent incorrect");
                                continue;
                            }
                        }

                        message = $"{Id}: {message}";
                        Console.WriteLine(message);
                        _server.BroadcastMessage(message, Id, isFile);
                    }
                    catch
                    {
                        message = $"{Id}: покинул чат";
                        Console.WriteLine(message);
                        _server.BroadcastMessage(message, Id, false);
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            finally
            {
                _server.RemoveConnection(this.Id);
                Close();
            }
        }

        private string GetMessage()
        {
            var data = new byte[64];
            var builder = new StringBuilder();
            int bytes = 0;
            do
            {
                bytes = Stream.Read(data, 0, data.Length);
                builder.Append(Encoding.Unicode.GetString(data, 0, bytes));
            }
            while (Stream.DataAvailable);

            return builder.ToString();
        }

        protected internal void Close()
        {
            Stream?.Close();
            _client?.Close();
        }

        public string FirstStep(string publicKey)
        {
            return Encrypt(Id + RndNumber, publicKey);
        }

        public string SecondStep(string data, string publicKey)
        {
            var x = Decrypt(data, PrivateKey);
            var r = x[..36];
            return Encrypt(r + RndNumber, publicKey);
        }

        public string ThirdStep(string data, string publicKey)
        {
            var x = Decrypt(data, PrivateKey);
            var rA = x[..36];
            if (rA == RndNumber)
            {
                return null;
            }
            var rB = x.Substring(36, x.Length - 36);
            return Encrypt(rB, publicKey);
        }

        public bool FourthStep(string data)
        {
            return Decrypt(data, PrivateKey) == RndNumber;
        }

        private static string Decrypt(string data, string privateKey)
        {
            var rsa = new RSACryptoServiceProvider();
            var dataArray = data.Split(new char[] { ',' });
            var dataByte = new byte[dataArray.Length];
            for (var i = 0; i < dataArray.Length; i++)
            {
                dataByte[i] = Convert.ToByte(dataArray[i]);
            }

            rsa.FromXmlString(privateKey);
            var decryptedByte = rsa.Decrypt(dataByte, false);
            return _encoder.GetString(decryptedByte);
        }

        private static string Encrypt(string data, string publicKey)
        {
            var rsa = new RSACryptoServiceProvider();
            rsa.FromXmlString(publicKey);
            var dataToEncrypt = _encoder.GetBytes(data);
            var encryptedByteArray = rsa.Encrypt(dataToEncrypt, false).ToArray();
            var length = encryptedByteArray.Count();
            var item = 0;
            var sb = new StringBuilder();
            foreach (var x in encryptedByteArray)
            {
                item++;
                sb.Append(x);

                if (item < length)
                    sb.Append(',');
            }

            return sb.ToString();
        }


        private static (BigInteger a, BigInteger b, int p, int g, int y) Sign(string message)
        {
            int p;
            const int rounds = 100;
            do
            {
                p = _rnd.Next(0, int.MaxValue);
            } while (!MillerRabinTest(p, rounds));

            int g;
            
            do
            {
                g = _rnd.Next(1, p);
            } while (BigInteger.ModPow(g, p - 1, p) != BigInteger.One);
            
            var x = _rnd.Next(0, p - 1);
            var y = (int)BigInteger.ModPow(g, x, p);
            
            if (y < BigInteger.Zero)
                y += p;
            
            var hash = (int)BigInteger.ModPow(HashFunction(message).GetHashCode(), 1, p - 2);
            
            if (hash < BigInteger.Zero)
                hash += p - 2;

            int r;
            
            do
            {
                r = _rnd.Next(1, p - 2);
            } while (BigInteger.GreatestCommonDivisor(r, p - 1) != BigInteger.One);

            var a = BigInteger.ModPow(g, r, p);
            if (a < BigInteger.Zero)
                a += p;
            
            r = (int)ModInverse(r, p - 1);
            if (r < BigInteger.Zero)
                r += p;

            var b = BigInteger.ModPow((hash - x * a) * r, 1, p - 1);
            if (b < BigInteger.Zero)
                b += p - 1;

            return (a, b, p, g, y);
        }

        public static bool SignCheck(int p, int g, int y, BigInteger a, BigInteger b, int hash)
        {
            var f1 = BigInteger.ModPow(g, hash, p);
            if (f1 < BigInteger.Zero)
                f1 += p;
            
            var f21 = BigInteger.ModPow(y, a, p);
            var f22 = BigInteger.ModPow(a, b, p);
            var f2 = BigInteger.ModPow(f21 * f22, 1, p);
            
            if (f2 < BigInteger.Zero)
                f2 += p;

            return f1 == f2;
        }

        private static BigInteger ModInverse(BigInteger a, BigInteger n)
        {
            BigInteger i = n, v = 0, d = 1;
            while (a > 0)
            {
                BigInteger t = i / a, x = a;
                a = i % x;
                i = x;
                x = d;
                d = v - t * x;
                v = x;
            }
            v %= n;
            if (v < 0) v = (v + n) % n;
            return v;
        }

        private static string HashFunction(string rawData)
        {
            using var sha256Hash = SHA256.Create();
            var bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(rawData));

            var builder = new StringBuilder();
            foreach (var t in bytes)
            {
                builder.Append(t.ToString("x2"));
            }

            return builder.ToString();
        }

        private static bool MillerRabinTest(BigInteger n, int k)
        {
            if (n == 2 || n == 3)
                return true;

            if (n < 2 || n % 2 == 0)
                return false;

            var t = n - 1;
            var s = 0;
            
            while (t % 2 == 0)
            {
                t /= 2;
                s += 1;
            }

            for (var i = 0; i < k; i++)
            {
                var rng = new RNGCryptoServiceProvider();
                var _a = new byte[n.ToByteArray().LongLength];

                BigInteger a;

                do
                {
                    rng.GetBytes(_a);
                    a = new BigInteger(_a);
                }
                while (a < 2 || a >= n - 2);
                
                var x = BigInteger.ModPow(a, t, n);

                if (x == 1 || x == n - 1)
                    continue;

                for (var r = 1; r < s; r++)
                {
                    x = BigInteger.ModPow(x, 2, n);

                    if (x == 1)
                        return false;

                    if (x == n - 1)
                        break;
                }

                if (x != n - 1)
                    return false;
            }

            return true;
        }
    }
}
