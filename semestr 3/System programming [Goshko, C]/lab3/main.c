#include <windows.h>
#include <stdio.h>
#include <conio.h>
/*
 ================================================================================

                    SERVER: GET AND PRINT MESSAGE [PROSECC B]

 ================================================================================
 */
void SEND_PID(DWORD PID){
    HANDLE hMailslot;                                                               // Идентификатор канала Mailslot
    LPSTR  lpszMailslotName = "\\\\.\\mailslot\\PID";                               // Имя создаваемого канала
    DWORD  cbWritten;                                                               // Количество байт, переданных через канал
    hMailslot = CreateFile(lpszMailslotName, GENERIC_WRITE,                         // Создаем канал с процессом MSLOTS
                           FILE_SHARE_READ, NULL, OPEN_EXISTING, 0, NULL);

    if(hMailslot == INVALID_HANDLE_VALUE){
        CloseHandle(hMailslot);
        return;
    }
    WriteFile(hMailslot, &PID, sizeof(DWORD), &cbWritten, NULL);

    CloseHandle(hMailslot);
}


int main(){
    BOOL   fReturnCode;                                                                 // Код возврата из функций
    DWORD  cbMessages;                                                                  // Размер сообщения в байтах
    DWORD  cbMsgNumber;                                                                 // Количество сообщений в канале Mailslot
    DWORD  cbRead;                                                                      // Количество байт данных, принятых через канал
    HANDLE hMailslot;                                                                   // Идентификатор канала Mailslot
    DWORD  PID              = GetCurrentProcessId();
    LPSTR  lpszMailslotName = "\\\\.\\mailslot\\$Channel$";                             // Имя создаваемого канала
    LPVOID pointer = NULL;

    char   szBuf[512];                                                                  // Буфер для передачи данных через канал
    hMailslot = CreateMailslot(lpszMailslotName, 0, MAILSLOT_WAIT_FOREVER, NULL);       // Создаем канал Mailslot


    if(hMailslot == INVALID_HANDLE_VALUE){
        return 0;
    }
    fprintf(stdout,"================\n");
    fprintf(stdout,"Mailslot created\n");
    fprintf(stdout,"PID: <%lu>\n", PID);
    fprintf(stdout,"================\n");

    while(1){
        SEND_PID(PID);
        fReturnCode = GetMailslotInfo(hMailslot, NULL, &cbMessages,     // Определяем состояние канала Mailslot
                                      &cbMsgNumber, NULL);
        if(!fReturnCode){
            break;
        }

        if(cbMsgNumber != 0){                                                           // Если в канале есть сообщения, читаем первое из них и выводим на экран
            if(ReadFile(hMailslot, &pointer, sizeof(LPVOID), &cbRead, NULL)){
                printf("Message: <%s>\n", (LPSTR) pointer);
                if(!strcmp((LPSTR) pointer, "exit"))
                    break;
            }else{
                break;
            }
        }
        Sleep(500);                                                       // Выполняем задержку на 500 миллисекунд
    }
    CloseHandle(hMailslot);                                                           // Закрываем идентификатор канала Mailslot
    return 0;
}