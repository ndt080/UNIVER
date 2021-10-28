#include <windows.h>
#include <process.h>
#include <stdio.h>

#define p 1
int n = 100000000;
double pi = 0.; // ��������� ����������������� ������
CRITICAL_SECTION cs;

DWORD WINAPI ThreadFunction(LPVOID pvParam)
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

	// ����������� ������
	EnterCriticalSection(&cs);
	pi += h * sum;
	LeaveCriticalSection(&cs);

	return 0;
}

int mainWithThreads()
{
	HANDLE hThreads[p];
	int k;
	InitializeCriticalSection(&cs);
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	double dElapsedTime;

	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);

	// �������� �������� �������
	for (k = 0; k < p; ++k)
	{
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0, (_beginthreadex_proc_type)ThreadFunction, (LPVOID)k, 0, NULL);
		if (hThreads[k] == NULL) // ��������� ������
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}

	// �������� ���������� �������� �������
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k) 
	{
		CloseHandle(hThreads[k]);
	}

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;

	printf("\n%f", dElapsedTime);
	DeleteCriticalSection(&cs); 		// ������������ ��������, ������� ����������� �������
	printf("\nPI = %.16f\n", pi);
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

	// ����������� ������
	pi += h * sum;

	QueryPerformanceCounter(&liFinishTime);
	dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;

	printf("\n%f", dElapsedTime);
	printf("\nPI = %.16f\n", pi);
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