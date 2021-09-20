using System;
using Extreme.Statistics.Distributions;

namespace lab2
{
    internal static class Program
    {
        private static readonly double[] ParamNorm = new []{0, 16.0};
        private static readonly double[] ParamNorm2 = new []{-5, 25.0};
        private static readonly double[] ParamLogNorm = new []{2, 16.0};
        private static readonly double[] ParamLog = new []{1, 1.0};
        private static readonly double[] ParamLpl = new []{1.0};
        private static readonly double[] ParamExp = new []{4.0};
        private const int N = 1000;
        private static readonly int S = (int) Math.Log2(N);

        private static void Main()
        {
            var crv = new Crv(N, S);
            var normalModel = crv.ModelingNormal(ParamNorm);
            var logNormalModel = crv.ModelingLogNormal(ParamLogNorm);
            var logisticsModel = crv.ModelingLogistics(ParamLog);

            var normalMathExpect = normalModel.GetMathExpectation();
            var normalDispersion = normalModel.GetDispersion(normalMathExpect);
            var normalCrtPearson =  normalModel.CriterionPearson(crv.NormalFunc, ParamNorm);
            var normalCrtKolmogorov =  normalModel.CriterionKolmogorov(crv.NormalFunc, ParamNorm);
            
            Console.WriteLine("log>\tNORMAL DISTRIBUTION\n" +
                              "\tCriterion Pearson's: {0}\n" +
                              "\tCriterion Kolmogorov's: {1}\n" +
                              "\tCalculated math expectation: {2}\n" +
                              "\tTheoretical math expectation: {3}\n" +
                              "\tCalculated dispersion: {4}\n" +
                              "\tTheoretical dispersion: {5}\n",
                
                normalCrtPearson,
                normalCrtKolmogorov,
                normalMathExpect, ParamNorm[0],
                normalDispersion, ParamNorm[1]);

            var logNormalMathExpect = logNormalModel.GetMathExpectation();
            var logNormalDispersion = logNormalModel.GetDispersion(logNormalMathExpect);
            var logNormalCrtPearson =  logNormalModel.CriterionPearson(crv.LogNormalFunc, ParamLogNorm);
            var logNormalCrtKolmogorov =  logNormalModel.CriterionKolmogorov(crv.LogNormalFunc, ParamLogNorm);
            
            Console.WriteLine("log>\tLOGNORMAL DISTRIBUTION\n" +
                              "\tCriterion Pearson's: {0}\n" +
                              "\tCriterion Kolmogorov's: {1}\n" +
                              "\tCalculated math expectation: {2}\n" +
                              "\tTheoretical math expectation: {3}\n" +
                              "\tCalculated dispersion: {4}\n" +
                              "\tTheoretical dispersion: {5}\n",
                
                logNormalCrtPearson,
                logNormalCrtKolmogorov,
                logNormalMathExpect, Math.Exp(ParamLogNorm[0] + (ParamLogNorm[1] * ParamLogNorm[1]) / 2),
                logNormalDispersion, (Math.Exp(ParamLogNorm[1]*ParamLogNorm[1]) - 1) * 
                                     Math.Exp(2 * ParamLogNorm[0] + (ParamLogNorm[1]*ParamLogNorm[1])));            
            
            var logisticsMathExpect = logisticsModel.GetMathExpectation();
            var logisticsDispersion = logisticsModel.GetDispersion(logisticsMathExpect);
            var logisticsCrtPearson = logisticsModel.CriterionPearson(crv.LogisticsFunc, ParamLog);
            var logisticsCrtKolmogorov = logisticsModel.CriterionKolmogorov(crv.LogisticsFunc, ParamLog);
            
            Console.WriteLine("log>\tLOGISTICS DISTRIBUTION\n" +
                              "\tCriterion Pearson's: {0}\n" +
                              "\tCriterion Kolmogorov's: {1}\n" +
                              "\tCalculated math expectation: {2}\n" +
                              "\tTheoretical math expectation: {3}\n" +
                              "\tCalculated dispersion: {4}\n" +
                              "\tTheoretical dispersion: {5}\n",
                
                logisticsCrtPearson,
                logisticsCrtKolmogorov,
                logisticsMathExpect, ParamLog[0],
                logisticsDispersion, Math.PI*Math.PI*ParamLog[1]*ParamLog[1]/3);


            Console.WriteLine("\n#####################################################\n");
            
            var crv2 = new Crv(N, S);
            var normalModel2 = crv2.ModelingNormal(ParamNorm2);
            var laplaceModel = crv2.ModelingLaplace(ParamLpl);
            var expModel = crv2.ModelingExponential(ParamExp);
            
            var normal2MathExpect = normalModel2.GetMathExpectation();
            var normal2Dispersion = normalModel2.GetDispersion(normal2MathExpect);
            var normal2CrtPearson =  normalModel2.CriterionPearson(crv2.NormalFunc, ParamNorm2);
            var normal2CrtKolmogorov =  normalModel2.CriterionKolmogorov(crv2.NormalFunc, ParamNorm2);
            
            Console.WriteLine("log>\tNORMAL DISTRIBUTION #2\n" +
                              "\tCriterion Pearson's: {0}\n" +
                              "\tCriterion Kolmogorov's: {1}\n" +
                              "\tCalculated math expectation: {2}\n" +
                              "\tTheoretical math expectation: {3}\n" +
                              "\tCalculated dispersion: {4}\n" +
                              "\tTheoretical dispersion: {5}\n",
                normal2CrtPearson,
                normal2CrtKolmogorov,
                normal2MathExpect, ParamNorm2[0],
                normal2Dispersion, ParamNorm2[1]);
            
            var laplaceMathExpect = laplaceModel.GetMathExpectation();
            var laplaceDispersion = laplaceModel.GetDispersion(laplaceMathExpect);
            var laplaceCrtPearson =  laplaceModel.CriterionPearson(crv2.LaplaceFunc, ParamLpl);
            var laplaceCrtKolmogorov =  laplaceModel.CriterionKolmogorov(crv2.LaplaceFunc, ParamLpl);
            
            Console.WriteLine("log>\tLAPLACE DISTRIBUTION\n" +
                              "\tCriterion Pearson's: {0}\n" +
                              "\tCriterion Kolmogorov's: {1}\n" +
                              "\tCalculated math expectation: {2}\n" +
                              "\tTheoretical math expectation: {3}\n" +
                              "\tCalculated dispersion: {4}\n" +
                              "\tTheoretical dispersion: {5}\n",
                laplaceCrtPearson,
                laplaceCrtKolmogorov,
                laplaceMathExpect, 0,
                laplaceDispersion, 2/(ParamLpl[0]*ParamLpl[0]));
            
            var expMathExpect = expModel.GetMathExpectation();
            var expDispersion = expModel.GetDispersion(expMathExpect);
            var expCrtPearson =  expModel.CriterionPearson(crv2.ExpFunc, ParamExp);
            var expCrtKolmogorov =  expModel.CriterionKolmogorov(crv2.ExpFunc, ParamExp);
            
            Console.WriteLine("log>\tEXPONENTIAL DISTRIBUTION\n" +
                              "\tCriterion Pearson's: {0}\n" +
                              "\tCriterion Kolmogorov's: {1}\n" +
                              "\tCalculated math expectation: {2}\n" +
                              "\tTheoretical math expectation: {3}\n" +
                              "\tCalculated dispersion: {4}\n" +
                              "\tTheoretical dispersion: {5}\n",
                expCrtPearson,
                expCrtKolmogorov,
                expMathExpect, 1/ParamExp[0],
                expDispersion, 1/Math.Pow(2, ParamExp[0]));
        }
    }
}