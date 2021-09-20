#include <windows.h>
#include <windowsx.h>
#include <tlhelp32.h>
#include <tchar.h>
#include <stdio.h>
#include <CommCtrl.h>
#include <commdlg.h>
#include "process.h"
#include "window.h"

HINSTANCE hInst;                                // current instance
HWND hWnd;
HWND hWndList;
HWND T_hWnd;
HWND hListBox;

const WCHAR* TITLE = TEXT("Я ФИГЕЮ С WINAPI");               // The title bar text
const WCHAR* szWindowClass = TEXT((const WCHAR *) "window");            // the main window class name
const WCHAR* childSzWindowClass = TEXT((const WCHAR *) "childWindow");            // the main window class name
const int WIDTH = 800;
const int HEIGHT = 600;

ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
LRESULT CALLBACK    WndProc1(HWND, UINT, WPARAM, LPARAM);


int WINAPI WinMain(HINSTANCE hInstance, // дескриптор экземпляра приложения
                   HINSTANCE hPrevInstance, // в Win32 не используется
                   LPSTR lpCmdLine, // нужен для запуска окна в режиме командной строки
                   int nCmdShow) // режим отображения окна
{
    GetProcessList();
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    MyRegisterClass(hInstance);

    if (!InitInstance(hInstance, nCmdShow)) return FALSE;
    MSG msg;
    while (GetMessage(&msg, NULL, 0, 0)){
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    return (int)msg.wParam;
}

BOOL InitInstance(HINSTANCE hInstance, int nCmdShow){
    // вычисление координат центра экрана
    RECT screen_rect;
    GetWindowRect(GetDesktopWindow(),&screen_rect);
    int x = screen_rect.right / 2-WIDTH/2;
    int y = screen_rect.bottom / 2-HEIGHT/2;

    hInst = hInstance; // Store instance handle in our global variable

    hWnd = CreateWindowW(szWindowClass, TITLE, WS_SYSMENU | WS_MINIMIZEBOX | WS_VISIBLE,
                              x, y, WIDTH, HEIGHT, NULL, NULL, hInstance, NULL);
    if (!hWnd) return FALSE;

    hWndList = CreateWindowEx(0L, WC_LISTVIEW, "SysListView32",
                              WS_VISIBLE | WS_CHILD | WS_BORDER | LVS_REPORT |
                              LVS_EDITLABELS,
                              0, 0, WIDTH-15, HEIGHT-38,
                              hWnd, (HMENU) ID_LISTVIEW, GetModuleHandle(NULL), NULL);
    ListView_SetExtendedListViewStyleEx(hWndList, 0, LVS_EX_FULLROWSELECT | LVS_EX_AUTOSIZECOLUMNS| WS_EX_CLIENTEDGE | LVS_EX_GRIDLINES);
    if (!hWndList) return FALSE;

    ShowWindow(hWnd, nCmdShow);
    UpdateWindow(hWnd);
    return TRUE;
}

ListView (){
    LV_COLUMN lvC;      // List View Column structure
    LV_ITEM lvI;        // List view item structure
    int index;
    char tmp[64];
    memset(&lvC, 0, sizeof(lvC));
    lvC.mask = LVCF_FMT | LVCF_WIDTH | LVCF_TEXT | LVCF_SUBITEM;
    lvC.fmt = LVCFMT_LEFT;  // left align the column
    lvC.cx = (WIDTH-35)/6;            // width of the column, in pixels

    // Add the columns.
    for (index = 0; index < NUM_COLUMNS; index++){
        lvC.iSubItem = index;
        lvC.pszText = col[index].name;
        ListView_InsertColumn(hWndList, index, &lvC);
    }

    // Добавляем актуальные элементы в элемент управления
    // Заполняем структуру LV_ITEM для каждого добавляемого элемента в список
    // The mask specifies the the .pszText, .lParam and .state
    // members of the LV_ITEM structure are valid.
    memset(&lvI, 0, sizeof(lvI));
    lvI.mask = LVIF_TEXT | LVIF_PARAM;

    for(int i=0; i<indPRS; i++){
        lvI.iItem = i;
        lvI.iSubItem = 0;
        lvI.pszText = processList[i][0].name;
        ListView_InsertItem(hWndList, &lvI);
        ListView_SetItemText(hWndList, i, 0, processList[i][0].name);

        lvI.iItem = i;
        lvI.iSubItem = 1;
        lvI.pszText = processList[i][0].ID;
        ListView_InsertItem(hWndList, &lvI);
        ListView_SetItemText(hWndList, i, 1, processList[i][0].ID);

        lvI.iItem = i;
        lvI.iSubItem = 2;
        lvI.pszText = processList[i][0].parentPID;
        ListView_InsertItem(hWndList, &lvI);
        ListView_SetItemText(hWndList, i, 2, processList[i][0].parentPID);

        lvI.iItem = i;
        lvI.iSubItem = 3;
        lvI.pszText = processList[i][0].priorityBase;
        ListView_InsertItem(hWndList, &lvI);
        ListView_SetItemText(hWndList, i, 3, processList[i][0].priorityBase);

        lvI.iItem = i;
        lvI.iSubItem = 4;
        lvI.pszText = processList[i][0].countThreads;
        ListView_InsertItem(hWndList, &lvI);
        ListView_SetItemText(hWndList, i, 4, processList[i][0].countThreads);

        lvI.iItem = i;
        lvI.iSubItem = 5;
        lvI.pszText = processList[i][0].countModules;
        ListView_InsertItem(hWndList, &lvI);
        ListView_SetItemText(hWndList, i, 5, processList[i][0].countModules);
    }
}

// функция обработки сообщений
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam){
    HMENU hMenu = NULL;
    UINT state;
    // выборка и обработка сообщений
    switch (message){
        case WM_PAINT:{
            ListView();
        }
        case WM_COMMAND:
            switch(LOWORD(wParam)) {
                case IDM_VIEW_MODULES:
                    state = GetMenuState(hMenu, IDM_VIEW_MODULES, MF_BYCOMMAND);
                    if (state == MF_CHECKED) {
                        CheckMenuItem(hMenu, IDM_VIEW_MODULES, MF_UNCHECKED);
                    } else {
                        int itemInt = SendMessage(hWndList, LVM_GETNEXTITEM, -1 , LVNI_SELECTED);
                        if(strcmp(processList[itemInt][0].countModules, "0") != 0){
                            GetModulesInfo(itemInt);
                        } else{
                            MessageBox(hWnd, TEXT("This process has no modules!") , "About process modules", (UINT) NULL);
                        }
                        CheckMenuItem(hMenu, IDM_VIEW_MODULES, MF_CHECKED);
                    }
                    break;
                case IDM_VIEW_THREADS:
                    state = GetMenuState(hMenu, IDM_VIEW_THREADS, MF_BYCOMMAND);
                    if (state == MF_CHECKED) {
                        CheckMenuItem(hMenu, IDM_VIEW_THREADS, MF_UNCHECKED);
                    } else {
                        int itemInt = SendMessage(hWndList, LVM_GETNEXTITEM, -1 , LVNI_SELECTED);
                        if(strcmp(processList[itemInt][0].countThreads, "0") != 0){
                            GetThreadsInfo(itemInt);
                        } else{
                            MessageBox(hWnd, TEXT("This process has no threads!"), "About process threads", (UINT) NULL);
                        }
                        CheckMenuItem(hMenu, IDM_VIEW_THREADS, MF_CHECKED);
                    }
                    break;
            }
            break;
        case WM_CONTEXTMENU:
            if ((HWND)wParam == hWndList) {
                hMenu = CreatePopupMenu();
                InsertMenu(hMenu, 0, MF_BYCOMMAND | MF_STRING | MF_ENABLED, IDM_VIEW_THREADS, "About process thread");
                InsertMenu(hMenu, 1, MF_BYCOMMAND | MF_STRING | MF_ENABLED, IDM_VIEW_MODULES, "About process modules");
                TrackPopupMenu(hMenu, TPM_TOPALIGN | TPM_LEFTALIGN, GET_X_LPARAM(lParam), GET_Y_LPARAM(lParam), 0, hWnd, NULL);
            }
            break;
        case WM_DESTROY:
            PostQuitMessage(0);
            break;
        default:
            // все не обработанные сообщения  обработает сама Windows
            return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}
// функция обработки сообщений
LRESULT CALLBACK WndProc1(HWND thWnd, UINT message, WPARAM wParam, LPARAM lParam){
    // выборка и обработка сообщений
    switch (message){
        case WM_CLOSE:
            ShowWindow(thWnd, SW_HIDE);
            break;
        default:
            // все не обработанные сообщения  обработает сама Windows
            return DefWindowProc(thWnd, message, wParam, lParam);
    }
    return 0;
}

 BOOL GetModulesInfo(int ind){
     RECT screen_rect;
     GetWindowRect(GetDesktopWindow(),&screen_rect);
     int x = screen_rect.right / 2-WIDTH/2;
     int y = screen_rect.bottom / 2-HEIGHT/2;
     T_hWnd = CreateWindowW(childSzWindowClass, TEXT((LPCWSTR) "MODULES"), WS_SYSMENU | WS_VISIBLE,
                            x, y, WIDTH*2/3, HEIGHT, NULL, NULL, hInst, NULL);
     if (!T_hWnd) return FALSE;

     hListBox = CreateWindow("listbox", NULL,WS_CHILD | WS_VSCROLL | WS_BORDER | WS_VISIBLE,
                             0, 0, WIDTH-15, HEIGHT-38,T_hWnd, (HMENU) ID_LIST, GetModuleHandle(NULL), NULL);

     if (!hListBox) return FALSE;

     ShowWindow(T_hWnd,1);
     HDC hdc;             // индекс контекста устройства
     PAINTSTRUCT ps;      // структура для рисования
     // Получаем индекс контекста устройства
     hdc = BeginPaint(T_hWnd, &ps);
     SetTextColor( hdc, RGB( 0, 0, 0 ) );

     char buffer[MAX_PATH];

     for(int i = 0; i < processList[ind][0].sizeModules; i++){
         sprintf(buffer, "NAME: %s ", processList[ind][i].MODULESINFO.name);
         SendMessage(hListBox, LB_ADDSTRING, 0,
                     (LPARAM) buffer);
         sprintf(buffer, "PATH: %s ", processList[ind][i].MODULESINFO.executable);
         SendMessage(hListBox, LB_ADDSTRING, 0,
                     (LPARAM) buffer);

         sprintf(buffer, "SIZE: %s ", processList[ind][i].MODULESINFO.size);
         SendMessage(hListBox, LB_ADDSTRING, 0,
                     (LPARAM)buffer);
         SendMessage(hListBox, LB_ADDSTRING, 0,
                     (LPARAM)" ");
     }
     EndPaint(T_hWnd, &ps);
     return TRUE;
}

BOOL GetThreadsInfo(int ind){
    RECT screen_rect;
    GetWindowRect(GetDesktopWindow(),&screen_rect);
    int x = screen_rect.right / 2-WIDTH/2;
    int y = screen_rect.bottom / 2-HEIGHT/2;
    T_hWnd = CreateWindowW(childSzWindowClass, TEXT((LPCWSTR) "THREADS"), WS_SYSMENU | WS_VISIBLE,
                           x, y, WIDTH*2/3, HEIGHT, NULL, NULL, hInst, NULL);
    if (!T_hWnd) return FALSE;

    hListBox = CreateWindow("listbox", NULL,WS_CHILD | WS_VSCROLL | WS_BORDER | WS_VISIBLE,
                            0, 0, WIDTH-15, HEIGHT-38,T_hWnd, (HMENU) ID_LIST, GetModuleHandle(NULL), NULL);

    if (!hListBox) return FALSE;

    ShowWindow(T_hWnd,1);
    HDC hdc;             // индекс контекста устройства
    PAINTSTRUCT ps;      // структура для рисования
    // Получаем индекс контекста устройства
    hdc = BeginPaint(T_hWnd, &ps);
    SetTextColor( hdc, RGB( 0, 0, 0 ) );

    char buffer[MAX_PATH];

    for(int i = 0; i < processList[ind][0].sizeThreads; i++){
        sprintf(buffer, "ID: %s ", processList[ind][i].THREADSINFO.id);
        SendMessage(hListBox, LB_ADDSTRING, 0,
                    (LPARAM) buffer);

        sprintf(buffer, "Priority: %s ", processList[ind][i].THREADSINFO.priorityBase);
        SendMessage(hListBox, LB_ADDSTRING, 0,
                    (LPARAM)buffer);
        SendMessage(hListBox, LB_ADDSTRING, 0,
                    (LPARAM)" ");
    }
    EndPaint(T_hWnd, &ps);
    return TRUE;
}

ATOM MyRegisterClass(HINSTANCE hInstance){
    WNDCLASSEXW wcex;
    wcex.cbSize = sizeof(WNDCLASSEX);
    wcex.style = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc = WndProc;
    wcex.cbClsExtra = 0;
    wcex.cbWndExtra = 0;
    wcex.hInstance = hInstance;
    wcex.hIcon = NULL;
    wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
    wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
    wcex.lpszMenuName = NULL;
    wcex.lpszClassName = szWindowClass;
    wcex.hIconSm = NULL;
    RegisterClassExW(&wcex);

    WNDCLASSEXW wcChild1 = wcex;
    wcChild1.cbSize = sizeof(WNDCLASSEX);
    wcChild1.lpfnWndProc = (WNDPROC)WndProc1;
    wcChild1.lpszClassName = childSzWindowClass;
    wcChild1.style = CS_HREDRAW | CS_VREDRAW | CS_OWNDC;
    wcChild1.hIcon = LoadIcon(NULL, IDI_ASTERISK);
    wcChild1.hCursor = LoadCursor(NULL, IDC_ARROW);
    wcChild1.lpszMenuName = NULL;
    wcex.cbClsExtra = 0;// Нет дополнительных данных класса
    wcex.cbWndExtra = 0;

    RegisterClassExW(&wcChild1);
    return TRUE;
}