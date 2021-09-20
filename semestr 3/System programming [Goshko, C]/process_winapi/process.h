#include <windows.h>
#include <tlhelp32.h>
#include <tchar.h>
#include <string.h>
#include <stdio.h>

#ifndef PROCESS_PROCESS_H
#define PROCESS_PROCESS_H


BOOL GetProcessList( );
BOOL ListProcessModules( DWORD dwPID );
BOOL ListProcessThreads( DWORD dwOwnerPID );


// structures
typedef struct tagPROCESSINFO{
    char name[64];
    char ID[64];
    char parentPID[64];
    char priorityBase[128];
    char countThreads[32];
    char countModules[32];
    struct {
        char name[64];
        char executable[MAX_PATH];
        char size[128];
    } MODULESINFO;
    struct {
        char id[64];
        char priorityBase[32];
    } THREADSINFO;
    int sizeThreads;
    int sizeModules;
} PROCESSINFO;

PROCESSINFO processList[1000][1000];


unsigned  int indPRS;
unsigned  int indMDLS;
#endif //PROCESS_PROCESS_H
