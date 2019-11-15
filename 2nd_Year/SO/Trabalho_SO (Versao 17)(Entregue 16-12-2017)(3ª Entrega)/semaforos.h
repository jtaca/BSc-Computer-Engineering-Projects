#include <sys/ipc.h>
#include <ctype.h>
#include <stdlib.h>
#include <stdio.h>
// Estruturas das operacoes do semaforo

//struct sembuf UP = { 0, 1, 0 } ; // fazer array de semaforo
//struct sembuf DOWN = { 0, -1, 0 };


//int id = semget ( 1007, 2, IPC_CREAT | 0666 );

//semctl ( id, 0, SETVAL, 1);
//semctl ( id, 1, SETVAL, 1);

#define KEYSEM 0x78178

#define exit_on_error(s,m) if (s<0) {perror(m); exit(1);}
#define exit_on_null(s,m) if(s==NULL){perror(m); exit(1);}

int idSem;

void sem_Up(int semPos);

void sem_DOWN(int semPos);

//void sem_Up( int semPos);

//void sem_Down(int semPos);

/*void sem_Up( int semPos){
    int status = semctl ( idSem, semPos, SETVAL, 1);
    exit_on_error (status, "UP");
    printf("Sem UP!\n");
}

void sem_Down(int semPos){
    int status = semctl ( idSem, semPos, SETVAL, 0);
    exit_on_error (status, "Down");
    printf("Sem UP!\n");
}

*/
/*
 void sem_UP(int id){ //static
 int sem_id = semget ( key, 1, 0 );
 exit_on_error (sem_id, "Criação/Ligação");
 printf("Sem UP!\n");
 int status = semop ( sem_id, &UP, 1 );
 exit_on_error (status, "UP");
 }
 
 
 
 void sem_DOWN(){
 int sem_id = semget ( key, 1, 0 );
 exit_on_error (sem_id, "Criação/Ligação");
 printf("Sem DOWN!\n");
 int status = semop ( sem_id, &DOWN, 1 );
 exit_on_error (status, "DOWN");
 }
 */


