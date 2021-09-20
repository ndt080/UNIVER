using System;
using System.Linq;

namespace magma
{
    internal class Converter
    {
        public static byte[] StringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                .Where(x => x % 2 == 0)
                .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                .ToArray();
        }

        public static string ByteArrayToString(byte[] ba)
        {
            return BitConverter.ToString(ba).Replace("-", "");
        }

        protected static byte[] ConvertToByte(ulong[] fl)
        {
            var encrByteFile = new byte[fl.Length * 8];

            for (var i = 0; i < fl.Length; i++)
            {
                var temp = BitConverter.GetBytes(fl[i]);
                for (var j = 0; j < temp.Length; j++)
                    encrByteFile[j + i * 8] = temp[j];
            }

            return encrByteFile;
        }

        protected static uint[] GetUIntKeyArray(byte[] byteKey)
        {
            var key = new uint[8];
            for (var i = 0; i < key.Length; i++)
            {
                key[i] = BitConverter.ToUInt32(byteKey, i * 4);
            }

            return key;
        }

        protected static ulong[] GetULongDataArray(byte[] byteData)
        {
            var data = new ulong[byteData.Length / 8];
            for (var i = 0; i < data.Length; i++)
            {
                data[i] = BitConverter.ToUInt64(byteData, i * 8);
            }

            return data;
        }
    }
}