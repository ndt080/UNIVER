using System;
using System.IO;
using System.Text;

namespace magma
{
    internal static class Program
    {
        private static byte[] EncryptData(byte[] data, byte[] key)
        {
            return new MagmaE32(data, key).GetEncryptData;
        }

        private static byte[] DecryptData(byte[] data, byte[] key)
        {
            return new MagmaD32(data, key).GetDecryptData;
        }

        private static byte[] ReadDataFile(string path)
        {
            return File.ReadAllBytes(path);
        }

        private static void WriteDataFile(string path, byte[] data)
        {
            File.WriteAllBytes(path, data);
        }

        private static void Main(string[] args)
        {
            /*string KEY = "0CFE9E69271557822FE715A8B3E564BE0CFE9E69271557822FE715A8B3E564BE";
            string DATA = "CD73CED94F3E36B7";
            const string dataPath = "../../../resources/data.bin";             
            const string keyPath = "../../../resources/key.bin";
            var tmp = Converter.StringToByteArray(hexString);
            WriteDataFile("../../../resources/key.bin", tmp);
            var tmp = Converter.StringToByteArray(hexString);
            WriteDataFile(dataPath, tmp);
            */
            const string keyPath = "../../../resources/key.bin";
            const string dataPath = "../../../resources/data.bin";

            //READ KEY
            Console.WriteLine("log>\tRead key..." +
                              "\n\tPath: {0}", keyPath);
            var keyByte = ReadDataFile(keyPath);
            var lengthKey = keyByte.Length;
            Console.WriteLine("\tLength key: {0} bit", lengthKey * 8);
            Console.WriteLine((keyByte is not {Length: 32})
                ? "\tError! Incorrect key length: " + lengthKey
                : "\tSuccessful key reading!");
            Console.WriteLine("log>\tKey: {0}", Converter.ByteArrayToString(keyByte));
            //READ DATA
            Console.WriteLine("log>\tRead data block..." +
                              "\n\tPath: {0}", dataPath);
            var dataByte = ReadDataFile(dataPath);
            var lengthData = dataByte.Length;
            Console.WriteLine("\tLength data block: {0} bit", lengthData * 8);
            Console.WriteLine((dataByte is not {Length: 8})
                ? "\tError! Incorrect data block length: " + lengthData
                : "\tSuccessful data block reading!");
            Console.WriteLine("log>\tBlock data: {0}", Converter.ByteArrayToString(dataByte));

            //ENCRYPTING AND DECRYPTING DATA
            var decryptByte = Array.Empty<byte>();
            try
            {
                Console.WriteLine("log>\tEncrypting data...");
                var encryptByte = EncryptData(dataByte, keyByte);
                Console.WriteLine("\tSuccessful data encryption!");

                Console.WriteLine("log>\tEncrypted string: " + Converter.ByteArrayToString(encryptByte));
                const string pathEncrypt = "../../../resources/encrypt.bin";
                Console.WriteLine("log>\tWrite encrypting data in file..." +
                                  "\n\tPath: {0}", pathEncrypt);
                WriteDataFile(pathEncrypt, encryptByte);
                Console.WriteLine("\tSuccessful write data encryption!");
                try
                {
                    Console.WriteLine("log>\tDecrypting data...");
                    decryptByte = DecryptData(encryptByte, keyByte);
                    Console.WriteLine("\tSuccessful data decryption!");
                }
                catch
                {
                    Console.WriteLine("\tError! Fatal error when decrypting data!");
                }
            }
            catch
            {
                Console.WriteLine("\tError! Fatal error when encrypting data!");
            }

            Console.WriteLine("log>\tDecrypted string: " + Converter.ByteArrayToString(decryptByte));
        }
    }
}