using System;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Double;

namespace lab1.NewtonMethods
{
    public static class NewtonMethod
    {
        private static Vector<double> F(Vector<double> X)
        {
            Vector<double> f = DenseVector.Create(X.Count, 0);
            f[0] = Math.Pow(X[0], 2) * X[1] - Math.Pow(X[1], 2) + 4 * X[0] - 10;
            f[1] = 1 / Math.Pow(3 * X[1] - 2, 2) - X[0] - 5;
            return f;
        }

        private static Matrix<double> dF(Vector<double> X)
        {
            Matrix<double> dF = DenseMatrix.Create(X.Count, X.Count, 0);
            dF[0, 0] = 2 * X[0] * X[1] + 4;
            dF[0, 1] = Math.Pow(X[0], 2) - 2 * X[1];
            dF[1, 0] = -1;
            dF[1, 1] = -6 / Math.Pow(3 * X[1] - 2, 3);
            return dF;
        }

        public static void Run(Vector<double> X, double epsilon)
        {
            Vector<double> dX;
            Vector<double> XLast;
            Matrix<double> W;

            Console.WriteLine($"X0 = [{string.Join("; ", X)}]");
            
            var Dx = double.MaxValue;
            var iter = 0;

            while (Dx > epsilon)
            {
                iter++;
                XLast = X;
                W = dF(XLast);
                X = XLast - W.Inverse() * F(XLast);
                dX = X - XLast;
                Dx = dX.SumMagnitudes();
            }
            
            Console.WriteLine($"Count iteration = {iter}");
            Console.WriteLine($"X = [{string.Join("; ", X)}]");
            Console.WriteLine($"F(x) = [{string.Join("; ", F(X))}]");
        }
    }
}