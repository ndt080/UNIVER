#include <iostream>
#include <vector>
#include <cstdlib>
#include <thread>
#include <algorithm>
#include <future>

using namespace std;

const int n = 10000000;
const int max_range_length = 1000;
vector<int> vec(n);

void fillVector() {
    srand(time(0));
    for (auto &item: vec) {
        item = rand();
    }
}

template<typename Iterator>
int parallel_vec_max_item(Iterator first, Iterator last) {
    ptrdiff_t const range_length = last - first;
    if (!range_length) return numeric_limits<int>::min();
    if (range_length <= max_range_length) return *max_element(first, last);

    Iterator const middle = first + (range_length / 2);

    future<int> task = async(&parallel_vec_max_item <Iterator>, first, middle);
    int maxItem1 = 0;
    int maxItem2 = 0;
    try {
        maxItem1 = parallel_vec_max_item(middle, last);
    }
    catch (...) {
        task.wait();
        throw;
    }

    maxItem2 = task.get();
    return max(maxItem1, maxItem2);
}

int main() {
    fillVector();

    double start_time = clock();
    auto maxItem = parallel_vec_max_item(vec.begin(), vec.end());
    double end_time = clock();

    double exec_time = (end_time - start_time) / CLOCKS_PER_SEC;
    cout << "Total time: " << exec_time << "s" << "\n";
    printf("Max item: %d", maxItem);
    return 0;
}
