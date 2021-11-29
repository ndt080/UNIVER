using System;
using System.IO;
using MathNet.Numerics;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Double;

namespace lab1
{
    public static class ChebyshevBarycentric
    {
        private static double Xn(double a, double b, double i, double n)
        {
            return (a + b) / 2 + ((a - b) / 2) * Math.Cos(Math.PI * (2 * i + 1) / (2 * n + 2));
        }
        
        private static double Pn(double X0, Vector<double> X, Vector<double> Y, Func<double, double> F, double n)
        {
            if (X.Exists(x => x.Equals(X0))) return F(X0);
            
            double numerator = 0;
            double denominator = 0;
            for (var i = 0; i < X.Count; i++)
            {
                var v = WeightFactor(i, n - 1);
                numerator += Y[i] * v / (X0 - X[i]);
                denominator += v / (X0 - X[i]);
            }
            return numerator / denominator;
        }

        private static double WeightFactor(int i, double degree)
        {
            return Math.Pow(-1, i) * Math.Sin((2 * i + 1) / (2 * degree + 2) * Math.PI);
        }

        public static void Run(Func<double, double> F, Vector<double> range, int nodes, double[] xArr)
        {
            using var sw = new StreamWriter(Environment.CurrentDirectory +
                                            $"../../../../ChebyshevBarycentric/results/{nodes}.txt",
                false, System.Text.Encoding.Default);
            var time = System.Diagnostics.Stopwatch.StartNew();
            Vector<double> X = DenseVector.Build.Dense(nodes, 0);
            Vector<double> Y = DenseVector.Build.Dense(nodes, 0);
            Vector<double> P = DenseVector.Build.Dense(xArr.Length, 0);
            
            for (var i = 0; i < nodes; i++)
            {
                X[i] = Xn(range[0], range[1], i, nodes);
                Y[i] = F(X[i]);
            }
            
            time.Start();
            for (var i = 0; i < xArr.Length; i++)
                P[i] = Pn(xArr[i], X, Y, F, nodes);
            time.Stop();

            var timeRes = time.Elapsed;
            Console.WriteLine($"Range = [{string.Join("; ", range)}]");
            Console.WriteLine($"arrX = file: results/{nodes}.txt, row 1; Number of items: {xArr.Length}");
            Console.WriteLine($"P = file: results/{nodes}.txt, row 2; Number of items: {P.Count}");
            Console.WriteLine($"X = file: results/{nodes}.txt, row 3; Number of items: {X.Count}");
            Console.WriteLine($"Y = file: results/{nodes}.txt, row 4; Number of items: {Y.Count}");
            Console.WriteLine($"Time = {timeRes.TotalMilliseconds}ms");

            System.Threading.Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");
            sw.WriteLine($"{string.Join(" ", xArr)}");
            sw.WriteLine($"{string.Join(" ", P)}");
            sw.WriteLine($"{string.Join(" ", X)}");
            sw.WriteLine($"{string.Join(" ", Y)}");
            sw.WriteLine($"arrX: line 1");
            sw.WriteLine($"P: line 2");
            sw.WriteLine($"X: line 3");
            sw.WriteLine($"Y: line 4");
            sw.WriteLine($"Time = {timeRes.TotalMilliseconds}ms");
            sw.Close();
        }
    }
}