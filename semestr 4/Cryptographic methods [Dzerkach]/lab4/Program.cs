using System;
using System.IO;

namespace lab4
{
    internal static class Program
    {
        private static void Main(string[] args)
        {
            const int size = 1250000;
            var gg = new GeneratorGeffe(
                0x400051, 0x6980D6, 
                0x100000C8, 0x1016FD2E, 
                0x4000007F, 0x732DB497, 
                8);

            Stream fs = new FileStream("../../../seq.bin", FileMode.OpenOrCreate, FileAccess.Write);
            using (var bw = new BinaryWriter(fs))
            {
                for (var i = 0; i < size; i++) {
                    var seq = gg.GenerateSeq();
                    bw.Write(seq);
                }
            }
            fs.Close();
        }
    }
}