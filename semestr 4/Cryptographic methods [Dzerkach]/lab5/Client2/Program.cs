using System;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Client2
{
    internal static class Program
    {
        private static string _cuId;
        private const string Host = "127.0.0.1";
        private const int Port = 8888;
        private static TcpClient _client;
        private static NetworkStream _stream;

        private static void Main(string[] args)
        {
            _client = new TcpClient();
            try
            {
                _client.Connect(Host, Port);
                _stream = _client.GetStream();

                Console.Write("Enter client ID: ");
                _cuId = Console.ReadLine();

                var message = _cuId;
                var data = Encoding.Unicode.GetBytes(message!);
                _stream.Write(data, 0, data.Length);

                var receiveThread = new Thread(new ThreadStart(ReceiveMessage));
                receiveThread.Start();
                SendMessage();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                Disconnect();
            }
        }

        private static void SendMessage()
        {
            Console.WriteLine("Enter message: ");

            while (true)
            {
                var message = Console.ReadLine();
                var data = Encoding.Unicode.GetBytes(message);
                _stream.Write(data, 0, data.Length);
            }
        }

        private static void ReceiveMessage()
        {
            while (true)
            {
                try
                {
                    var data = new byte[64];
                    var builder = new StringBuilder();
                    var bytes = 0;
                    do
                    {
                        bytes = _stream.Read(data, 0, data.Length);
                        builder.Append(Encoding.Unicode.GetString(data, 0, bytes));
                    }
                    while (_stream.DataAvailable);

                    var message = builder.ToString();
                    Console.WriteLine(message);
                }
                catch
                {
                    Console.WriteLine("Connect terminated!");
                    Console.ReadLine();
                    Disconnect();
                }
            }
        }

        private static void Disconnect()
        {
            _stream?.Close();
            _client?.Close();
            Environment.Exit(0);
        }
    }
}
