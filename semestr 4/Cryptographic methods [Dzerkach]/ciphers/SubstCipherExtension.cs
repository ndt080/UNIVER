using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Ciphers
{
    public static class SubstCipherExtension
    {
        public static List<string> GetSubstEncryptTxt(this List<string> text, string key)
        {
            var cipherText = new List<string>();
            foreach (var str in text)
            {
                var tmp  = Regex.Replace(str, "[-.?!)(,:]", "")
                    .Replace(" ", "").ToLower();
                var cipherStr = "";
                foreach (var t in tmp)
                {
                    if ((int) t >= 97 && (int) t <= 122)
                    {
                        cipherStr += key[(int) t - 97];
                    }
                }
                cipherText.Add(cipherStr);
            }
            
            
            return cipherText;
        }
        
        public static List<string> GetSubstDecryptTxt(this List<string> text, string key)
        {
            var origText = new List<string>();
            
            foreach (var str in text)
            {
                var origStr = "";
                foreach (var t in str)
                {
                    var index = 0;
                    for (var k = 0; k < 26; k++)
                    {
                        if (key[k] == t)
                        {
                            index = k;
                            break;
                        }
                    }
                    origStr += (char)(index + 97);
                }
                origText.Add(origStr);
            }
            return origText;
        }
    }
}