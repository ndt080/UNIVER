using System;
using Lab_2Petrov;
using Microsoft.VisualStudio.TestPlatform.TestHost;
using NUnit.Framework;

namespace Tests
{
    public class Tests
    {

        [Test]
        public void StandardTest()
        {
            double[,] matrix =
            {
                {4,1,-1},
                {2,-2,1},
                {1,-1,2}
            };
            double[] freeMembers = {3, 1, 5};
            double[] expectedAnswer = {1, 2, 3};
            double[] startState = {2, 2, 2};
            const double accuracy = 1e-8;
            var realAnswer = IterationMethods.StandardGaussSeidel(matrix, freeMembers, startState, accuracy);
            double norm = 0;
            var len = realAnswer.Length;
            for (var i = 0; i < len; i++)
                norm += Math.Pow(realAnswer[i] - expectedAnswer[i], 2);
            Assert.IsTrue(norm<accuracy);
        }
        [Test]
        public void SymmetricTest()
        {
            double[,] matrix =
            {
                {4,1,-1},
                {2,-2,1},
                {1,-1,2}
            };
            double[] freeMembers = {3, 1, 5};
            double[] expectedAnswer = {1, 2, 3};
            double[] startState = {2, 2, 2};
            const double accuracy = 1e-8;
            var realAnswer = IterationMethods.SymmetricGaussSeidel(matrix, freeMembers, startState, accuracy);
            double norm = 0;
            var len = realAnswer.Length;
            for (var i = 0; i < len; i++)
                norm += Math.Pow(realAnswer[i] - expectedAnswer[i], 2);
            Assert.IsTrue(norm<accuracy);
        }
    }
}