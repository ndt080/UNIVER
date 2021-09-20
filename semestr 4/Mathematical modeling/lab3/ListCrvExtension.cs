using System;
using System.Collections.Generic;
using System.Linq;

namespace lab2
{
    public static class ListCrvExtension
    {
        public delegate double DistributionDelegate(double x, params double[] param);
        
        public static double GetDispersion(this List<double> sequence, double mathExpectation)
        {
            var sum = sequence.Sum(seq => (seq - mathExpectation) * (seq - mathExpectation));
            return  sum / (sequence.Count - 1);
        }

        public static double GetMathExpectation(this List<double> sequence)
        {
            return sequence.Sum()/sequence.Count;
        }
        
        public static double CriterionPearson(this List<double> sequence, DistributionDelegate distribution, 
                                              params double[] param)
        {
            int j = 0;
            double xi = 0;
            var n = sequence.Count;
            double minValue = sequence.Min();
            double cellSize = (sequence.Max()- minValue) / Crv.NumberSection;
            sequence.Sort();
            
            for (var i = 1; i <= Crv.NumberSection; i++)
            {
                var frequency = 0;
                var border = minValue + (cellSize * i);
                while ((j < n) && (sequence[j] < border))
                {
                    j++;
                    frequency++;
                }
                var probability = distribution( minValue + cellSize * i, param) - 
                                        distribution( minValue + cellSize * (i-1), param);
                xi += (frequency - n * probability) * (frequency - n * probability) / n * probability;
            }
            return xi;
        }
        
        public static double CriterionKolmogorov(this List<double> sequence, DistributionDelegate distribution, 
                                                 params double[] param)
        {
            double supr = 0;
            int n = sequence.Count;
            sequence.Sort();

            for (int i = 0; i < n; i++)
            {
                if (supr < Math.Abs((double) (i + 1) / n - distribution(sequence[i], param)))
                    supr = Math.Abs((double) (i + 1) / n - distribution(sequence[i], param));
            }
            return Math.Sqrt(n) * supr;
        }
        
    }
}