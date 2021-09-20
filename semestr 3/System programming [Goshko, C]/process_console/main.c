#include <windows.h>
#include <tlhelp32.h>
#include <tchar.h>
#include <stdio.h>
#include <CommCtrl.h>
#include <commdlg.h>
#include "process.h"
#include "window.h"


int main( int argc, char* argv[] ) {

    GetProcessList( );
    return 0;
}
