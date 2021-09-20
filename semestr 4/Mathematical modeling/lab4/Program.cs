using System;
using System.Collections.Generic;

namespace lab4
{
    internal static class Program
    {
        private static void Main()
        {
            double exact_value = 3.21825;
            var rnd = new Random();
            var res = 0.0;
            var n = 1000000;
            for (var i = 0; i < n; i++)
            {
                var x = 0.0;
                var y = 0.0;
                while (true)
                {
                    x = rnd.NextDouble() * 2 * Math.Sqrt(3) - Math.Sqrt(3);
                    y = rnd.NextDouble() * 2 * Math.Sqrt(3) - Math.Sqrt(3);
                    var value = Math.Pow(x, 2) + Math.Pow(y, 2);
                    if (value >= 1 && value < 3)
                        break;
                }

                res += 2 * Math.PI / (Math.Pow(x, 2) + Math.Pow(y, 4));
            }
            Console.WriteLine("Monte-Carlo: {0}\nExact value: {1}", res / n, exact_value);
        }
    }
}