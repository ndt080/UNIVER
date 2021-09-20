#include <mpi.h>
#include <iostream>
#include "Process.h"
#include <ctime>
using namespace  std;

class Program {
public:
    static void Main(){
        srand(time(0));
        auto process = MPI::Process();

        int* data1 = nullptr;
        int* data2 = nullptr;
        int* answer = nullptr;

        // Vector length
        const int DATALENGTH = 7;

        if (process.IsMaster()) {
            data1 = new int[DATALENGTH];
            data2 = new int[DATALENGTH];
            answer = new int[process.GetProcessCount()];

            fill(data1, DATALENGTH);
            cout << "********************************************" << endl;
            cout << "MATRIX #1: ";
            for (int i = 0; i < DATALENGTH; i++){
                cout << data1[i] << " ";
            }
            cout << endl;
            
            fill(data2, DATALENGTH);
            cout << "MATRIX #2: ";
            for (int i = 0; i < DATALENGTH; i++){
                cout << data2[i] << " ";
            }
            cout << endl;
            cout << "********************************************" << endl;
        }

        int* sizes = new int[process.GetProcessCount()];
        int* displs = new int[process.GetProcessCount()];

        fill_sizes(sizes, process.GetProcessCount(), DATALENGTH, displs);

        int processCount = process.GetProcessCount();
        const int MAX_SIZE = (DATALENGTH + processCount - 1) / processCount;
        int* slice1 = new int[MAX_SIZE];
        int* slice2 = new int[MAX_SIZE];

        MPI_Scatterv(data1, sizes, displs, MPI_INT, slice1, sizes[process.GetRank()], MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Scatterv(data2, sizes, displs, MPI_INT, slice2, sizes[process.GetRank()], MPI_INT, 0, MPI_COMM_WORLD);

        int sum = 0;
        for (int i = 0; i < sizes[process.GetRank()]; i++){
            sum += slice1[i] * slice2[i];
        }

        MPI_Gather(&sum,1, MPI_INT,answer,1,MPI_INT, MPI::MasterRank, MPI_COMM_WORLD);

        if (process.IsMaster()) {
            int answer_sum = 0;
            for (int i = 0; i < process.GetProcessCount(); i++){
                answer_sum += answer[i];
            }

            cout << "*********************INFO*******************"<< endl;
            cout << "Scalar multiplication: " << answer_sum << endl;

            delete[] data1;
            delete[] data2;
            delete[] answer;
        }

        delete[] slice1;
        delete[] slice2;
        delete[] displs;
        delete[] sizes;
    }

private:
    static void fill(int * data, int length){
        for (auto i = 0; i < length; i++){
            data[i] = rand() % 2;
        }
    }
    static void fill_sizes(int* data, int process_count, int datalen, int* displs) {
        int after = datalen;
        int MAX_PROCESSES = (after / (process_count)) + 1;
        for (auto i = 0; i < process_count; i++) {
            if (after / (process_count - i) < ((double)after / (process_count - i))){
                data[i] = (after / (process_count - i)) + 1;
                after -= (after / (process_count - i)) + 1;
            }else{
                data[i] = (after / (process_count - i));
                after -= (after / (process_count - i));
            }
            if (i != 0)
                displs[i] = displs[i - 1] + data[i - 1];
            else
                displs[i] = 0;
        }
    }

};

int main() {
    Program::Main();
    return 0;
}