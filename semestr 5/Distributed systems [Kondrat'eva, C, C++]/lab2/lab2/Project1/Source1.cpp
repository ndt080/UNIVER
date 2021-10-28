#include <windows.h>
#include <process.h>
#include <stdio.h>
#define p 8 // количество дочерних потоков
int n = 10000000;
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

int main()
{

	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;
	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);
	HANDLE hThreads[p]; // массив дескрипторов потоков
	// массив параметров потоковых функций
	SThreadParam params[p];
	int k;
	double sum;
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
		CloseHandle(hThreads[k]);
	sum = 0.;
	for (k = 0; k < p; ++k)
		sum += params[k].sum;
	printf("PI = %.16f\n", sum);

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
	printf("\n%f", dElapsedTime);
	return 0;
}
