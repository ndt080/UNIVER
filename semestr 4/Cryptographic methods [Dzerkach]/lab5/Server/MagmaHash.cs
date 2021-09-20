using System;
using System.Collections;
using System.Collections.Generic;
using System.Net.Security;

namespace Server
{
    public class MagmaHash
    {
        private static readonly int[][] Sblock = new[]
        {
            new[] {5, 9, 2, 4, 8, 7, 3, 10, 15, 1, 14, 12, 6, 11, 0, 13},
            new[] {7, 13, 5, 12, 2, 14, 4, 3, 0, 8, 10, 9, 1, 6, 11, 15},
            new[] {12, 9, 11, 4, 15, 5, 10, 1, 14, 3, 7, 13, 8, 6, 0, 2},
            new[] {10, 8, 4, 0, 6, 15, 11, 3, 12, 13, 2, 1, 7, 5, 9, 14},
            new[] {8, 10, 15, 6, 9, 11, 4, 12, 7, 1, 14, 3, 5, 0, 2, 13},
            new[] {13, 1, 10, 0, 5, 8, 2, 14, 7, 15, 9, 12, 11, 3, 6, 4},
            new[] {15, 7, 6, 10, 12, 4, 1, 11, 14, 9, 8, 0, 2, 5, 13, 3},
            new[] {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
        };


        public static byte EncryptionPolicy(byte[] d)
        {
            
            if (d.Length % 8 != 0)
            {
                var paddingCount = 8 - (d.Length % 8);
                Array.Resize(ref d, d.Length + paddingCount);
                for (var i = 0; i < 8 - (d.Length % 8); i++)
                {
                    d[i] = 0;
                }
            }
            
            var text = new BitArray(d);
            var key = new BitArray(Convert.ToByte("Lorem ipsum dolor sit amet conse"));

            for (var i = 0; i < (int) d.Length / 8; i++)
            {
                for(var r = 0; r < 32; r++)
                {
                    var iter = r % 8;

                    var len = 32 * (iter + 1) - 32 * iter;
                    var keyline = new BitArray(len);
                    for (var k = 32 * iter; k < 32 * (iter + 1); k++)
                    {
                        keyline[k] = key[k];
                    }


                    len = 64 * i + 3 - 64 * i;
                    var L = new BitArray(len);
                    for (var k = 64*i; k < 64 * i + 32; k++)
                    {
                        L[k] = text[k];
                    }
                    
                    len = 64 * (i + 1) - 64 * i + 32;
                    var R = new BitArray(len);
                    for (var k = 64 * i + 32; k < 64 * (i + 1); k++)
                    {
                        L[k] = text[k];
                    }
                    
                    //
                    // Smod = int2ba((ba2int(R) + ba2int(keyline)) % (2 ** 32))
                    //
                    // k = bitarray()
                    // for j in range(32 - len(Smod)):
                    //     k.append(0)
                    //
                    // Smod = k + Smod
                    //
                    // Ssimple = bitarray()
                    // for j in range(0, len(Smod), 4):
                    //     Ssimple += (int2ba(int(Sblock[int(j / 4)][ba2int(Smod[j: j + 4])]), length=4))
                    //
                    // Srol = Ssimple[11:] + Ssimple[: 11]
                    //
                    // Sxor = Srol ^ L
                    //
                    // R = L
                    // L = Sxor
                    //
                    // encoded_text = L + R
                    //
                    // text[64 * i: 64 * (i + 1)] = encoded_text
                } 
            }    
                
            var bytes = new byte[1];
            text.CopyTo(bytes, 0);
            return bytes[0];
        }
    }
}