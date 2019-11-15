#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <sys/wait.h>
#include "timemsg.h"

#define SIZEOFMESSAGE 100

//FILE *flog;

int filho;
char servidorPID[] = "servidor.pid";

void write_log(char message[]) {
	FILE *flog = fopen("servidor.log", "a");
	if(flog == NULL) {
		perror("Erro: Não consegui abrir o servidor.log");
		exit(1);
	}
	fprintf(flog, "%s\n", message);
	fclose(flog);
}

void terminar_fiscal(int signal) {
	char messageWrite[] = "O Fiscal terminou sua actividade.";
	char message[SIZEOFMESSAGE];
	current_time_message(message, SIZEOFMESSAGE, messageWrite); 
	printf("\n%s\n", message);
	write_log(message);
	exit(0);
}

void terminar_servidor(int signal) {
	kill(filho, SIGINT);
	wait(NULL);
	int removed = remove(servidorPID);
	char remMessage[SIZEOFMESSAGE] = "O ";
	strcat(remMessage, servidorPID);
	if(removed == 0) {
		strcat(remMessage, " foi removido");
	} else {
		strcat(remMessage, " nao foi removido");
	}
	char removeMessage[SIZEOFMESSAGE];
	current_time_message(removeMessage, SIZEOFMESSAGE, remMessage);
	printf("%s\n", removeMessage);
	write_log(removeMessage);
	char message[SIZEOFMESSAGE];
	current_time_message(message, SIZEOFMESSAGE, "O servidor terminou sua atividade.");
	printf("%s\n", message);
	write_log(message);
	write_log("----------------------------------------------------------");
	exit(0);
}

void verificar_dados(int signal) {
	char message[SIZEOFMESSAGE];
	current_time_message(message, SIZEOFMESSAGE, "Fiscal vai verificar os dados.");
	printf("%s\n", message);
	write_log(message);
	alarm(60);
}

int main() {
	char message[SIZEOFMESSAGE];
	char messageWrite[SIZEOFMESSAGE];
	
	int pid = getpid();
	sprintf(messageWrite, "O servidor comecou a correr. PID = %d", pid); 
	current_time_message(message, SIZEOFMESSAGE, messageWrite);
	write_log(message);	
	printf("%s\n", message);
	FILE *f = fopen(servidorPID, "w");
	if(f == NULL) {
		char messageErr[] = "Erro: Nao consegui abrir o servidor.pid";
		char messageError[SIZEOFMESSAGE];
		current_time_message(messageError, SIZEOFMESSAGE, messageErr);
		write_log(messageError);
		perror(messageErr);
		exit(1);
	}
	//FILE *flog;
	fprintf(f, "%d\n", pid);
	current_time_message(message, SIZEOFMESSAGE, "O PID do processo foi escrito no servidor.pid");
	write_log(message);
	fclose(f);
	filho = fork();
	if(filho == 0) {
		current_time_message(message, SIZEOFMESSAGE, "O Fiscal foi criado com sucesso.");
		write_log(message);
		signal(SIGINT, terminar_fiscal);
		signal(SIGALRM, verificar_dados);
		alarm(60);
		while(1) {
			pause();
		}
		exit(0);
	}
	signal(SIGINT, terminar_servidor);
	while(1) {
		pause();
	}
	return 0;
}
