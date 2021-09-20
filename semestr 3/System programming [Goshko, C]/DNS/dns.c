#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include "dns.h"
#include "hashtable.h"

typedef struct hashData {
    char* hName;
    IPADDRESS ip;
    struct data* next;
} hashData;


DNSHandle InitDNS( ){
    // создать хэш таблицу, и вернуть на нее
    hashData** HashTable = malloc(sizeof(hashData*) * TABLESIZE);
    for (int i = 0; i < TABLESIZE; ++i) {
        HashTable[i] = NULL;
    }
    return (DNSHandle)HashTable;
}

void LoadHostsFile( DNSHandle hDNS, const char* hostsFilePath ){
    // открываем и построчно читаем файл. адрес хэш таблицы и путь к файлу для чтения)
    // разбираем файл. В каждой строчке ip1.ip2.ip3.ip4 и  hostName. Добавляем их в таблицу
    FILE* file;
    if (fopen_s(&file, hostsFilePath, "r") != 0)
        return;

    unsigned int ip1 = 0, ip2 = 0, ip3 = 0, ip4 = 0;
    char hostName[STRING_DEFAULT_SIZE + 1];

    while (fscanf_s(file, "%d.%d.%d.%d    %s", &ip1, &ip2, &ip3, &ip4, hostName, STRING_DEFAULT_SIZE) != EOF){
        hashData* data = malloc(sizeof(hashData));
        int length = strlen(hostName) + 1;
        data->hName = malloc(length);
        data->ip = (ip1 & 0xFF) << 24 | (ip2 & 0xFF) << 16 | (ip3 & 0xFF) << 8 | (ip4 & 0xFF);
        strcpy_s(data->hName, length, hostName);

        unsigned int index = Hash(hostName);
        p = (struct data *) ((hashData **) hDNS)[index];
        ((hashData**)hDNS)[index] = data;
    }
    fclose(file);
}

IPADDRESS DnsLookUp( DNSHandle hDNS, const char* hostName ){
    // (ссылка на таблицу  и хост, ip которого надо найти
    // ищем в таблице значение и возвращаем ip
    unsigned int index = Hash(hostName);
    hashData* element = ((hashData**)hDNS)[index];

    while (element != NULL){
        if (strcmp(element->hName, hostName) == 0)
            return element->ip;
        element = (hashData *) element->next;
    }
    return INVALID_IP_ADDRESS;
}

void ShutdownDNS( DNSHandle hDNS ){
    // очистка памяти, выделенной по хэш таблицу и все созданные элементы;
    hashData** myHashTable = (hashData**)hDNS;

    for (int i = 0; i < TABLESIZE; ++i){
        hashData* element = myHashTable[i];
        while (element != NULL){
            hashData* p = element;
            element = (hashData *) p->next;
            free(p->hName);
            free(p);
        }
    }
    free(myHashTable);
}
