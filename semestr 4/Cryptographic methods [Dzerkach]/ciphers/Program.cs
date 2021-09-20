using System;
using System.Collections.Generic;
using System.IO;

namespace Ciphers
{
    internal static class Program
    {
        private static List<string> ReadFile(string filename)
        {
            string line;
            var text = new List<string>();
            using var sr = new StreamReader(filename,  System.Text.Encoding.Default);
            while ((line = sr.ReadLine()) != null)
            {
                text.Add(line);
            }
            sr.Close();
            return text;
        }
        private static void WriteFile(string filename, IEnumerable<string> data)
        {
            using var sw = new StreamWriter(filename);
            foreach (var str in data)
            {
                sw.WriteLine(str);
            }
            sw.Close();
        }

        private static void Main(string[] args)
        {
            var text = ReadFile("../../../resource/data.txt");
            var key = ReadFile("../../../resource/key.txt");

            
            var encSubstText = text.GetSubstEncryptTxt(key[0]);
            var decSubstText = encSubstText.GetSubstDecryptTxt(key[0]);
            WriteFile("../../../resource/out/EncryptSubst.txt", encSubstText);
            WriteFile("../../../resource/out/DecryptSubst.txt", decSubstText);  
            
            var keyV = text.GetVigEncryptKey(key[1]);
            var encryptedText = text.GetVigEncryptTxt(keyV);
            var decryptedText = encryptedText.GetVigDecryptTxt(keyV);
            WriteFile("../../../resource/out/EncryptVig.txt", encryptedText);
            WriteFile("../../../resource/out/DecryptVig.txt", decryptedText);
        }
    }
}