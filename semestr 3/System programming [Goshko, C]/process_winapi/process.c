#include "process.h"


BOOL GetProcessList() {
    HANDLE hProcessSnap;
    HANDLE hProcess;
    PROCESSENTRY32 pe32;
    DWORD dwPriorityClass;

    // Делаем снимок
    hProcessSnap = CreateToolhelp32Snapshot( TH32CS_SNAPPROCESS, 0 );
    if( hProcessSnap == INVALID_HANDLE_VALUE ){
        return( FALSE );
    }

    // Устанавливаем размер структуры
    pe32.dwSize = sizeof( PROCESSENTRY32 );

    // Получение информации о первом процессе и завершение в случае неудачи
    if( !Process32First( hProcessSnap, &pe32 ) ){
        CloseHandle( hProcessSnap );          //очищаем снимок
        return( FALSE );
    }

    indPRS = 0;
    do{
        // Получаем класс приоритета
        dwPriorityClass = 0;
        hProcess = OpenProcess( PROCESS_ALL_ACCESS, FALSE, pe32.th32ProcessID );
        if( hProcess != NULL ){
            dwPriorityClass = GetPriorityClass( hProcess );
            CloseHandle( hProcess );
        }
        sprintf( processList[indPRS][0].name,         "%s",    pe32.szExeFile);
        sprintf( processList[indPRS][0].ID,           "%lu",   pe32.th32ProcessID);
        sprintf( processList[indPRS][0].parentPID,    "%lu",   pe32.th32ParentProcessID);
        sprintf( processList[indPRS][0].priorityBase, "%ld",   pe32.pcPriClassBase);
/*        sprintf( processList[indPRS][0].countThreads, "%lu",   pe32.cntThreads);*/
/*        processList[indPRS][0].sizeThreads = (int) pe32.cntThreads;*/

        indPRS++;
        indMDLS=0;
        ListProcessModules( pe32.th32ProcessID );

        sprintf( processList[indPRS][0].countModules, "%u",    indMDLS);
        processList[indPRS][0].sizeModules = (int) indMDLS;

        indMDLS=0;
        ListProcessThreads( pe32.th32ProcessID );

        sprintf( processList[indPRS][0].countThreads, "%u",    indMDLS);
        processList[indPRS][0].sizeThreads = (int) indMDLS;

    } while( Process32Next( hProcessSnap, &pe32 ) );

    CloseHandle( hProcessSnap );
    return( TRUE );
}

BOOL ListProcessModules( DWORD dwPID ) {
    HANDLE hModuleSnap = INVALID_HANDLE_VALUE;
    MODULEENTRY32 me32;

    hModuleSnap = CreateToolhelp32Snapshot( TH32CS_SNAPMODULE, dwPID );
    if( hModuleSnap == INVALID_HANDLE_VALUE ){
        return( FALSE );
    }

    me32.dwSize = sizeof( MODULEENTRY32 );

    if( !Module32First( hModuleSnap, &me32 ) ){
        CloseHandle( hModuleSnap );
        return( FALSE );
    }

    do{
        sprintf( processList[indPRS][indMDLS].MODULESINFO.name,       "%s",       me32.szModule);
        sprintf( processList[indPRS][indMDLS].MODULESINFO.executable, "%s",       me32.szExePath);
        sprintf( processList[indPRS][indMDLS].MODULESINFO.size,       "%lu",      me32.modBaseSize);
        indMDLS++;
    } while( Module32Next( hModuleSnap, &me32 ) );

    CloseHandle( hModuleSnap );
    return( TRUE );
}

BOOL ListProcessThreads( DWORD dwOwnerPID ){
    HANDLE hThreadSnap = INVALID_HANDLE_VALUE;
    THREADENTRY32 te32;

    hThreadSnap = CreateToolhelp32Snapshot( TH32CS_SNAPTHREAD, 0 );
    if( hThreadSnap == INVALID_HANDLE_VALUE )
        return( FALSE );

    te32.dwSize = sizeof(THREADENTRY32);

    if( !Thread32First( hThreadSnap, &te32 ) ){
        CloseHandle( hThreadSnap );
        return( FALSE );
    }

    //смотрим потоки, только связанные с нашим процессом
    do{
        if( te32.th32OwnerProcessID == dwOwnerPID ){
            sprintf( processList[indPRS][indMDLS].THREADSINFO.id,           "%lu",       te32.th32ThreadID);
            sprintf( processList[indPRS][indMDLS].THREADSINFO.priorityBase, "%ld",       te32.tpBasePri);
            indMDLS++;
        }
    } while( Thread32Next(hThreadSnap, &te32 ) );

    CloseHandle( hThreadSnap );
    return( TRUE );
}