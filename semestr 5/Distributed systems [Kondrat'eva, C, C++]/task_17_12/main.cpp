#include <omp.h>
#include <iostream>
#include <vector>
#include <chrono>
#include <windows.h>
#include <cmath>

using namespace std;
using namespace chrono;
using namespace chrono_literals;

const int ROW_NUMBER = 100;
const int COLUMN_NUMBER = 100;

void fill_matrix(vector<vector<int>> &matrix, int row, int col) {
    srand(time(NULL));
    for (int i = 0; i < row; i++)
        for (int j = 0; j < col; j++)
            matrix[i][j] = rand() % 1000;
}

int find_row_max(vector<int> &matrix) {
    int max_value = 0;

//#pragma omp parallel for
    for (int i = 0; i < matrix.size(); i++) {
        if (matrix[i] > max_value) {
//#pragma omp critical
            max_value = matrix[i];
        }
    }
    return max_value;
}

float find_min_value(vector<int> &matrix) {
    int min_value = INT_MAX;

//#pragma omp parallel for
    for (int i = 0; i < matrix.size(); i++) {
        if (matrix[i] < min_value) {
//#pragma omp critical
            min_value = matrix[i];
        }
    }
    return min_value;
}

int find_total_max(vector<vector<int>> &matrix, int row) {
    vector<int> min_elements(row);

    for (int i = 0; i < row; i++) {
        min_elements[i] = find_min_value(matrix[i]);
    }
    return find_row_max(min_elements);
}

int find_total_max_nesting(vector<vector<int>> &matrix, int row, int cols) {
    int max_value = INT_MIN;
    omp_set_nested(true);

#pragma omp parallel for reduction(max:max_value)
    for (int i = 0; i < row; i++) {
        int min_value = INT_MAX;

#pragma omp parallel for reduction(min:min_value)
        for (int j = 0; j < cols; j++) {
            min_value = min(min_value, matrix[i][j]);
        }
        max_value = max(max_value, min_value);
    }
    return max_value;
}

int main(int argc, char **argv) {
    LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
    double dElapsedTime;
    QueryPerformanceFrequency(&liFrequency);

    vector<vector<int>> matrix(ROW_NUMBER, vector<int>(COLUMN_NUMBER));
    fill_matrix(matrix, ROW_NUMBER, COLUMN_NUMBER);

    QueryPerformanceCounter(&liStartTime);
    int total_max = find_total_max(matrix, ROW_NUMBER);
//    int total_max = find_total_max_nesting(matrix, ROW_NUMBER, COLUMN_NUMBER);
    QueryPerformanceCounter(&liFinishTime);

    dElapsedTime = 1000.0 * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
    printf("Time:  %f ms\n", dElapsedTime);
    printf("Total max: %d\n", total_max);
}