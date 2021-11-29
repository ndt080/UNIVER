using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using MathNet.Numerics;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Double;

namespace lab1
{
    public class CubicSpline
    {
        private static Vector<double> GetY(Func<double, double> F, Vector<double> X)
        {
            var Y = DenseVector.Build.Dense(X.Count, 0);
            for (var i = 0; i < X.Count; i++) Y[i] = F(X[i]);
            return Y;
        }

        private static Vector<double> GetGamma(double h, Vector<double> alpha)
        {
            var n = alpha.Count;
            var cI = 1 / 2;
            var eI = cI;
            Vector<double> b = DenseVector.Build.Dense(n - 2, 0);
            
            for (var i = 1; i < n - 1; i++)
                b[i - 1] = 3 * (alpha[i + 1] + alpha[i - 1] - 2 * alpha[i]) / Math.Pow(h, 2);

            var matrix = DenseMatrix.Build.Dense(n - 2, n - 2);
            
            for (var i = 0; i < n - 2; i++)
            {
                var col = DenseVector.Build.Dense(n - 2, 0);
                col[i] = 2;
            
                if (i == 0) col[i + 1] = cI;
                else if (i == n - 3) col[i - 1] = eI;
                else
                {
                    col[i - 1] = eI;
                    col[i + 1] = cI;
                }
            
                matrix.SetColumn(i, col);
            }
            var gamma = matrix.Solve(b).ToList();
            gamma.Add(0);
            gamma.Insert(0, 0);
            return DenseVector.Build.DenseOfArray(gamma.ToArray());
        }

        private static double GetDeltaByIndex(int i, double h, Vector<double> gamma) =>
            (gamma[i] - gamma[i - 1]) / h;

        private static double GetBetaByIndex(int i, double h, Vector<double> alpha, Vector<double> gamma) =>
            (alpha[i] - alpha[i - 1]) / h + h * (2 * gamma[i] + gamma[i - 1]) / 6;

        private static double Spline(double t, Vector<double> alpha, Vector<double> beta,
            Vector<double> gamma, Vector<double> delta, int i)
        {
            return alpha[i] + beta[i] * t + gamma[i] * Math.Pow(t, 2) / 2 + delta[i] * Math.Pow(t, 3) / 6;
        }

        public static void Run(Func<double, double> F, Vector<double> range, int nodes, double[] xArr)
        {
            using var sw = new StreamWriter(Environment.CurrentDirectory +
                                            $"../../../../CubicSpline/results/{nodes}.txt",
                false, System.Text.Encoding.Default);
            var time = System.Diagnostics.Stopwatch.StartNew();
            Vector<double> X = DenseVector.OfArray(Generate.LinearSpaced(nodes, range[0], range[1]));
            Vector<double> Y = GetY(F, X);
            Vector<double> S = DenseVector.Build.Dense(xArr.Length, 0);
            var h = (range[1] - range[0]) / (nodes - 1);

            time.Start();
            Vector<double> gamma = GetGamma(h, Y);
            var delta = DenseVector.Build.Dense(gamma.Count, 0);
            var beta = DenseVector.Build.Dense(gamma.Count, 0);

            for (var i = 1; i < nodes; i++)
            {
                delta[i] = GetDeltaByIndex(i, h, gamma);
                beta[i] = GetBetaByIndex(i, h, Y, gamma);
            }

            for (var i = 0; i < xArr.Length; i++)
            {
                var index = 1;
                while (xArr[i] > X[index]) index++;

                S[i] = Spline(xArr[i] - X[index], Y, beta, gamma, delta, index);
            }
            time.Stop();

            var timeRes = time.Elapsed;
            Console.WriteLine($"Range = [{string.Join("; ", range)}]");
            Console.WriteLine($"h = {h}");
            Console.WriteLine($"arrX = file: results/{nodes}.txt, row 1; Number of items: {xArr.Length}");
            Console.WriteLine($"S = file: results/{nodes}.txt, row 2; Number of items: {S.Count}");
            Console.WriteLine($"X = file: results/{nodes}.txt, row 3; Number of items: {X.Count}");
            Console.WriteLine($"Y = file: results/{nodes}.txt, row 4; Number of items: {Y.Count}");
            Console.WriteLine($"beta = file: results/{nodes}.txt, row 5; Number of items: {beta.Count}");
            Console.WriteLine($"gamma = file: results/{nodes}.txt, row 6; Number of items: {gamma.Count}");
            Console.WriteLine($"delta = file: results/{nodes}.txt, row 7; Number of items: {delta.Count}");
            Console.WriteLine($"Time = {timeRes.TotalMilliseconds}ms");

            System.Threading.Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");
            sw.WriteLine($"{string.Join(" ", xArr)}");
            sw.WriteLine($"{string.Join(" ", S)}");
            sw.WriteLine($"{string.Join(" ", X)}");
            sw.WriteLine($"{string.Join(" ", Y)}");
            sw.WriteLine($"{string.Join(" ", beta)}");
            sw.WriteLine($"{string.Join(" ", gamma)}");
            sw.WriteLine($"{string.Join(" ", delta)}");
            sw.WriteLine($"arrX: line 1");
            sw.WriteLine($"S: line 2");
            sw.WriteLine($"X: line 3");
            sw.WriteLine($"Y: line 4");
            sw.WriteLine($"beta: line 5");
            sw.WriteLine($"gamma: line 6");
            sw.WriteLine($"delta: line 7");
            sw.WriteLine($"Time = {timeRes.TotalMilliseconds}ms");
            sw.Close();
        }

        public static Vector<double> tridiagonalMatrixAlgorithm(int middle, int matrixSize, Vector<double> columnA,
            Vector<double> columnB, Vector<double> columnC, Vector<double> vecF)
        {
            var alpha = DenseVector.Build.Dense(matrixSize, 0);
            var beta = DenseVector.Build.Dense(matrixSize, 0);
            var mu = DenseVector.Build.Dense(matrixSize, 0);
            var nu = DenseVector.Build.Dense(matrixSize, 0);

            alpha[0] = columnB[0] / columnC[0];
            beta[0] = vecF[0] / columnC[0];

            for (var i = 0; i < middle - 1; i++)
            {
                var denominator = columnC[i + 1] - columnA[i] * alpha[i];
                alpha[i + 1] = columnB[i + 1] / denominator;
                beta[i + 1] = (vecF[i + 1] - columnA[i] * beta[i]) / denominator;
            }

            mu[matrixSize - 1] = columnA[matrixSize - 2] / columnC[matrixSize - 1];
            nu[matrixSize - 1] = vecF[matrixSize - 1] / columnC[matrixSize - 1];


            for (var i = matrixSize - 2; i > middle - 1; i--)
            {
                var denominator = columnC[i] - columnB[i] * mu[i + 1];
                mu[i] = columnA[i - 1] / denominator;
                nu[i] = (vecF[i] - columnB[i] * nu[i + 1]) / denominator;
            }

            var y = DenseVector.Build.Dense(matrixSize, 0);
            y[middle] = (nu[middle] - mu[middle] * beta[middle - 1]) / (1 - mu[middle] * alpha[middle - 1]);

            for (var i = middle - 1; i > -1; i--)
            {
                y[i] = beta[i] - alpha[i] * y[i + 1];
            }

            for (var i = middle; i < matrixSize - 1; i++)
            {
                y[i + 1] = nu[i + 1] - mu[i + 1] * y[i];
            }

            return y;
        }
    }
}