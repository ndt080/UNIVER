using System;
using System.IO;
using System.Runtime.InteropServices.ComTypes;
using System.Security.Cryptography;
using System.Text;

namespace lab3
{
    internal static class Program
    {
        public static void Main()
        {
            //my padding http://www.crypto-it.net/eng/theory/padding.html
            //my cipher mode http://www.crypto-it.net/eng/theory/modes-of-block-ciphers.html
            
            var original = ReadDataFile("../../../resources/data.bin");
            var paddStr = Aes.AddPadding(original);

            var encrypted = Aes.Ofb(paddStr);
            var roundtrip = Aes.Ofb(encrypted);
            
            var rPaddStr = Aes.RemovePadding(roundtrip);
            
            const string pathEncrypt1 = "../../../resources/addP.bin";
            const string pathEncrypt2 = "../../../resources/delP.bin";
            const string pathEncrypt3 = "../../../resources/encrypt.bin";
            WriteDataFile(pathEncrypt1, paddStr);
            WriteDataFile(pathEncrypt2, rPaddStr);  
            WriteDataFile(pathEncrypt3, encrypted);  
            
            Console.WriteLine("Original:   {0}", Convert.ToHexString(original));
            Console.WriteLine("+padding:   {0}",  Convert.ToHexString(paddStr));
            Console.WriteLine("Encrypt:   {0}", Convert.ToHexString(encrypted));
            Console.WriteLine("Decrypt: {0}", Convert.ToHexString(roundtrip));
            Console.WriteLine("-padding:   {0}",  Convert.ToHexString(rPaddStr));
        }
        private static void WriteDataFile(string path, byte[] data)
        {
            File.WriteAllBytes(path, data);
        }
        
        private static byte[] ReadDataFile(string path)
        {
            return File.ReadAllBytes(path);
        }

    }
}