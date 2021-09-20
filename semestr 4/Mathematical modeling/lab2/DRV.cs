using System;
using System.Collections.Generic;
using System.Linq;

namespace lab2
{
    public class DRV
    {
        private static int NumberImplementations { get; set; }
        internal DRV(int n)
        {
            NumberImplementations = n;
        }
        public static List<int> ModelingGeometric(params double[] param)
        {
            var p = param[0];
            var sequence = new List<int>();
            var rnd = new Random();

            for (var i = 0; i < NumberImplementations; i++)
            {
                sequence.Add( (int) (Math.Log(rnd.NextDouble(), 1 - p) + 1));
            }
            return sequence;
        }
        public static List<int> ModelingBernoulli(params double[] param)
        {
            var p = param[0];
            var sequence = new List<int>();
            var rnd = new Random();

            for (var i = 0; i < NumberImplementations; i++)
            {
                sequence.Add(rnd.NextDouble() < p ? 1 : 0);
            }
            return sequence;
        }
        public static List<int> ModelingBinomial(params double[] param)
        {
            var m = param[0];
            var p = param[1];
            double  x = 0;
            var sequence = new int[NumberImplementations];
            var rnd = new Random();

            for(var j = 0; j < NumberImplementations; j++)
            {
                for(var i = 0; i < m; i++)
                {
                    if (rnd.NextDouble() < p)
                    {
                        x++;
                    }
                }
                sequence[j] = (int) x;
                x = 0;
            }
            return new List<int>(sequence);
        }
        public static List<int> ModelingNegBinomial(params double[] param)
        {
            var r = param[0];
            var p = param[1];
            double rem = r, x = 0;
            var sequence = new int[NumberImplementations];
            var rnd = new Random();

            for(var j = 0; j < NumberImplementations; j++)
            {
                while (rem != 0)
                {
                    if (rnd.NextDouble() > p)
                    {
                        x++;
                    }
                    else
                    {
                        rem--;
                    }
                }
                sequence[j] = (int) x;
                rem = r;
                x = 0;
            }
            return new List<int>(sequence);
        }
    }
}