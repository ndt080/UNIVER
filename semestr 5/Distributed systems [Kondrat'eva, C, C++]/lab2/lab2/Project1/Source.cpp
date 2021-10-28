#include <windows.h>
#include <process.h>
#include <stdio.h>
#define p 8 // ���������� �������� �������
int n = 1000000;

// ��� ���������, ������������� ������� ������
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
	// � ���������� ���������� �� ������� ����� ����������
	param->sum = h * sum;
	return 0;
}

int mainWithThreads()
{

	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;
	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);
	HANDLE hThreads[p];			// ������ ������������ �������
	SThreadParam params[p]; 	// ������ ���������� ��������� �������
	int k;
	double sum;

	// �������� �������� �������
	for (k = 0; k < p; ++k)
	{
		params[k].k = k;
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0, (_beginthreadex_proc_type)ThreadFunction, (LPVOID) & (params[k]), 0, NULL);
		if (hThreads[k] == NULL) // ��������� ������
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}

	// �������� ���������� �������� �������
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k)
		CloseHandle(hThreads[k]);
	sum = 0.;
	for (k = 0; k < p; ++k)
		sum += params[k].sum;

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
	printf("\n%f\n", dElapsedTime);
	printf("PI = %.16f\n", sum);
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