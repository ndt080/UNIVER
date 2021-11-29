#include <iostream>
#include <vector>
#include <random>
#include <algorithm>
#include <execution>
#include <ctime>

/*Найти все числа делящиеся на 13*/
static bool check(long x) {
    return bool(x % 13);
}

using namespace std;

int main() {
    long BORDER = 100000000;
    std::srand(time(0));
    //Vector на 100 миллионов значений
    std::vector<long> d;
    unsigned long i = 1;
    for (i; i <= BORDER;) {
        i += 1;
        d.push_back(i);
    }

    auto start_time = std::clock();
    auto no_parallel = count_if(d.begin(), d.end(), check);
    auto end_time = std::clock();
    auto search_time = end_time - start_time;
    /**************************************************/
    std::cout << "Parallel started\n";

    auto start_time_p = std::clock();
    auto yes_parallel = count_if(std::execution::par, d.begin(), d.end(), check);
    auto end_time_p = std::clock();
    auto search_time_p = end_time_p - start_time_p;
    std::cout << "Parallel time: " << search_time_p << "\nNot parallel time: " << search_time << std::endl;
    //Не параллельные вычисления (в режиме макс. производ.) -- 980 tck, параллельные -- 370
    return 0;
}