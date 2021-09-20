using Extreme.Mathematics;

namespace lab1
{
    public static class BigIntegerExtension
    {
        public static long CalculateJacobian(this BigInteger a, BigInteger n)
        {
            if (n <= 0 || n % 2 == 0)
                return 0;

            var ans = 1L;

            if (a < 0)
            {
                a = -a;
                if (n % 4 == 3)
                    ans = -ans;
            }

            if (a == 1)
                return ans; 

            while (a != 0)
            {
                if (a < 0)
                {
                    a = -a; 
                    if (n % 4 == 3)
                        ans = -ans;
                }

                while (a % 2 == 0)
                {
                    a /= 2;
                    if (n % 8 == 3 || n % 8 == 5)
                        ans = -ans;
                }

                var temp = a;
                a = n;
                n = temp;

                if (a % 4 == 3 && n % 4 == 3)
                    ans = -ans;

                a %= n;
                if (a > n / 2)
                    a = a - n;
            }
            return (n == 1) ? ans : 0;
        }

        public static BigInteger Convert(this BigInteger bigInt, string str)
        {
            bigInt = 0;
            for (int i = str.Length - 1; i >= 0; --i)
            {
                bigInt += str[i] == '1' ? (new BigInteger(1) << str.Length - (i + 1)) : 0;
            }

            return bigInt;
        }
    }
}