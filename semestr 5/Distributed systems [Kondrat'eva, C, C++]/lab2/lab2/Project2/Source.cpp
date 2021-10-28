#include <windows.h>
#include <process.h>
#include <stdio.h>
#define p 8 // количество дочерних потоков
int n = 100000000;


// тип параметра, передаваемого функции потока
struct SThreadParam
{
	int k;
	double sum;
};

DWORD WINAPI ThreadFunction(LPVOID pvParam)
{
	SThreadParam* param = (SThreadParam*)pvParam;
	int i, start;
	double h, sum, x;
	h = 1. / n;
	sum = 0.;
	start = param->k;
	for (i = start; i < n; i += p)
	{
		x = h * i;
		sum += 4. / (1. + x * x);
	}
	// к глобальной переменной не следует часто обращаться
	param->sum = h * sum;
	return 0;
}

int mainWithThreads()
{
	HANDLE hThreads[p]; // массив дескрипторов потоков
	// массив параметров потоковых функций
	SThreadParam params[p];
	int k;
	double sum;
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;

	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);

	// создание дочерних потоков
	for (k = 0; k < p; ++k)
	{
		params[k].k = k;
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0, (_beginthreadex_proc_type)ThreadFunction, (LPVOID) & (params[k]), 0, NULL);
		if (hThreads[k] == NULL) // обработка ошибки
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}

	// ожидание завершения дочерних потоков
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k) 
	{
		CloseHandle(hThreads[k]);
	}

	sum = 0.;
	for (k = 0; k < p; ++k) 
	{
		sum += params[k].sum;
	}

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;

	printf("\n%f", dElapsedTime );
	printf("\nPI = %.16f\n", sum);
	return 0;
}
 


int mainNoThreads()
{
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;

	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);

	int i, start;
	double h, sum, x;
	h = 1. / n;
	sum = 0.;
	start = 0;
	for (i = start; i < n; i += p)
	{
		x = h * i;
		sum += 4. / (1. + x * x);
	}
	sum = h * sum;

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;

	printf("\n%f", dElapsedTime);
	printf("\nPI = %.16f\n", sum);
	return 0;
}


int main() {
	int count = p;

	printf("\n============= NOT THREADS ===============\n");
	n = 1000000;
	mainNoThreads();
	n = 10000000;
	mainNoThreads();
	n = 100000000;
	mainNoThreads();
	printf("\n=========================================\n\n");

	printf("\n=========== WITH THREADS: %d ============\n", count);
	n = 1000000;
	mainWithThreads();
	n = 10000000;
	mainWithThreads();
	n = 100000000;
	mainWithThreads();
	printf("\n=========================================\n");
	return 0;
}