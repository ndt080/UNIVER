using System;
using System.Threading;

namespace Server
{
    internal static class Program
    {
        private static ServerObject _server;
        private static Thread _listenThread;

        private static void Main(string[] args)
        {
            try
            {
                _server = new ServerObject();
                _listenThread = new Thread(new ThreadStart(_server.Listen));
                _listenThread.Start();
            }
            catch (Exception ex)
            {
                _server.Disconnect();
                Console.WriteLine(ex.Message);
            }
        }
    }
}