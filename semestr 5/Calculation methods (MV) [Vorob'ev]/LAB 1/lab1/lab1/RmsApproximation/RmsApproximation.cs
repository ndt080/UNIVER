using System;
using System.IO;
using System.Linq;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Double;

namespace lab1
{
    public static class RmsApproximation
    {
        private static Vector<double> GetY(Func<double, double> F, Vector<double> X)
        {
            var Y = DenseVector.Build.Dense(X.Count, 0);
            for (var i = 0; i < X.Count; i++) Y[i] = F(X[i]);
            return Y;
        }

        private static Vector<double> GetCoeffs(Func<double, double> F, Vector<double> X, Vector<double> Y, int n)
        {
            var B = DenseVector.Build.Dense(n + 1, 0);
            for (var i = 0; i < n + 1; i++) {
                var pair = X.Zip(Y, (x, y) => new { X = x, Y = y });
                B[i] = pair.Sum(p => p.Y * Math.Pow(p.X, i));
            }

            var matrix = DenseMatrix.Build.Dense(n + 1, n + 1, (i, j) => {
                return X.Sum(x => Math.Pow(x, j + i));
            });
            return matrix.Solve(B);
        }

        private static double Compute(double x0, int n, Vector<double> coeffs)
        {
            var sum = 0.0;
            for (var i = 0; i < n + 1; i++)
                sum += coeffs[i] * Math.Pow(x0, i);
            return sum;
        }

        public static void Run(Func<double, double> F, Vector<double> range, int degree, double[] xArr)
        {
            using var sw = new StreamWriter(Environment.CurrentDirectory +
                                            $"../../../../RmsApproximation/results/{degree}.txt",
                false, System.Text.Encoding.Default);
            var rnd = new Random();
            var time = System.Diagnostics.Stopwatch.StartNew();
            time.Start();
            Vector<double> X = DenseVector.Build.Random(100);
            for (var i = 0; i < X.Count; i++)
                X[i] = rnd.NextDouble() * 22;
            Vector<double> Y = GetY(F, X);
            Vector<double> Rms = DenseVector.Build.Dense(xArr.Length, 0);
            
            var coeffs = GetCoeffs(F, X, Y, degree);
            for (var i = 0; i < xArr.Length; i++)
                Rms[i] = Compute(xArr[i], degree, coeffs);
            time.Stop();

            var timeRes = time.Elapsed;
            Console.WriteLine($"Range = [{string.Join("; ", range)}]");
            Console.WriteLine($"Degree = {degree}");
            Console.WriteLine($"arrX = file: results/{degree}.txt, row 1; Number of items: {xArr.Length}");
            Console.WriteLine($"Rms = file: results/{degree}.txt, row 2; Number of items: {Rms.Count}");
            Console.WriteLine($"X = file: results/{degree}.txt, row 3; Number of items: {X.Count}");
            Console.WriteLine($"Y = file: results/{degree}.txt, row 4; Number of items: {Y.Count}");
            Console.WriteLine($"Time = {timeRes.TotalMilliseconds}ms");
            
            System.Threading.Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");
            sw.WriteLine($"{string.Join(" ", xArr)}");
            sw.WriteLine($"{string.Join(" ", Rms)}");
            sw.WriteLine($"{string.Join(" ", X)}");
            sw.WriteLine($"{string.Join(" ", Y)}");
            sw.WriteLine($"arrX: line 1");
            sw.WriteLine($"Rms: line 2");
            sw.WriteLine($"X: line 3");
            sw.WriteLine($"Y: line 4");
            sw.WriteLine($"Degree = {degree}");
            sw.WriteLine($"Time = {timeRes.TotalMilliseconds}ms");
            sw.Close();
        }
    }
}