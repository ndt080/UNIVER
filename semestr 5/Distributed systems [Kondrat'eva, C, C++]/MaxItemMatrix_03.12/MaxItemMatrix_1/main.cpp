#include <iostream>
#include <vector>
#include <cstdlib>
#include <thread>
#include <algorithm>

using namespace std;

const int n = 100000;
vector<int> vec(n);

void fillVector() {
    srand(time(0));
    for (auto &item: vec) {
        item = rand();
    }
}

template<typename Iterator, typename T>
struct max_item_block {
    void operator()(Iterator first, Iterator last, T &result) {
        result = *max_element(first, last);
    }
};

template<typename Iterator, typename T>
T parallel_vec_max_item(Iterator first, Iterator last, T init) {
    unsigned long const length = distance(first, last);
    if (!length)
        return init;
    unsigned long const min_per_thread = 25;
    unsigned long const max_threads = (length + min_per_thread - 1) / min_per_thread;
    unsigned long const hardware_threads = thread::hardware_concurrency();
    unsigned long const num_threads = min(hardware_threads != 0 ? hardware_threads : 2, max_threads);
    unsigned long const block_size = length / num_threads;
    vector<T> results(num_threads);
    vector<thread> threads(num_threads - 1);
    Iterator block_start = first;
    for (auto i = 0; i < num_threads - 1; ++i) {
        Iterator block_end = block_start;
        advance(block_end, block_size);
        threads[i] = thread(max_item_block<Iterator, T>(), block_start, block_end, ref(results[i]));
        block_start = block_end;
    }
    max_item_block<Iterator, T>()(block_start, last, results[num_threads - 1]);

    for (auto &entry: threads)
        entry.join();
    return *max_element(results.begin(), results.end());
}

int main() {
    fillVector();

    double start_time = clock();
    auto maxItem = parallel_vec_max_item(vec.begin(), vec.end(), 0);
    double end_time = clock();

    double exec_time = (end_time - start_time) / CLOCKS_PER_SEC;
    cout << "Total time: " << exec_time << "s" << "\n";
    printf("Max item: %d", maxItem);
    return 0;
}
