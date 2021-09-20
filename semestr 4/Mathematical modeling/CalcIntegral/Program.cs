using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;

namespace CalcIntegral
{
    internal static class Program
    {
        private static double GetRandomNumber(double minimum, double maximum)
        {
            return new Random().NextDouble() * (maximum - minimum) + minimum;
        }

        private static void Main(string[] args)
        {
            const int n = 1000000;
            var x = new double[n];
            var a = new double[n];
            const double maxLim = (5 * Math.PI) / 7;
            double sum = 0;
            for (var i = 0; i < n; i++)
            {
                x[i] = GetRandomNumber(0, maxLim);
                a[i] = Math.Cos(x[i] + Math.Sin(x[i]));

                sum += a[i];
            }

            Console.WriteLine((sum / n) * maxLim);
        }
    }
}