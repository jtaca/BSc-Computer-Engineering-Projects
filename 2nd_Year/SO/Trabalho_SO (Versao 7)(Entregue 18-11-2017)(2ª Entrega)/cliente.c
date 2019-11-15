#include <signal.h>
#include <unistd.h>
#include <string.h>
#include "timemsg.h"

int n;

void trata_sinal(int sinal) {
	int sizeOfMessage = 100;
	char message[sizeOfMessage];
	switch(sinal) {
		case SIGUSR1 :
		current_time_message(message, sizeOfMessage, "O tempo de reserva terminou.");
		break;
	
		case SIGUSR2 :
		current_time_message(message, sizeOfMessage, "Saldo insuficiente. Regresse assim que possivel.");
		break;
		
		default:
		break;
	}
	printf("%s\n", message);
}

int main() {
	n = 1;
	printf("Bem vindo ao Projeto eCoolIUL, em que podemos ajudar?\n");
	printf("O meu PID é %d\n", getpid());
	signal(SIGUSR1, trata_sinal);
	signal(SIGUSR2, trata_sinal);
	while(n) {
		pause();
	}
	return 0;
} 
