#include <iostream>
#include <vector>

using namespace std;

const int a = 1;
const int b = 1000000;
vector<int> primeNumbers;

bool isPrime(int n) {
    if (n <= 1) return false;

    for (int i = 2; i < n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}

int main() {
    double start_time = clock();
    for (int i = a; i <= b; i++) {
        if (isPrime(i)) {
            primeNumbers.push_back(i);
        }
    }
    double end_time = clock();
    double exec_time = (end_time - start_time) / CLOCKS_PER_SEC;
    cout << "Total time: " << exec_time << "s" << "\n";
    printf("Prime numbers on the [%d; %d]: %d", a, b, primeNumbers.size());
}
