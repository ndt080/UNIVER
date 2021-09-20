#pragma once
#include <math.h>
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
