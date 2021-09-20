#include <string.h>
#include <stdio.h>
#include "splpv1.h"
/*
    Петров Андрей Александрович, 14 группа 1 курс
---------------------------------------------------------------------------------------------------------------------------
# |      STATE      |         DESCRIPTION       |           ALLOWED MESSAGES            | NEW STATE | EXAMPLE
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
1 | INIT            | initial state             | A->B     CONNECT                      |     2     |
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
2 | CONNECTING      | client is waiting for con-| A<-B     CONNECT_OK                   |     3     |
  |                 | nection approval from srv |                                       |           |
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
3 | CONNECTED       | Connection is established | A->B     GET_VER                      |     4     |
  |                 |                           |        -------------------------------+-----------+----------------------
  |                 |                           |          One of the following:        |     5     |
  |                 |                           |          - GET_DATA                   |           |
  |                 |                           |          - GET_FILE                   |           |
  |                 |                           |          - GET_COMMAND                |           |
  |                 |                           |        -------------------------------+-----------+----------------------
  |                 |                           |          GET_B64                      |     6     |
  |                 |                           |        ------------------------------------------------------------------
  |                 |                           |          DISCONNECT                   |     7     |
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
4 | WAITING_VER     | Client is waiting for     | A<-B     VERSION ver                  |     3     | VERSION 2
  |                 | server to provide version |          Where ver is an integer (>0) |           |
  |                 | information               |          value. Only a single space   |           |
  |                 |                           |          is allowed in the message    |           |
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
5 | WAITING_DATA    | Client is waiting for a   | A<-B     CMD data CMD                 |     3     | GET_DATA a GET_DATA
  |                 | response from server      |                                       |           |
  |                 |                           |          CMD - command sent by the    |           |
  |                 |                           |           client in previous message  |           |
  |                 |                           |          data - string which contains |           |
  |                 |                           |           the following allowed cha-  |           |
  |                 |                           |           racters: small latin letter,|           |
  |                 |                           |           digits and '.'              |           |
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
6 | WAITING_B64_DATA| Client is waiting for a   | A<-B     B64: data                    |     3     | B64: SGVsbG8=
  |                 | response from server.     |          where data is a base64 string|           |
  |                 |                           |          only 1 space is allowed      |           |
--+-----------------+---------------------------+---------------------------------------+-----------+----------------------
7 | DISCONNECTING   | Client is waiting for     | A<-B     DISCONNECT_OK                |     1     |
  |                 | server to close the       |                                       |           |
  |                 | connection                |                                       |           |
---------------------------------------------------------------------------------------------------------------------------

IN CASE OF INVALID MESSAGE THE STATE SHOULD BE RESET TO 1 (INIT)
*/
/* FUNCTION:  validate_message
 *
 * PURPOSE:
 *    This function is called for each SPLPv1 message between client
 *    and server
 *
 * PARAMETERS:
 *    msg - pointer to a structure which stores information about
 *    message
 *
 * RETURN VALUE:
 *    MESSAGE_VALID if the message is correct
 *    MESSAGE_INVALID if the message is incorrect or out of protocol
 *    state
 */
/*const char* state[7][20] = {"INIT", "CONNECTING", "CONNECTED", "WAITING_VER", "WAITING_DATA", "WAITING_B64_DATA", "DISCONNECTING"};*/


struct Commands {
    char* command;
    unsigned int len;
};
const struct Commands commands[] = {"GET_DATA", 8, "GET_FILE",8, "GET_COMMAND", 11};


unsigned int indexCommand;
unsigned int stateNow = 1;

const char base64[] = { 0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,
                        0, 0, 0, 1, 0,      0, 0, 1, 1, 1,      1, 1, 1, 1, 1,      1, 1, 1, 0, 0,
                        0, 0, 0, 0, 0,      1, 1, 1, 1, 1,      1, 1, 1, 1, 1,      1, 1, 1, 1, 1,
                        1, 1, 1, 1, 1,      1, 1, 1, 1, 1,      1, 0, 0, 0, 0,      0, 0, 1, 1, 1,
                        1, 1, 1, 1, 1,      1, 1, 1, 1, 1,      1, 1, 1, 1, 1,      1, 1, 1, 1, 1,
                        1, 1, 1, 0, 0,      0, 0, 0 };

const char data[] = { 0, 0, 0, 0, 0,        0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0,        0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0,        0, 1, 0, 1, 1,      1, 1, 1, 1, 1,      1, 1, 1, 0, 0,
                      0, 0, 0, 0, 0,        0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0,        0, 0, 0, 0, 0,      0, 0, 0, 0, 0,      0, 0, 1, 1, 1,
                      1, 1, 1, 1, 1,        1, 1, 1, 1, 1,      1, 1, 1, 1, 1,      1, 1, 1, 1, 1,
                      1, 1, 1, 0, 0,        0, 0, 0};

const char msg[] = { 0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 1, 1,      1, 1, 1, 1, 1,     1, 1, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0,     0, 0, 0, 0, 0,      0, 0, 0, 0, 0,     0 };

enum test_status validate_message(struct Message *messages) {
    char* message = messages->text_message;
    //ОТ ПОЛЬЗОВАТЕЛЯ К СЕРВЕРУ
    if (messages->direction == A_TO_B) {
        if (stateNow == 1 && !strcmp(message, "CONNECT")) {
            stateNow = 2;
            return MESSAGE_VALID;
        }
        if (stateNow == 3) {
            if (!strcmp(message, "GET_VER")) {
                stateNow = 4;
                return MESSAGE_VALID;
            }
            if (!strcmp(message, commands[0].command)) {
                indexCommand = 0;
                stateNow = 5;
                return MESSAGE_VALID;
            }
            if (!strcmp(message, commands[1].command)) {
                indexCommand = 1;
                stateNow = 5;
                return MESSAGE_VALID;
            }
            if (!strcmp(message, commands[2].command)) {
                indexCommand = 2;
                stateNow = 5;
                return MESSAGE_VALID;
            }
            if (!strcmp(message, "GET_B64")) {
                stateNow = 6;
                return MESSAGE_VALID;
            }
            if (!strcmp(message, "DISCONNECT")) {
                stateNow = 7;
                return MESSAGE_VALID;
            }
        }

        //ОТ СЕРВЕРА К ПОЛЬЗОВАТЕЛЮ
    }else {
        if (stateNow == 2 &&  !strcmp(message, "CONNECT_OK")) {
            stateNow = 3;
            return MESSAGE_VALID;
        }
        if (stateNow == 7 && !strcmp(message, "DISCONNECT_OK")) {
            stateNow = 1;
            return MESSAGE_VALID;
        }
        if (stateNow == 4 && !strncmp(message, "VERSION ", 8)) {
            message += 8;
            for (++message; *message != '\0'; message++) {
                if (msg[*message] != '1') {
                    stateNow = 1;
                    return MESSAGE_INVALID;
                }
            }
            stateNow = 3;
            return MESSAGE_VALID;
        }
        if (stateNow == 5) {
            if (!strncmp(message, commands[indexCommand].command, commands[indexCommand].len)){
                message += commands[indexCommand].len;
                if (*message == ' ') {
                    char *s;

                    message++;
                    while(data[*message]){
                        ++message;
                    }

                    s = (*message == ' ') ? message + 1 : NULL;
                    if (s && !strcmp(s, commands[indexCommand].command)) {
                        stateNow = 3;
                        return MESSAGE_VALID;
                    }
                }
            }
        }
        if (stateNow == 6 && !strncmp(message, "B64: ", 5)) {
            message += 5;
            char* begin = message;

            while(base64[*message]){
                ++message;
            }

            //проверка на =
            char check = 0; //к-во =
            while(check < 2 && message[check] == '='){
                ++check;
            }

            if ((message - begin + check) % 4 == 0 && !message[check]) {
                stateNow = 3;
                return MESSAGE_VALID;
            }
        }
    }

    stateNow = 1;
    return MESSAGE_INVALID;
}

