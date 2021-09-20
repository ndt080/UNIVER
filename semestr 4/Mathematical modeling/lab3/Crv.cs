using System;
using System.Collections.Generic;
using System.Linq;
using Extreme.Mathematics.Calculus;

namespace lab2
{
    public class Crv
    {
        private int NumberImplementations { get; set; }
        public static int NumberSection{ get; set; }
        internal Crv(int n, int s)
        {
            NumberImplementations = n;
            NumberSection = s;
        }
        
        public List<double> ModelingNormal(params double[] param)
        {
            var p1 = param[0];
            var p2 = param[1];
            var sequence = new List<double>();
            var rnd = new Random();
            
            for (var i = 0; i < NumberImplementations; i++)
            {
                double sum = -6;
                for (int j = 0; j < 12; j++)
                {
                    sum += rnd.NextDouble();
                }
                sequence.Add(p1 + sum * Math.Sqrt(p2));
            }
            return sequence;
        }
        
        public List<double> ModelingLogNormal(params double[] param)
        {
            var p1 = param[0];
            var p2 = param[1];
            var sequence = new List<double>();
            var rnd = new Random();
            
            for (var i = 0; i < NumberImplementations; i++)
            {
                double sum = -6;
                for (int j = 0; j < 12; j++)
                {
                    sum += rnd.NextDouble();
                }
                sequence.Add(Math.Exp(p1 + sum * Math.Sqrt(p2)));
            }
            return sequence;
        }
        
        public List<double> ModelingLogistics(params double[] param)
        {
            var p1 = param[0];
            var p2 = param[1];
            var sequence = new List<double>();
            var rnd = new Random();
            
            for (var i = 0; i < NumberImplementations; i++)
            {
                var y = rnd.NextDouble();
                sequence.Add(p1 + p2 * Math.Log(y / (1 - y)));
            }
            return sequence;
        }
        
        public List<double> ModelingLaplace(params double[] param)
        {
            var p = param[0];
            var sequence = new List<double>();
            var rnd = new Random();
            
            for (var i = 0; i < NumberImplementations; i++)
            {
                var y = rnd.NextDouble();
                if (y < 0.5)
                    sequence.Add(1 / p * Math.Log(2 * y));
                else
                    sequence.Add(-1 / p * Math.Log(2 * (1 - y)));
            }
            
            return sequence;
        }
        
        public List<double> ModelingExponential(params double[] param)
        {
            var p = param[0];
            var sequence = new List<double>();
            var rnd = new Random();
            
            for (var i = 0; i < NumberImplementations; i++)
            {
                var y = rnd.NextDouble();
                sequence.Add(-Math.Log(y) / p);
            }
            return sequence;
        }
        
        public double NormalFunc(double value, params double[] param)
        {
            var m = param[0];
            var s2 = param[1];
            
            AdaptiveIntegrator integrator = new AdaptiveIntegrator();
            Func<double, double> f1 = (x) => Math.Exp(-Math.Pow(x - m, 2) / 2 / s2);
            integrator.Integrate(f1, -1000, value);
            return integrator.Result / Math.Sqrt(2 * Math.PI * s2);
        }
        
        public double LogNormalFunc(double value, params double[] param)
        {
            var m = param[0];
            var s2 = param[0];
            AdaptiveIntegrator integrator = new AdaptiveIntegrator();
            Func<double, double> f1 = (x) =>Math.Exp(-Math.Pow(Math.Log(x) - m, 2) / 2 / s2);
            integrator.Integrate(f1, -1000, value);
            return integrator.Result / Math.Sqrt(2 * Math.PI * s2);
        }
        
        public double LogisticsFunc(double value, params double[] param)
        {
            var p1 = param[0];
            var p2 = param[1];
            return 1 / (1 + Math.Exp(-(value - p1) / p2));
        }

        public double ExpFunc(double value, params double[] param)
        {
            var p = param[0];
            return 1 - Math.Exp(-p * value);
        }
        public double LaplaceFunc(double value, params double[] param)
        {
            var p = param[0];
            if (value < 0)
                return 0.5 * Math.Exp(p * value);
            return 1 - 0.5 * Math.Exp(-p * value);
        }
    }
}