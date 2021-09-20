using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Security.Cryptography;

namespace lab3
{
    public static class Aes
    {
        private static byte[] Key { get; set; }
        private static byte[] Iv { get; set; }

        static Aes()
        {
            Key = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f
            };
            Iv = Key;
        }

        private static byte[] Encrypt_Aes(byte[] data)
        {
            using var aes = Rijndael.Create();
            aes.IV = Key;
            aes.Key = Iv;
            aes.KeySize = 128;
            aes.BlockSize = 128;
            aes.Padding = PaddingMode.None;
            aes.Mode = CipherMode.ECB;
            var ms = new MemoryStream();
            var cs = new CryptoStream(ms, aes.CreateEncryptor(Key, Iv), CryptoStreamMode.Write);

            cs.Write(data, 0, data.Length);
            cs.Close();
            return ms.ToArray();
        }

        public static byte[] AddPadding(byte[] plaintext)
        {
            var size = plaintext.Length;
            var paddingLen = 16 - (size % 16);
            Array.Resize(ref plaintext, size+paddingLen);

            plaintext[size] = 0x80;
            for (var i = size+1; i < size + paddingLen; i++)
            {
                plaintext[i] += 0x00;
            }
            return plaintext;
        }
        
        public static byte[] RemovePadding(byte[] plaintext)
        {
            var size = plaintext.Length;
            var paddingLen = 0;
            byte b = 0x80;
            for (var i = size - 1; i >= 0; i--)
            {
                paddingLen++;
                if (plaintext[i] == b) break;
            }
            Console.WriteLine(paddingLen);
            var tmp = new byte[size-paddingLen];
            Array.Copy(plaintext, 0, tmp, 0, size - paddingLen);
            return tmp;
        }
        
        private static byte[] Xor(byte[] i, byte[] key)
        {
            var res = new byte[i.Length];
            for (var j = 0; j < i.Length; j++)
            {
                res[j] = (byte)(i[j] ^ key[j]);
            }
            return res;
        }

        public static byte[] Ofb(byte[] plainText)
        {
            var blocks = new byte[plainText.Length];
            var previous = Iv;
            var plainBlocksCount = plainText.Length / 16;
            for (var i = 0; i < plainBlocksCount; i++)
            {
                var plaintextBlock = new byte[16];
                Array.Copy(plainText, i*16, plaintextBlock, 0, 16);
                var block = Encrypt_Aes(previous);
                var encryptBlock = Xor(plaintextBlock, block);
                Array.Copy(encryptBlock, 0, blocks, i*16, 16);
                previous = block;
            }
            return blocks;
        }
    }
}