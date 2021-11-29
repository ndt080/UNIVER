using System;
using lab1.NewtonMethods;
using MathNet.Numerics;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Double;

namespace lab1
{
    internal static class Program
    {
        private const double Epsilon = 1e-6;

        public static double F(double x)
        {
            return Math.Cos(x) * x;
        }

        private static void Main(string[] args)
        {
            Vector<double> X0 = DenseVector.OfArray(new double[] { -6, 3 });

            Console.WriteLine("=================Newton Method==================");
            NewtonMethod.Run(X0, Epsilon);
            Console.WriteLine("================================================\n");

            Console.WriteLine("=============Discrete Newton Method=============");
            DiscreteNewtonMethod.Run(X0, 1, Epsilon);
            Console.WriteLine("================================================\n");

            X0 = DenseVector.OfArray(new double[] { 0, 22 });
            var xArr = Generate.LinearRange(X0[0], 0.02, X0[1]);

            Console.WriteLine("======Barycentric Equally Spaced Nodes(6)=======");
            Barycentric.Run((x) => x * Math.Cos(x), X0, 6, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("======Barycentric Equally Spaced Nodes(12)======");
            Barycentric.Run((x) => x * Math.Cos(x), X0, 12, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("======Barycentric Equally Spaced Nodes(18)======");
            Barycentric.Run((x) => x * Math.Cos(x), X0, 18, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("==ChebyshevBarycentric Equally Spaced Nodes(6)==");
            ChebyshevBarycentric.Run((x) => x * Math.Cos(x), X0, 6, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("==ChebyshevBarycentric Equally Spaced Nodes(12)=");
            ChebyshevBarycentric.Run((x) => x * Math.Cos(x), X0, 12, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("==ChebyshevBarycentric Equally Spaced Nodes(18)=");
            ChebyshevBarycentric.Run((x) => x * Math.Cos(x), X0, 18, xArr);
            Console.WriteLine("================================================\n");
            
            
            Console.WriteLine("======CubicSpline Equally Spaced Nodes(6)=======");
            CubicSpline.Run((x) => x * Math.Cos(x), X0, 6, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("======CubicSpline Equally Spaced Nodes(12)======");
            CubicSpline.Run((x) => x * Math.Cos(x), X0, 12, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("======CubicSpline Equally Spaced Nodes(18)======");
            CubicSpline.Run((x) => x * Math.Cos(x), X0, 18, xArr);
            Console.WriteLine("================================================\n");
            
            
            Console.WriteLine("=============RmsApproximation(1)===============");
            RmsApproximation.Run((x) => x * Math.Cos(x), X0, 1, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("=============RmsApproximation(2)===============");
            RmsApproximation.Run((x) => x * Math.Cos(x), X0, 2, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("=============RmsApproximation(4)===============");
            RmsApproximation.Run((x) => x * Math.Cos(x), X0, 4, xArr);
            Console.WriteLine("================================================\n");
            
            Console.WriteLine("=============RmsApproximation(6)===============");
            RmsApproximation.Run((x) => x * Math.Cos(x), X0, 6, xArr);
            Console.WriteLine("================================================\n");
            
        }
    }
}