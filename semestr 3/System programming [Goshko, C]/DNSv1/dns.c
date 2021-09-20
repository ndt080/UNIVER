#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include "dns.h"

typedef struct item {
    char* hName;
    IPADDRESS ip;
    struct item* next;
} item;


DNSHandle InitDNS( ){
    // создать хэш таблицу, и вернуть на нее (вместо 0 название хэш таблицы)
    item** HashTable = malloc(sizeof(item*) * TABLESIZE);
    for (int i = 0; i < TABLESIZE; ++i) {
        HashTable[i] = NULL;
    }
    return (DNSHandle)HashTable;
}

void LoadHostsFile( DNSHandle hDNS, const char* hostsFilePath ){
    // открываем и построчно читаем файл. адрес хэш таблицы и путь к файлу для чтения)
    // разбираем файл. В каждой строчке ip адрес и  домен. создав структуру для хранения ip и домена, добавляеем в таблицу. + закрыть файл
    FILE* file;
    if (fopen_s(&file, hostsFilePath, "r") != 0)
        return;

    unsigned int ip1 = 0, ip2 = 0, ip3 = 0, ip4 = 0;
    char strName[STRING_DEFAULT_SIZE + 1];

    while (fscanf_s(file, "%d.%d.%d.%d    %s", &ip1, &ip2, &ip3, &ip4, strName, STRING_DEFAULT_SIZE) != EOF)
    {
        item* newelement = malloc(sizeof(item));
        int length = strlen(strName) + 1;
        newelement->hName = malloc(length);
        newelement->ip = (ip1 & 0xFF) << 24 | (ip2 & 0xFF) << 16 | (ip3 & 0xFF) << 8 | (ip4 & 0xFF);
        strcpy_s(newelement->hName, length, strName);

        unsigned int ind = Hash(strName);
        newelement->next = ((item**)hDNS)[ind];
        ((item**)hDNS)[ind] = newelement;
    }

    fclose(file);

}

IPADDRESS DnsLookUp( DNSHandle hDNS, const char* hostName ){
    // (ссылка на таблицу  и домен, ip которого надо найти
    // ищем в таблице значение и возвращаем ip
    unsigned int ind = Hash(hostName);
    item* element = ((item**)hDNS)[ind];

    while (element != NULL){
        if (strcmp(element->hName, hostName) == 0)
            return element->ip;
        element = element->next;
    }

    return INVALID_IP_ADDRESS;
}

void ShutdownDNS( DNSHandle hDNS ){
    // очистка памяти, выделенной по хэш таблицу и все созданные элементы. free(что хотим очистить);
    item** myHashTable = (item**)hDNS;

    for (int i = 0; i < TABLESIZE; ++i)
    {
        item* element = myHashTable[i];
        while (element != NULL)
        {
            item* p = element;
            element = p->next;
            free(p->hName);
            free(p);
        }
    }
    free(myHashTable);
}
