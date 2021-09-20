#include <windows.h>
#include <stdio.h>
#include <conio.h>

/*
 ================================================================================

                        CLIENT: WRITE MESSAGE [PROSECC A]

 ================================================================================
 */
DWORD GET_PID(){
    BOOL   fReturnCode;                                                                 // Код возврата из функций
    DWORD  cbMessages;                                                                  // Размер сообщения в байтах
    DWORD  cbMsgNumber;                                                                 // Количество сообщений в канале Mailslot
    DWORD  cbRead;                                                                      // Количество байт данных, принятых через канал
    HANDLE hMailslot;                                                                   // Идентификатор канала Mailslot
    LPSTR  lpszMailslotName = "\\\\.\\mailslot\\PID";                                   // Имя создаваемого канала
    DWORD PID = 0;

    hMailslot = CreateMailslot(lpszMailslotName, 0, MAILSLOT_WAIT_FOREVER, NULL);       // Создаем канал Mailslot

    if(hMailslot == INVALID_HANDLE_VALUE){
        CloseHandle(hMailslot);
        return 1;
    }

    fReturnCode = GetMailslotInfo(hMailslot, NULL, &cbMessages,     // Определяем состояние канала Mailslot
                                  &cbMsgNumber, NULL);
    if(!fReturnCode){
        CloseHandle(hMailslot);
        return 2;
    }

    if(!ReadFile(hMailslot, &PID, sizeof(DWORD), &cbRead, NULL)){
        CloseHandle(hMailslot);
        return 3;
    }

    CloseHandle(hMailslot);
    return PID;
}


DWORD main(int argc, char *argv[]){
    HANDLE hMailslot;                                                               // Идентификатор канала Mailslot
    char   szMailslotName[256];                                                     // Буфер для имени канала Mailslot
    char   szBuf[512];                                                              // Буфер для передачи данных через канал
    DWORD  cbWritten;                                                               // Количество байт, переданных через канал
    LPVOID pointer;                                                                 // Указатель на строку

    if(argc > 1)                                                                    // Если при запуске было указано имя срвера, указываем его в имени канала Mailslot
        sprintf(szMailslotName, "\\\\%s\\mailslot\\$Channel$", argv[1]);
    else                                                                            // Если имя сервера задано не было, создаем канал с локальным процессом
        strcpy(szMailslotName, "\\\\.\\mailslot\\$Channel$");

    hMailslot = CreateFile(szMailslotName, GENERIC_WRITE,                           // Создаем канал с процессом MSLOTS
                           FILE_SHARE_READ, NULL, OPEN_EXISTING, 0, NULL);

    if(hMailslot == INVALID_HANDLE_VALUE){
        fprintf(stdout,"===================================\n");
        fprintf(stdout,"Connection error. Launch the server\n");
        fprintf(stdout,"===================================\n");

        getch();
        return 0;
    }
    DWORD PID = GET_PID();
    printf("PID SERVER: <%lu>\n", PID);


    HANDLE hProcReceiver = OpenProcess(PROCESS_ALL_ACCESS, FALSE, PID);

    fprintf(stdout,"===================================\n");
    fprintf(stdout,"Connected. Type 'exit' to terminate\n");
    fprintf(stdout,"===================================\n");

    while(1) {
        printf("cmd>");
        gets_s(szBuf, sizeof(szBuf));                                               // Вводим текстовую строку
        // Проверить строку
        // Через VirtuAllocEX выделить память в сервере = вернет указатель
        // WriteProcessMemory (записывает в адресном пространстве процесса по указанному адресу какое-то кодичество байт

        pointer = VirtualAllocEx(hProcReceiver, 0, 512,
                                            MEM_COMMIT | MEM_RESERVE, PAGE_READWRITE);

        if(!WriteProcessMemory(hProcReceiver, pointer, szBuf, 512, 0)){
            fprintf(stdout,"Error WriteProcessMemory\n");
            break;
        }

        if(!WriteFile(hMailslot, &pointer, sizeof(LPVOID),
                      &cbWritten, NULL)) break;

        if(!strcmp(szBuf, "exit"))
            break;
    }

    VirtualFreeEx(hProcReceiver, pointer, 0, MEM_RELEASE);
    CloseHandle(hMailslot);                                                         // Закрываем идентификатор канала
    return 0;
}
