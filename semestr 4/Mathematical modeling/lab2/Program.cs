using System;

namespace lab2
{
    internal static class Program
    {
        private static readonly double[] ParamBern = new []{0.2};
        private static readonly double[] ParamGeom = new []{0.6};
        private static readonly double[] ParamBi = new []{6, 0.3333333};
        private static readonly double[] ParamNegBi = new []{4, 0.2};
        private const int N = 1000;
        
        private static void Main()
        {
            var dvr = new DRV(N);
            var geomModel = DRV.ModelingGeometric(ParamGeom);
            var biModel = DRV.ModelingBernoulli(ParamBern);

            var geomMathExpect = geomModel.GetMathExpectation();
            var geomDispersion = geomModel.GetDispersion(geomMathExpect);
            var geomCrtPearson =  geomModel.CriterionPearsonGeometric(ParamGeom[0]);
            
            Console.WriteLine("log>\tCriterion Pearson's for GEOMETRIC distribution: {0}\n" +
                              "\tCalculated math expectation: {1}\n" +
                              "\tTheoretical math expectation: {2}\n" +
                              "\tCalculated dispersion: {3}\n" +
                              "\tTheoretical dispersion: {4}\n",
                geomCrtPearson, 
                geomMathExpect, 1/ParamGeom[0],
                geomDispersion, (1-ParamGeom[0])/Math.Pow(ParamGeom[0], 2));
            
            var biMathExpect = biModel.GetMathExpectation();
            var biDispersion = biModel.GetDispersion(biMathExpect);
            var biCrtPearson = biModel.CriterionPearsonBernoulli(ParamBern[0]);
            
            Console.WriteLine("log>\tCriterion Pearson's for BERNOULLI distribution: {0}\n" +
                              "\tCalculated math expectation: {1}\n" +
                              "\tTheoretical math expectation: {2}\n" +
                              "\tCalculated dispersion: {3}\n" +
                              "\tTheoretical dispersion: {4}\n",
                biCrtPearson, 
                biMathExpect, ParamBern[0],
                biDispersion, (1-ParamBern[0])*ParamBern[0]);

            var dvr2 = new DRV(N);
            var binomialModel = DRV.ModelingBinomial(ParamBi);
            var negBinomialModel = DRV.ModelingNegBinomial(ParamNegBi);       
            
            var binomialMathExpect = binomialModel.GetMathExpectation();
            var binomialDispersion = binomialModel.GetDispersion(binomialMathExpect);
            var binomialCrtPearson =  binomialModel.CriterionPearsonBinomial(ParamBi);
            
            Console.WriteLine("log>\tCriterion Pearson's for BINOMIAL distribution: {0}\n" +
                              "\tCalculated math expectation: {1}\n" +
                              "\tTheoretical math expectation: {2}\n" +
                              "\tCalculated dispersion: {3}\n" +
                              "\tTheoretical dispersion: {4}\n",
                binomialCrtPearson, 
                binomialMathExpect, ParamBi[0]*ParamBi[1],
                binomialDispersion,ParamBi[0]*ParamBi[1]*(1-ParamBi[1]));
            
            var negBinomialMathExpect = negBinomialModel.GetMathExpectation();
            var negBinomialDispersion = negBinomialModel.GetDispersion(negBinomialMathExpect);
            var negBinomialCrtPearson = negBinomialModel.CriterionPearsonNegBinomial(ParamNegBi);

            Console.WriteLine("log>\tCriterion Pearson's for NEGATIVE BINOMIAL distribution: {0}\n" +
                              "\tCalculated math expectation: {1}\n" +
                              "\tTheoretical math expectation: {2}\n" +
                              "\tCalculated dispersion: {3}\n" +
                              "\tTheoretical dispersion: {4}",
                negBinomialCrtPearson,
                negBinomialMathExpect, ParamNegBi[0] * (1-ParamNegBi[1]) / ParamNegBi[1],
                negBinomialDispersion, ParamNegBi[0] * (1-ParamNegBi[1]) / Math.Pow(ParamNegBi[1], 2));
        }
    }
}