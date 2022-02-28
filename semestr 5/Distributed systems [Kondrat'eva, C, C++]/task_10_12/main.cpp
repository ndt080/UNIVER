#include <omp.h>
#include <iostream>
#include <vector>
#include <chrono>
#include <windows.h>
#include <cmath>
#include <future>
#include <thread>

using namespace std;
using namespace chrono;
using namespace chrono_literals;

const int NUMBER_ROWS = 100;
const int NUMBER_COLUMNS = 100;
const int THREAD_COUNT = 4;

void fill_matrix(vector<vector<int>> &matrix, int row, int col) {
    srand(time(NULL));
    for (int i = 0; i < row; i++)
        for (int j = 0; j < col; j++)
            matrix[i][j] = rand() % 1000;
}

int vector_max(vector<int> &vec)
{
	return *max_element(vec.begin(), vec.end());
}

int matrix_row_max(vector<vector<int>> &matrix, int thread_index, int thread_count)
{
	int n = matrix.size();
	int r = n % thread_count;
	int start = min(thread_index, r) + (n / thread_count) * thread_index;
	int end = min(thread_index + 1, r) + (n / thread_count) * (thread_index + 1);

	vector<int> maximums;
	for (thread_index = start; thread_index < end; ++thread_index) {
		maximums.emplace_back(vector_max(matrix[thread_index]));
	}
	return vector_max(maximums);
}

int matrix_max_seq(vector<vector<int>> &matrix) {
    int max_value = 0;
    int tmp_value = 0;
    for(auto &row : matrix) {
        tmp_value = vector_max(row);
    }
    if(tmp_value > max_value) max_value = tmp_value;
    return max_value;
}


int main(int argc, char **argv)
{
    LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
    double dElapsedTime;
    QueryPerformanceFrequency(&liFrequency);

	vector<vector<int>> matrix(NUMBER_ROWS, vector<int>(NUMBER_COLUMNS));
    fill_matrix(matrix, NUMBER_ROWS, NUMBER_COLUMNS);

    QueryPerformanceCounter(&liStartTime);
    vector<int> maximums(THREAD_COUNT);
    vector<future<int>> futures;
    vector<thread> threads;

	for (auto i = 0; i < THREAD_COUNT; ++i) {
		auto task = packaged_task<int(vector<vector<int>> &, int, int)>(matrix_row_max);
		futures.emplace_back(task.get_future());
		threads.emplace_back(move(task), ref(matrix), i, THREAD_COUNT);
	}

	for (auto i = 0; i < THREAD_COUNT; ++i) {
		maximums[i] = futures[i].get();
		threads[i].join();
	}
	int total_max = vector_max(maximums);
//	int total_max = matrix_max_seq(matrix);
    QueryPerformanceCounter(&liFinishTime);

    dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
    printf("Time:  %f ms\n", dElapsedTime);
    printf("Total max: %d\n", total_max);
	return 0;
}
