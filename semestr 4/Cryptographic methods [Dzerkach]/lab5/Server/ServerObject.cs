using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Numerics;
using System.Text;
using System.Threading;

namespace Server
{
    public class ServerObject
    {
        private static TcpListener _tcpListener;
        private readonly List<ClientObject> _clients = new List<ClientObject>();
        private readonly List<(ClientObject cl1, ClientObject cl2)> _pairs = new List<(ClientObject cl1, ClientObject cl2)>();

        protected internal void AddConnection(ClientObject clientObject)
        {
            _clients.Add(clientObject);
        }
        
        protected internal void RemoveConnection(string id)
        {
            var client = _clients.FirstOrDefault(c => c.Id == id);

            if (client != null)
                _clients.Remove(client);
        }

        protected internal bool Authenticate(string id1, string id2)
        {
            if (id2 == "no")
            {
                return true;
            }

            ClientObject myClient = null, myFriend = null;
            foreach (var client in _clients)
            {
                if (client.Id == id1)
                    myClient = client;
                else if (client.Id == id2)
                    myFriend = client;
            }

            if (myClient == null || myFriend  == null)
            {
                return false;
            }

            _pairs.Add((myClient, myFriend));

            try
            {
                var x = myClient.FirstStep(myFriend.PublicKey);
                x = myFriend.SecondStep(x, myClient.PublicKey);
                x = myClient.ThirdStep(x, myFriend.PublicKey);
                return x != null && myFriend.FourthStep(x);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }
        }

        protected internal bool FileCheck((BigInteger a, BigInteger b, int p, int g, int y) valueTuple, int hash)
        {
            try
            {
                if (ClientObject.SignCheck(valueTuple.p, valueTuple.g, valueTuple.y, valueTuple.a, valueTuple.b, hash))
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }

            return false;
        }

        protected internal void Listen()
        {
            try
            {
                _tcpListener = new TcpListener(IPAddress.Any, 8888);
                _tcpListener.Start();
                Console.WriteLine("The server is running. Waiting for connections...");

                while (true)
                {
                    var tcpClient = _tcpListener.AcceptTcpClient();

                    var clientObject = new ClientObject(tcpClient, this);
                    Console.WriteLine(clientObject.Id);
                    var clientThread = new Thread(new ThreadStart(clientObject.Process));
                    clientThread.Start();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                Disconnect();
            }
        }

        protected internal void BroadcastMessage(string message, string id, bool isFile)
        {
            var data = Encoding.Unicode.GetBytes(message);
            foreach (var t in _clients)
            {
                if (_pairs.Exists(c => c.cl1.Id == t.Id || c.cl2.Id == t.Id))
                {
                    var (cl1, cl2) = _pairs.First(c => c.cl1.Id == t.Id || c.cl2.Id == t.Id);
                    if (cl1.Id != id)
                    {
                        Console.WriteLine("{" + message + "}");
                        if (isFile && File.Exists(message.Substring(message.IndexOf("file: ") + 6, message.Length - 6 - message.IndexOf("file: "))))
                        {
                            try
                            {
                                var fs = File.OpenRead(message.Substring(message.IndexOf("file: ") + 6, message.Length - 6 - message.IndexOf("file: ")));
                                fs.CopyTo(cl1.Stream);
                            }
                            catch (Exception e)
                            {
                                Console.WriteLine(e.Message);
                            }
                        }
                        else
                        {
                            cl1.Stream.Write(data, 0, data.Length);
                        }
                    }
                    else
                    {
                        if (isFile && File.Exists(message.Substring(message.IndexOf("file: ") + 6, message.Length - 6 - message.IndexOf("file: "))))
                        {
                            try
                            {
                                var fs = File.OpenRead(message.Substring(message.IndexOf("file: ") + 6, message.Length - 6 - message.IndexOf("file: ")));
                                fs.CopyTo(cl2.Stream);
                            }
                            catch (Exception e)
                            {
                                Console.WriteLine(e.Message);
                            }
                        }
                        else
                        {
                            cl2.Stream.Write(data, 0, data.Length);
                        }
                    }
                    break;
                }
            }
        }
        
        protected internal void Disconnect()
        {
            _tcpListener.Stop();
            foreach (var t in _clients)
                t.Close();

            Environment.Exit(0);
        }
    }
}
