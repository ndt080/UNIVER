#include <winnt.h>

#ifndef PROCESS_WINDOW_H
#define ID_LISTVIEW     1000
#define ID_LIST         1010
#define ID_BUTTON       1002
#define NUM_COLUMNS     6

struct COLUM {
    char* name;
};
const struct COLUM col[] = {"Process", "ID", "Parent PID", "Priority base", "Thread count", "Modules count"};

//menu
#define IDM_VIEW_THREADS    1
#define IDM_VIEW_MODULES    2

GetModulesInfo(int ind);
GetThreadsInfo(int ind);
#endif //PROCESS_WINDOW_H
