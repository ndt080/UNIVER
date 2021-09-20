#include <windows.h>
#include <tlhelp32.h>
#include <tchar.h>
#include <stdio.h>


#ifndef PROCESS_PROCESS_H
#define PROCESS_PROCESS_H

BOOL GetProcessList( );
BOOL ListProcessModules( DWORD dwPID );
BOOL ListProcessThreads( DWORD dwOwnerPID );


#endif //PROCESS_PROCESS_H
