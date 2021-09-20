#include <stdio.h>
#include <iostream>
#include <windows.h>
#include <stdbool.h>
/*#include "../StaticLibFunctionality/static_func.h";
#include "..\Functional\plugins_manager.h"*/

#pragma comment (lib, "functional.lib")

bool locale = true;

int main() {
    bool menu, choosing;
    int choice;

    setlocale(LC_ALL, "Russian");
    printf("=======================================================\n");
    printf("Выберите язык \t|\t Choose the language\n");
    printf("%s\n", "\"0\", русский");
    printf("%s\n", "\"1\" english");
    printf("=======================================================\n");
    scanf_s("%i", &locale);

    menu = true;
    while (menu){
        if (!locale){
            printf("=======================================================\n");
            printf("%s\n", "Меню:");
            printf("%s\n", "1 -> Загрузить плагины");
            printf("%s\n", "2 -> Выгрузить плагины");
            printf("%s\n", "3 -> Показать информацию о загруженных плагинах");
            printf("%s\n", "4 -> Выполнить плагины");
            printf("%s\n", "5 -> Очистить консоль");
            printf("%s\n", "6 -> Выход");
            printf("=======================================================\n");
        }else{
            printf("=======================================================\n");
            printf("%s\n", "Menu:");
            printf("%s\n", "1 -> Load plugins");
            printf("%s\n", "2 -> Unload plugins");
            printf("%s\n", "3 -> Show info about loaded plugins");
            printf("%s\n", "4 -> Execute plugins");
            printf("%s\n", "5 -> Clean console");
            printf("%s\n", "6 -> Exit");
            printf("=======================================================\n");
        }
        choosing = true;
        while (choosing){
            if(!locale)
                printf("%s", "Ваш выбор: ");
            else
                printf("%s", "Your choice: ");
            scanf_s("%d", &choice);

            switch (choice){
                case 1:
                    LoadPlugins(locale);
                    choosing = false;
                    break;
                case 2:
                    UnloadPlugins(locale);
                    choosing = false;
                    break;
                case 3:
                    ShowInfo(locale);
                    choosing = false;
                    break;
                case 4:
                    RunPlugins(locale);
                    choosing = false;
                    break;
                case 5:
                    system("cls");
                    choosing = false;
                    break;
                case 6:
                    Exit(locale);
                    choosing = false;
                    menu = false;
                    break;
                default:
                    if (!locale)
                        printf("%s\n\n", "ОШИБКА: Некорректный выбор.");
                    else
                        printf("%s\n\n", "ERROR: Incorrect choose.");
                    break;
            }
        }
    }

    system("pause>>NUL");
    return 0;
}