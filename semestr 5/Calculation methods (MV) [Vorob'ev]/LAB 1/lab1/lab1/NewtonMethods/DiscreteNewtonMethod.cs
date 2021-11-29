using System;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Double;

namespace lab1.NewtonMethods
{
    public static  class DiscreteNewtonMethod
    {
        private static double F1(double x, double y)
        {
            return Math.Pow(x, 2) * y - Math.Pow(y, 2) + 4 * x- 10;
        }
        
        private static double F2(double x, double y)
        {
            return 1 / Math.Pow(3 * y - 2, 2) - x - 5;
        }
        
        private static Vector<double> F(Vector<double> X)
        {
            Vector<double> f = DenseVector.Create(X.Count, 0);
            f[0] = F1(X[0], X[1]);
            f[1] = F2(X[0], X[1]);
            return f;
        }
        
        private static Matrix<double> dF(Vector<double> X, double h)
        {
            Matrix<double> dF = DenseMatrix.Create(X.Count, X.Count, 0);
            dF[0, 0] = (F1(X[0] + h, X[1]) - F1(X[0], X[1])) / h;
            dF[0, 1] = (F1(X[0], X[1] + h) - F1(X[0], X[1])) / h;
            dF[1, 0] = (F2(X[0] + h, X[1]) - F2(X[0], X[1])) / h;
            dF[1, 1] = (F2(X[0], X[1] + h) - F2(X[0], X[1])) / h;
            return dF;
        }

        public static void Run(Vector<double> X, double h, double epsilon)
        {
            Vector<double> dX;
            Vector<double> XLast;
            Matrix<double> W;

            Console.WriteLine($"X0 = [{string.Join("; ", X)}]");
            Console.WriteLine($"h = {h}");
            
            var Dx = double.MaxValue;
            var iter = 0;

            while (Dx > epsilon)
            {
                iter++;
                XLast = X;
                W = dF(XLast, h);
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