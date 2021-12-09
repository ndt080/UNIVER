#include <iostream>
#include <future>
#include <vector>

using namespace std;

const int a = 1;
const int b = 10000;
const int thread_number = 4;

vector<future<vector<int>>> futures;
vector<int> futurePrimeNumbers;

bool isPrime(int n) {
    if (n <= 1) return false;

    for (int i = 2; i < n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}

vector<int> findNumberPrimes(int start, int end) {
    vector<int> primeNumbers;
    for (int i = start; i <= end; i++) {
        if (isPrime(i)) {
            primeNumbers.push_back(i);
        }
    }

    return primeNumbers;
}

int main() {
    double start_time = clock();
    int step = (b - a) / thread_number;

    for (int i = 0; i < thread_number; i++) {
        futures.push_back(async(launch::async, findNumberPrimes, step * i + a, step * (i + 1) + a - 1));
    }

    for (auto &future: futures) {
        auto res = future.get();
        futurePrimeNumbers.insert(futurePrimeNumbers.end(), res.begin(), res.end());
    }
    double end_time = clock();
    double exec_time = (end_time - start_time) / CLOCKS_PER_SEC;
    cout << "Total time: " << exec_time << "s" << "\n";
    printf("Prime numbers on the [%d; %d]: %d", a, b, futurePrimeNumbers.size());
}
