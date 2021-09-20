#include "Process.h"
#include <mpi.h>
#include <iostream>
#include <exception>
#include <iomanip>
#include <random>
#include <ctime>

using namespace std;
class Program
{

public:
    static void Main()
    {

        auto process = MPI::Process();

        int K, L, M;

        if (process.IsMaster())
        {
            std::cout << "\nInput the parameters K, L, M\n";
            std::cin >> K >> L >> M;
        }

        MPI_Bcast(&K, 1, MPI_DOUBLE, MPI::MasterRank, MPI_COMM_WORLD); //делюсь информацией
        MPI_Bcast(&M, 1, MPI_DOUBLE, MPI::MasterRank, MPI_COMM_WORLD);

        double *firstMatrix = nullptr, *secondMatrix = nullptr,
                *firstSlice = nullptr, *secondSlice = nullptr,
                *answer = nullptr, *result = nullptr;

        int *firstSlices = nullptr, *firstPosition = nullptr,
                *secondSlices = nullptr, *secondPosition = nullptr,
                sliceSizes[2];
        double timestart, timefinish;
        if (process.IsMaster())
        {
            firstMatrix = generateMatrix(L, K);
            secondMatrix = generateMatrix(L, M);

            std::cout << "\nFirst Matrix:\n";
            printTransplantMatrix(firstMatrix, L, K);
            std::cout << "\nSecond Matrix:\n";
            printMatrix(secondMatrix, L, M);

            timestart = MPI_Wtime();
            countSlices(process.GetProcessCount(), L, K, firstSlices, firstPosition);
            countSlices(process.GetProcessCount(), L, M, secondSlices, secondPosition);

            for (int i = 1; i < process.GetProcessCount(); i++)
            {
                sliceSizes[0] = firstSlices[i];
                sliceSizes[1] = secondSlices[i];

                MPI_Send
                        (
                                sliceSizes,
                                2,
                                MPI_INT,
                                i,
                                0,
                                MPI_COMM_WORLD
                        );
            }

            sliceSizes[0] = firstSlices[0];
            sliceSizes[1] = secondSlices[0];
        }
        else
        {
            MPI_Recv
                    (
                            sliceSizes,
                            2,
                            MPI_INT,
                            MPI::MasterRank,
                            0,
                            MPI_COMM_WORLD,
                            MPI_STATUSES_IGNORE
                    );
        }

        firstSlice = new double[sliceSizes[0]];
        secondSlice = new double[sliceSizes[1]];
        scatterMatrixt(firstMatrix, firstSlice, firstSlices, firstPosition, sliceSizes[0]);
        scatterMatrixt(secondMatrix, secondSlice, secondSlices, secondPosition, sliceSizes[1]);

        result = multiply(firstSlice, secondSlice, K, sliceSizes[0] / K, M);

        if (process.IsMaster())
            answer = new double[K * M];

        reduce(answer, result, K * M);


        if (process.IsMaster())
        {
            std::cout << "\nTime: " << MPI_Wtime() - timestart << "\n";
            std::cout << "\nResult:\n";
            printMatrix(answer, K, M);
        }


        delete[] firstSlice;
        delete[] secondSlice;
        delete[] result;

        if (process.IsMaster())
        {
            delete[] firstMatrix;
            delete[] secondMatrix;
            delete[] firstSlices;
            delete[] firstPosition;
            delete[] secondSlices;
            delete[] secondPosition;
            delete[] answer;
        }

    }


private:

    static double* generateMatrix(int N, int M)
    {
        double* result = new double[N * M];

        for (int i = 0; i < M*N; i++)
        {
            result[i] = (rand() % 2);
        }

        return result;
    }

    static void printTransplantMatrix(double* matrix, int N, int M)
    {
        for (int j = 0; j < M; j++)
        {
            for (int i = 0; i < N; i++)
            {
                std::cout << matrix[i * M + j] << std::setw(5);
            }
            std::cout << '\n';
        }
    }

    static void reduce(double* answer, double* part, int partSize)
    {
        MPI_Reduce
                (
                        part,
                        answer,
                        partSize,
                        MPI_DOUBLE,
                        MPI_SUM,
                        MPI::MasterRank,
                        MPI_COMM_WORLD
                );
    }

    static double* multiply(double* firstMatrix, double* secondMatrix, int K, int dimC, int M)
    {
        double* answer = new double[K * M];

        for (int i = 0; i < K; i++)
        {
            for (int j = 0; j < M; j++)
            {
                answer[i * M + j] = 0;

                for (int k = 0; k < dimC; k++)
                {
                    answer[i * M + j] += firstMatrix[k * K + i] * secondMatrix[k * M + j];
                }
            }
        }

        return answer;
    }

    static void printMatrix(double* matrix, int N, int M)
    {
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                std::cout << matrix[i * M + j] << std::setw(5);
            }
            std::cout << '\n';
        }
    }

    static void scatterMatrixt(double* matrix, double* slice, int* slices, int* positions, int sliseSize)
    {
        MPI_Scatterv
                (
                        matrix,
                        slices,
                        positions,
                        MPI_DOUBLE,
                        slice,
                        sliseSize,
                        MPI_DOUBLE,
                        MPI::MasterRank,
                        MPI_COMM_WORLD
                );
    }

    static void countSlices(int processNumber, int N, int M, int*& slices, int*& positions)
    {
        slices = new int[processNumber];
        positions = new int[processNumber];

        std::fill(slices, slices + processNumber, (N / processNumber) * M);

        for (int i = 0; i < processNumber; i++)
        {
            positions[i] = i * (N / processNumber) * M;
        }

        for (int i = 1, shift = 0; i < (N % processNumber) + 1; i++, shift += M)
        {
            slices[i] += M;
            positions[i] += shift;
        }
    }

};

int main(){
    Program::Main();

    return 0;
}