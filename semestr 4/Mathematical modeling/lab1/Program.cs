using System;
using System.IO;

namespace lab1
{
    internal static class Program
    {
        private const long A0 = 78125;
        private const long Beta = A0;
        private const long M = 2147483648L;
        private const int NumImplements = 1000;
        private const int K = 256;
        private const double CriticalNum = 16.91898;
        private const double CriticalNumD = 1.63;
        
        private static double[] MultiMethod(double[] a , long beta, int n){
            var aWithStar = new double[n];
            aWithStar[0] = beta*beta % M;
            a[0] = aWithStar[0] / M;
            
            for (int i = 1; i < n; i++){
                aWithStar[i] = aWithStar[i - 1]*Beta % M;
                a[i] = aWithStar[i] / M;
            }
            return a;
        }

        private static double[] MethodMacLarenMarsaglia(double[] a, double[] b, double[] c){
            var temp = new double[K];
            Array.Copy(b,0, temp, 0, K);
            
            for (var i = 0; i < NumImplements; i++){
                var s =((int)(c[i]*K))% K;
                a[i] = temp[s];
                temp[s] = c[(i + K) % K];
            }
            
            return a;
        }

        private static double TestPirson(double[] a, int L){
            Array.Sort(a);
            double xi = 0;
            var i = 0;
            
            for (int j = 1; j <= L; j++){
                var count = 0;
                while ((i < NumImplements) && (a[i] < (double) j / L)){
                    i++;
                    count++;
                }
                xi += Math.Pow(count - (double)(NumImplements / L), 2) / (double)(NumImplements / L);
            }
            
            Console.WriteLine("xi^2={0} | critical number: {1}", xi, CriticalNum );
            return xi;
        }

        private static double TestKolmogorov(double[] a){
            Array.Sort(a);
            double D = 0;
            
            for (int i = 0; i < NumImplements; i++){
                D = Math.Max(D, Math.Abs(((double)i + 1) / NumImplements) - a[i]);
            }
            
            Console.WriteLine("D={0} | critical number: {1}", D*Math.Sqrt(1000), CriticalNumD );
            return D;
        }


        public static void Main(String[] args) {
            var firstSeq = MultiMethod(new double[NumImplements], A0, NumImplements);
            var secondSeq = MultiMethod(new double[K], 2*Beta + 1, K);
            var thirdSeq = new double[NumImplements];
            
            thirdSeq = MethodMacLarenMarsaglia(thirdSeq, secondSeq, firstSeq);

            TestPirson(firstSeq, 10);
            TestPirson(thirdSeq, 10);
            
            TestKolmogorov(firstSeq);
            TestKolmogorov(thirdSeq);
            
            StreamWriter sw = new StreamWriter("data.txt");
            sw.WriteLine("MultiMethod 1#:");
            foreach (var f in firstSeq)
            {
                sw.Write("{0} ", f);
            }
            sw.WriteLine("\nMultiMethod 2#:");
            foreach (var s in secondSeq)
            {
                sw.Write("{0} ", s);
            }
            sw.WriteLine("\nMethodMacLarenMarsaglia:");
            foreach (var th in thirdSeq)
            {
                sw.Write("{0} ", th);
            }
            sw.Close();

        }
    }
}