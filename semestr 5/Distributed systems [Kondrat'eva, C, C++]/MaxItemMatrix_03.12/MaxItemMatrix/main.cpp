#include <iostream>
#include <vector>
#include <cstdlib>
#include <thread>
#include <algorithm>

using namespace std;

const int n = 100000;
vector<int> arr(n);

void fillVector() {
    srand(time(0));
    for (auto &item: arr) {
        item = rand();
    }
}

int main() {
    fillVector();

    double start_time = clock();
    std::sort(arr.begin(), arr.end());
    int maxItem = arr.back();

    double end_time = clock();
    double exec_time = (end_time - start_time) / CLOCKS_PER_SEC;
    cout << "Total time: " << exec_time << "s" << "\n";
    printf("Max item: %d", maxItem);
    return 0;
}
