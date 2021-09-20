#pragma once
#include <math.h>

#ifndef _LAB_1_DNS_H_
#define _LAB_1_DNS_H_

typedef   unsigned int DNSHandle;
typedef   unsigned int IPADDRESS;

#define   INVALID_DNS_HANDLE   0
#define   INVALID_IP_ADDRESS   0     // 0.0.0.0

DNSHandle InitDNS( );
void LoadHostsFile( DNSHandle hDNS, const char* hostsFilePath );
void ShutdownDNS( DNSHandle hDNS );
IPADDRESS DnsLookUp( DNSHandle hDNS, const char* hostName );
#endif // _LAB_1_DNS_H_


#define STRING_DEFAULT_SIZE 200
#define TABLESIZE 16384
/**********************************************
FUNCTION:
    Hash()

DESCRIPTION:
    Convert string to unsigned integer

PARAMETERS:
    key: const char* - input string

RETURN VALUE:
    hash-code
**********************************************/
unsigned int Hash(const char* key) {
    unsigned int value = 0;
    while (*key != '\0')
        value += (int)(*(key++));

    return abs(value) % TABLESIZE;
}
