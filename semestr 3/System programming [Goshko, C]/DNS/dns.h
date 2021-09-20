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
