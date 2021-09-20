using System;
using IronPython.Hosting;
using MathNet.Numerics.LinearAlgebra;
using Microsoft.Scripting.Hosting;

namespace lab5
{
    internal static class Program
{
    private static Vector<double> MonteCarlo(Matrix<double> a, Vector<double> b, int m, int n)
    {
        var rnd = new Random();
        var res = Vector<double>.Build.Dense(3);
        var h = Matrix<double>.Build.DenseIdentity(3);
        var p = Matrix<double>.Build.Dense(3, 3, 1.0 / 3.0); //начальное состояние цепей маркова
        var pi = Vector<double>.Build.Dense(3, 1.0 / 3.0); //матрица переходных вероятностей

        for (var i = 0; i < 3; i++)
        {
            //цепь Маркова (последовательность случайных событий с конечным или счётным числом исходов
            var chain = Vector<double>.Build.Dense(n+1); 
            var q = Vector<double>.Build.Dense(n+1); //вес перехода
            var ksi = Vector<double>.Build.Dense(m); //случайная величина
            for (var j = 0; j < m; j++)
            {
                //Реализуется состояние
                for (var k = 0; k < n + 1; k++)
                {
                    var r = rnd.NextDouble();
                    chain[k] = (r < pi[0]) ? 0 : (r < pi[0] + pi[1] ? 1 : 2);
                }
                //Вычисляем веса цепи Маркова
                q[0] = pi[(int) chain[0]] > 0 ? h[i, (int) chain[0]] / pi[(int) chain[0]] : 0;
                for (var k = 1; k < n + 1; k++)
                {
                    var row = (int) chain[k - 1];
                    var column = (int) chain[k];
                    q[k] = p[row, column] > 0 ? q[k - 1] * a[row, column] / p[row, column] : 0;
                }
                for (var k = 0; k < n + 1; k++)
                {
                    ksi[j] += q[k] * b[(int) chain[k]];
                }
            }
            for (var k = 0; k < m; k++)
            {
                res[i] += ksi[k];
            }
            res[i] /= m;
        }
        return res;
    }

    private static void Main(string[] args)
    {
        //Матрица заранее приведена к необходимому виду
        var A = Matrix<double>.Build.DenseOfRowArrays(
            new double[] {-0.1, 0.1, -0.2},
            new double[] {-0.1, 0.5, -0.3},
            new double[] {0.3, 0.1, -0.3}
        );
        var f = Vector<double>.Build.DenseOfArray(
            new double[] {-3.0, 1.0, 4.0}
        );
        var res = MonteCarlo(A, f, 100, 10000);
        Console.WriteLine("WolframAlpha exec: (-3.07, 1.14, 2.456)");
        Console.WriteLine(res);
        
    }
}
}