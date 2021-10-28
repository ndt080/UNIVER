#include <windows.h>
#include <process.h>
#include <stdio.h>
#include <iostream>
#include <chrono>
#define p 1 // количество дочерних потоков
double pi[p];
int n = 1000000;

unsigned int __stdcall ThreadFunction(LPVOID pvParam)
{
	int nParam = (int)pvParam;
	int i, start;
	double h, sum, x;
	h = 1. / n;
	sum = 0.;
	start = nParam;
	for (i = start; i < n; i += p)
	{
		x = h * i;
		sum += 4. / (1. + x * x);
	}
	pi[nParam] = h * sum;
	return 0;
}

int main()
{
	HANDLE hThreads[p];
	int k;
	double sum;

	auto start = std::chrono::steady_clock::now();

	// создание дочерних потоков
	for (k = 0; k < p; ++k)
	{
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0, ThreadFunction, (LPVOID)k, 0, NULL);
		if (hThreads[k] == NULL) // обработка ошибки
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}

	// ожидание завершения дочерних потоков
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k)
		CloseHandle(hThreads[k]);
	sum = 0.;
	for (k = 0; k < p; ++k)
		sum += pi[k];

	auto end = std::chrono::steady_clock::now();
	std::chrono::duration<double> elapsed_seconds = end - start;
	std::cout << "elapsed time: " << elapsed_seconds.count() << "s\n";

	printf("PI = %.16f\n", sum);
	return 0;
}
