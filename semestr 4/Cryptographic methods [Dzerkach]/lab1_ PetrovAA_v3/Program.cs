using System;
using System.Collections.Generic;
using Extreme.Mathematics;

namespace lab1
{
    internal static class Program
    {
        private static bool SoloveyTest(BigInteger bigInteger, int n, int acc)
        {
            var rand = new Random();
            for (var i = 0; i < acc; i++)
            {
                var r = BigInteger.Random(rand, n);
                var a = r % (bigInteger - 1) + 1;
                var jacobian = (bigInteger + a.CalculateJacobian(bigInteger)) % bigInteger;
                var mod = BigInteger.ModularPow(a, (bigInteger - 1) / 2, bigInteger);
                if (jacobian == 0 || mod != jacobian)
                    return false;
            }

            return true;
        }

        private static bool FermatTest(BigInteger bigInteger, int n, int acc)
        {
            var rand = new Random();
            for (var j = 0; j < acc; ++j)
            {
                var i = BigInteger.Random(rand, n);
                i %= (bigInteger - 1);
                i += 1;
                var result = BigInteger.ModularPow(i, bigInteger - 1, bigInteger);
                if (result != 1)
                {
                    return false;
                }
            }

            return true;
        }
        private static IEnumerable<byte> GeneratePrime(int n)
        {
            byte[] number = new byte[n], temp = new byte[n - 2];
            var rand = new Random();
            for (var i = 0; i < n - 2; ++i)
            {
                temp[i] = (byte) rand.Next(0, 2);
            }

            number[0] = number[^1] = 1;
            temp.CopyTo(number, 1);
            return number;
        }

        private static void Main() {
            Console.WriteLine("Введите битовую длину числа: ");
            var n = int.Parse(Console.ReadLine()!);
            Console.WriteLine("Введите точность: ");
            var acc = Double.Parse(Console.ReadLine()!);
            int rangF = -(int) Math.Log2(acc);
            int rangS = -(int) Math.Log2(acc);
            while (true){
                Console.WriteLine("*********************************************************");
                Console.WriteLine("Выберите тест: ");
                Console.WriteLine("1 - Тест Ферма");
                Console.WriteLine("2 - Тест Соловея-Штрассена");
                Console.WriteLine("0 - ВЫХОД");
                Console.WriteLine("*********************************************************");
                var menu = int.Parse(Console.ReadLine()!);
                if(menu == 1) {
                    while (true) {
                        var number = GeneratePrime(n);
                        var strNumber = string.Join("", number);
                        var maybePrime = new BigInteger().Convert(strNumber);

                        if (FermatTest(maybePrime, n, rangF)) {
                            Console.WriteLine("Чисто {0} простое | точность {1}", maybePrime, acc);
                            break;
                        } else {
                            Console.WriteLine("Число составное. Еще одна попытка");
                        }
                    }
                } else if (menu == 2) {
                    while (true) {
                        var number = GeneratePrime(n);
                        var strNumber = string.Join("", number);
                        var maybePrime = new BigInteger().Convert(strNumber);

                        if (SoloveyTest(maybePrime, n, rangS)) {
                            Console.WriteLine("Чисто {0} простое | точность {1}", maybePrime, acc);
                            break;
                        } else {
                            Console.WriteLine("Число составное. Еще одна попытка");
                        }
                    }
                } else if (menu == 0) {
                    break;
                }
            }
        }
    }
}