using System;

namespace lab4
{
    public class GeneratorGeffe
    {
        private static long Length { get; set; }
        private static uint Polynomial1 { get; set; }
        private static uint Polynomial2 { get; set; }
        private static uint Polynomial3 { get; set; }
        
        private static uint S1 { get; set; }
        private static uint S2 { get; set; }
        private static uint S3 { get; set; }

        public GeneratorGeffe(uint p1, uint s1, uint p2, uint s2, uint p3, uint s3, long length)
        {
            Length = length;
            Polynomial1 = Reverse(p1);
            Polynomial2 = Reverse(p2);
            Polynomial3 = Reverse(p3);
            S1 = s1;
            S2 = s2;
            S3 = s3;
        }
        
        public byte GenerateSeq()
        {
            byte result = 0;
            for (var i = 0; i < Length; i++)
            {
                result <<= 1;
                result |= (byte) (GenerateNext() ? 0b1 : 0b0);
                // if (GenerateNext())
                // {
                //     result |= 0b1;
                // }
                // else
                // {
                //     result |= 0b0;
                // }
            }
            return result;
        }

        private static bool GenerateNext()
        {
            var lfsr1 = LfsrConfigGalois(1, 0b10000000000000000000000);
            var lfsr2 = LfsrConfigGalois(2, 0b10000000000000000000000000000);
            var lfsr3 = LfsrConfigGalois(3, 0b1000000000000000000000000000000);
            return (lfsr1 & lfsr2) ^ ((lfsr1 ^ true) & lfsr3);
        }
       
        private static uint Reverse(uint num)
        {
            uint result = 0;
            while (num != 0)
            {
                result = (result << 1) | (num & 1);
                num >>= 1;
            }
            return result;
        }

        private static bool LfsrConfigGalois(int n, uint length)
        {
            switch (n)
            {
                case 1:
                    if ((S1 & 0b1) == 1) {
                        S1 = ((S1 ^ Polynomial1) >> 1) | length;
                        return true;
                    }
                    S1 >>= 1;
                    break;
                case 2:
                    if ((S2 & 0b1) == 1) {
                        S2 = ((S2 ^ Polynomial2) >> 1) | length;
                        return true;
                    }
                    S2 >>= 1;
                    break;
                case 3:
                    if ((S3 & 0b1) == 1) {
                        S3 = ((S3 ^ Polynomial3) >> 1) | length;
                        return true;
                    }
                    S3 >>= 1;
                    break;
            }
            return false;
        }
        
    }
}