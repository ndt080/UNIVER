#include <iostream>
#include <windows.h>
#include <process.h>
#include <stdio.h>
#include <math.h>
#include <cmath>

#define p 4 // количество дочерних потоков
double a = 1; //Левая граница
double b = 2; //Правая граница
double n = 1000;

double func2(double x) {
    return (10 - x);
}

double func(double x) {
	return (2 * x * x * x - 7 * x + 4);
}

double func1(double x) {
	return (1 / x) * sin(3.14 * x / 2);
}

// тип параметра, передаваемой функции потока
typedef double(*pointFunc)(double);

// тип параметра, передаваемого функции потока
struct SThreadParam
{
    int k;
	pointFunc f;
    double result;
};

DWORD WINAPI ThreadFunction(LPVOID pvParam)
{
    SThreadParam* param = (SThreadParam*)pvParam;
    double h = (b - a) / n;
    double sum = 0.;
    int start = param->k;
    for (int i = start; i <= n - 1; i += p)
    {
        double x = a + i * h;
        sum += param->f(x);
    }

    param->result = h * sum;
	return 0;
}

int main()
{
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;
	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);
	HANDLE hThreads[p];			// массив дескрипторов потоков
	SThreadParam params[p]; 	// массив параметров потоковых функций
	int k;
	double result;

	// создание дочерних потоков
	for (k = 0; k < p; ++k)
	{
		params[k].k = k;
		params[k].f = &func;
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0, (_beginthreadex_proc_type)ThreadFunction, (LPVOID) & (params[k]), 0, NULL);
		if (hThreads[k] == NULL) // обработка ошибки
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}

	// ожидание завершения дочерних потоков
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k) {
		CloseHandle(hThreads[k]);
	}
	result = 0.;
	for (k = 0; k < p; ++k) {
		result += params[k].result;
	}

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
	printf("\n%f\n", dElapsedTime);
	printf("\nIntegral: %f\n", result);
	return 0;
}

/*
double LeftRectangle(pointFunc f, double a, double b, int n)
{
	double h = (b - a) / n;
	double sum = 0.;
	for (int i = 0; i <= n - 1; i++)
	{
		double x = a + i * h;
		sum += f(x);
	}

	double result = h * sum;
	return result;
}



int mainN()
{
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;

	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);

	double result = LeftRectangle(func, a, b, n);

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;

	printf("\n%f\n", dElapsedTime);
	printf("\nIntegral: %f\n", result);
	return 0;
}

*/