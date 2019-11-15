#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <unistd.h>
#include <ctype.h>

#define exit_on_error(s,m) if (s<0) {perror(m); exit(1);}
#define exit_on_null(s,m) if(s==NULL){perror(m); exit(1);}

typedef struct{
	char nick[50];
	char pass[50];
	int id;
	char nome[50];
	char email[40];
	char turma[10];
	int saldo;
	struct {
		int online;
		int pid;
	} status;
} Tutilizador;

typedef struct {
	char ID[20];
	char cor[20];
	char marca[50];
	char modelo[30];
	char tipo[20];
	int mudancas;
	char matricula[15];
	struct {
		int tipo;
		int id_user;
		time_t tempo;
	} reserva;
    
    // atributo está resevada?
    // tempo que está reserava
    // ciclo?? inteiro s e maior que 0 está alugada e decrementa
    // struct com int cicloReserva; o id do user , o int tip com 0 1 2 livre, reservado, alugado
    
    // Atenção que o saldo pode ficar negativo!!!!
    
} Tviatura;

//int key = 30209; //define
//int key1 = 30210;

// Estruturas das operacoes do semaforo
//struct sembuf UP = { 0, 1, 0 } ; // fazer array de semaforo
//struct sembuf DOWN = { 0, -1, 0 };
/*
void sem_UP(){ //static
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
