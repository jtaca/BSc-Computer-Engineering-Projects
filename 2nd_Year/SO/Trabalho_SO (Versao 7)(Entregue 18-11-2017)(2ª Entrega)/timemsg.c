#include <stdio.h>
#include <time.h>

void current_time_message(char result[], int sizeOfResult, char message[]) {
    time_t t = time(NULL);
    struct tm time = *localtime(&t);
    snprintf(result, sizeOfResult, "%d-%02d-%02d %02d:%02d:%02d %s", time.tm_year + 1900, time.tm_mon + 1, time.tm_mday, time.tm_hour, time.tm_min, time.tm_sec, message);

}

