using System;
using System.Collections.Generic;
using System.Linq;

namespace lab2
{
    public static class ListDrvExtension
    {
        public static double GetDispersion(this List<int> sequence,double mathExpectation)
        {
            var sum = sequence.Sum(seq => (seq - mathExpectation) * (seq - mathExpectation));
            return  sum / (sequence.Count - 1);
        }

        public static double GetMathExpectation(this List<int> sequence)
        {
            var sum = sequence.Aggregate<int, double>(0, (current, seq) => current + seq);
            return sum/sequence.Count;
        }
        
        public static double CriterionPearsonBernoulli(this List<int> sequence, double p)
        {
            var n = sequence.Count;
            double xi = 0;

            var valuesNum = new[] {0, 0};
            var probabilities = new[] {1-p, p};

            foreach (var seq in sequence)
            {
                valuesNum[seq]++;
            }
            for (int i = 0; i < 2; i++)
            {
                xi += ((double) valuesNum[i] / n - probabilities[i]) * 
                      ((double) valuesNum[i] / n - probabilities[i]) / 
                      probabilities[i];
            }
            return xi;
        }
        
        public static double CriterionPearsonGeometric(this List<int> sequence, double p)
        {
            var n = sequence.Count;
            double xi = 0;
            var maxValue = sequence.Max();

            var valuesNum = new int[maxValue+1];
            var probabilities = new double[maxValue+1];

            for (var i = 0; i <= maxValue; i++)
            {
                probabilities[i] = Math.Pow(1 - p, i) * p;
            }
            foreach (var seq in sequence)
            {
                valuesNum[seq]++;
            }
            for (int i = 0; i <= maxValue; i++)
            {
                xi += ((double) valuesNum[i] / n - probabilities[i]) * 
                      ((double) valuesNum[i] / n - probabilities[i]) / 
                      probabilities[i];
            }
            return xi;
        }
        public static double CriterionPearsonBinomial(this List<int> sequence, params double[] param)
        {
            var m = (int) param[0] + 1;
            var p = param[1];
            var n = sequence.Count;
            double xi = 0;
            
            var valuesNum = new int[m];
            var probabilities = new double[m];

            for (var i = 0; i < m; i++)
            {
                probabilities[i] = Cominations((m-1),i)*Math.Pow(p,i)*Math.Pow(1-p, m-1-i);
            }
            foreach (var seq in sequence)
            {
                valuesNum[seq]++;
            }
            for (var i = 0; i < m; i++)
            {
                xi += ((double) valuesNum[i] / n - probabilities[i]) * 
                      ((double) valuesNum[i] / n - probabilities[i]) / 
                      probabilities[i];
            }
            return xi;
        }
        public static double CriterionPearsonNegBinomial(this List<int> sequence, params double[] param)
        {
            var r = (int) param[0];
            var p = param[1];
            var n = sequence.Count;
            double xi = 0;
            var maxValue = sequence.Max();
            
            var valuesNum = new int[maxValue+1];
            var probabilities = new double[maxValue+1];

            foreach (var seq in sequence)
            {
                valuesNum[seq]++;
            }
            
            for (var i = 0; i <= maxValue; i++)
            {
                probabilities[i] = Cominations(i + r - 1, i) * Math.Pow(p, r) * Math.Pow(1 - p, i);
            }

            for (var i = 0; i <= maxValue; i++)
            {
                xi += ((double) valuesNum[i] / n - probabilities[i]) * 
                      ((double) valuesNum[i] / n - probabilities[i]) / 
                      probabilities[i];
            }
            return xi;
        }
        
        private static long Cominations(int allNumbers, int perGroup)
        {
            if (perGroup < 0 || perGroup > allNumbers)
            {
                return 0;
            }
            if (perGroup == 0 || perGroup == allNumbers)
            {
                return 1;
            }

            perGroup = Math.Min(perGroup, allNumbers - perGroup);
            long c = 1;
            for (int i = 0; i < perGroup; i++)
            {
                c = c * (allNumbers - i) / (i + 1);
            }

            return c;
        }
    }
}