#include "semaforos.h"
#include <sys/sem.h>


int idSem;

struct sembuf DOWN0 = {0, -1, 0};
struct sembuf UP0 = {0, 1, 0};

struct sembuf DOWN1 = {1, -1, 0};
struct sembuf UP1 = {1, 1, 0};


struct sembuf DOWN2 = {2, -1, 0};
struct sembuf UP2 = {2, 1, 0};

void sem_Up(int semPos){
	int status = 1;
    if(semPos == 0) {
        status = semop (idSem, &UP0, 1);
        //exit_on_error (status, "UP");
    }else if(semPos == 1) {
        status = semop (idSem, &UP1, 1);
        //exit_on_error (status, "UP");
    } else if(semPos == 2) {
		status = semop(idSem, &UP2, 1);
		//printf("Sem UP!\n");
	}
	exit_on_error(status, "UP");
    //printf("Sem UP!\n");
}

void sem_DOWN(int semPos){
    int status = 1;
	if(semPos == 0) {
        status = semop (idSem, &DOWN0, 1);
        //exit_on_error (status, "DOWN");
    }else if(semPos == 1) {
        status = semop (idSem, &DOWN1, 1);
        //exit_on_error (status, "DOWN");
    } else if(semPos == 2) {
		status = semop(idSem, &DOWN2, 1);
		//printf("Sem DOWN!\n");
	}
    exit_on_error (status, "DOWN");
    //printf("Sem DOWN!\n");
}
