using System;

namespace Lab_2Petrov
{
    public static class IterationMethods
    {
        private static bool StopCondition(double[,] matrix, double[] freeMembers, double[] state, double accuracy)
        {
            double norm=0;
            int len = freeMembers.Length;
            for (int i = 0; i < len; i++)
            {
                double currentValue=0;
                for (int j = 0; j < len; j++)
                {
                    currentValue += matrix[i, j] * state[j];
                }

                norm += Math.Pow(freeMembers[i] - currentValue,2);
            }

            return norm < accuracy;
        }
        public static double[] StandardGaussSeidel(double[,] matrix, double[] freeMembers, double[] startState, double accuracy)
        {
            double[] state = startState;
            int len = freeMembers.Length;
            while (!StopCondition(matrix, freeMembers, state, accuracy))
            {
                double[] previousState = state;
                for (int i = 0; i < len; i++)
                {
                    double currentSum = 0;
                    double previousSum = 0;
                    for (int j = 0; j < i; j++)
                    {
                        currentSum += matrix[i, j] * state[j];
                    }

                    for (int j = i + 1; j < len; j++)
                    {
                        previousSum += matrix[i, j] * previousState[j];
                    }

                    state[i] = (freeMembers[i] - currentSum - previousSum) / matrix[i, i];
                }
            }

            return state;
        }
        
        public static double[] SymmetricGaussSeidel(double[,] matrix, double[] freeMembers, double[] startState, double accuracy)
        {
            double[] state = startState;
            int len = freeMembers.Length;
            while (!StopCondition(matrix, freeMembers, state, accuracy))
            {
                double[] previousState = state;
                for (int i = 0; i < len; i++)
                {
                    double currentSum = 0;
                    double previousSum = 0;
                    for (int j = 0; j < i; j++)
                    {
                        currentSum += matrix[i, j] * state[j];
                    }

                    for (int j = i + 1; j < len; j++)
                    {
                        previousSum += matrix[i, j] * previousState[j];
                    }

                    state[i] = (freeMembers[i] - currentSum - previousSum) / matrix[i, i];
                }
                
                previousState = state;
                for (int i = len-1; i >=0; i--)
                {
                    double currentSum = 0;
                    double previousSum = 0;
                    for (int j = 0; j < i; j++)
                    {
                        previousSum += matrix[i, j] * previousState[j];
                    }

                    for (int j = i + 1; j < len; j++)
                    {
                        currentSum += matrix[i, j] * state[j];
                    }

                    state[i] = (freeMembers[i] - currentSum - previousSum) / matrix[i, i];
                }
            }
            return state;
        }

    }
    
}