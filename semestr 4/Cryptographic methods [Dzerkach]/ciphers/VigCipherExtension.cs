using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Ciphers
{
    public static class VigCipherExtension
    {
        public static List<string> GetVigEncryptKey(this List<string> text, string keyword)
        {
            var key = new List<string>();
            foreach (var str in text)
            {
                var tmp = keyword;
                var x = str.Length;
                for (var i = 0; ; i++)
                {
                    i = x == i ? 0 : i;
                    if (tmp.Length == str.Length)
                    {
                        break;
                    }
                    tmp+=(tmp[i]);
                }
                key.Add(tmp);
            }
            return key;
            
        }
        public static List<string> GetVigEncryptTxt(this List<string> text, List<string> keys)
        {
            var cipherText = new List<string>();

            for (var i = 0; i < text.Count && i < keys.Count; i++)
            {
                
                var str = Regex.Replace(text[i], "[-.?!)(,:]", "")
                    .Replace(" ", "").ToUpper();
                var key = keys[i];
                var cipherStr = "";
                for (var j = 0; j < str.Length; j++)
                {
                    var x = (str[j] + key[j]) % 26;
                    x += 'A';
                    cipherStr += Convert.ToChar(x);
                }
                cipherText.Add(cipherStr);
            }
            return cipherText;
        }
        
        public static List<string> GetVigDecryptTxt(this List<string> text, List<string> keys)
        {
            var origText = new List<string>();
            for (var i = 0; i < text.Count && i < keys.Count; i++)
            {
                var str = text[i];
                var key = keys[i];
                var origStr = "";
                for (var j = 0 ; j < str.Length && j < key.Length; j++)
                {
                    var x = (str[j] - key[j] + 26) % 26;
                    x += 'A';
                    origStr += Convert.ToChar(x);
                }
                origText.Add(origStr);
            }
            return origText;
        }
    }
}