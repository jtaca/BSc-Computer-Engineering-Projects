#include <signal.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/msg.h>
#include "timemsg.h"
#include "mensagens.h"

#define exit_on_error(s, m) if (s < 0) { perror(m); exit(1); }
#define login_if_not(m) if(m.dados.status == LOGGED_OUT) { printf("%s\n", m.dados.texto); login(); break; }

#define DEFAULT_STRING_SIZE 20

int n;
int id;
int idMsg;

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
	printf("\n%s", message);
	printf ("\nEscolha uma opcao: ");
	fflush(stdout);
}

void connectToMessageList() {
    idMsg = msgget(KEYMESSAGE, 0666);
    exit_on_error(idMsg, "A lista de mensagens nao esta criada");
}

void sendMessage(char operacao[], char info1[], char info2[]) {
	MsgClientServer mensagem;
	mensagem.tipo = 1;
	mensagem.dados.myid = id;

	strcpy(mensagem.dados.operacao, operacao);
	strcpy(mensagem.dados.info1, info1);
	strcpy(mensagem.dados.info2, info2);
	int error = msgsnd(idMsg, &mensagem, sizeof(mensagem.dados), 0);
	exit_on_error(error, "O Servidor reiniciou");
}

void recieveMessage(MsgServerClient *msg) {
    int error = msgrcv(idMsg, msg, sizeof(msg -> dados), id, 0);
    exit_on_error(error, "O Servidor reiniciou");
}

void login() {
	MsgServerClient mensagemRecebida;
	char nickname[DEFAULT_STRING_SIZE], password[DEFAULT_STRING_SIZE];
	id = getpid();
	do {
		printf("Nickname: ");
		fgets(nickname, DEFAULT_STRING_SIZE, stdin);
		nickname[ strlen(nickname) - 1 ] = '\0';
		printf("Password: ");
		fgets(password, DEFAULT_STRING_SIZE, stdin);
		password[ strlen(password) - 1 ] = '\0';

		sendMessage("Login", nickname, password);

		recieveMessage(&mensagemRecebida);
		//int error = msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
		//exit_on_error(error, "O Servidor reiniciou");
		id = mensagemRecebida.dados.valor1;
		printf("%s\n", mensagemRecebida.dados.texto);
	} while(id < 0);
	printf("%d", id);
}

//void recieveMessage(MsgServerClient *msg) {
//	int error = msgrcv(idMsg, &msg, sizeof(mensagemRecebida.dados), id, 0);
//	exit_on_error(error, "O Servidor reiniciou");
//}

int main() {
	connectToMessageList();
	MsgServerClient mensagemRecebida;

	login();
	n = 1;
	printf("Bem vindo ao Projeto eCoolIUL, em que podemos ajudar?\n");
	printf("O meu PID é %d\n", getpid());
	signal(SIGUSR1, trata_sinal);
	signal(SIGUSR2, trata_sinal);

	int opcao = -1;
	while(opcao != 0) {
		char aux[DEFAULT_STRING_SIZE];
		int valor = 0;
		printf ("\nMenu\n");
		printf ("1 - Listar viaturas disponiveis \n");
		printf ("2 - Iniciar reserva \n");
		printf ("3 - Iniciar aluguer \n");
		printf ("4 - Terminar pedido \n");
		printf ("5 - Adicionar saldo \n");
		printf ("6 - Ver saldo \n");
		printf ("0 - Sair \n");
		printf ("\nEscolha uma opcao: ");
		fgets (aux, DEFAULT_STRING_SIZE, stdin);

		opcao = atoi(aux);
		switch (opcao) {
			case 1:
				sendMessage("Viaturas", "", "");

				recieveMessage(&mensagemRecebida);
				//int error = msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				//exit_on_error(error, "O Servidor reiniciou");
				login_if_not(mensagemRecebida);
				printf("Viaturas:\n");
				while(mensagemRecebida.dados.status == LOOP) {
					printf("%s\n", mensagemRecebida.dados.texto);
					msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				}
				printf("%s\n", mensagemRecebida.dados.texto);
				break;
			case 2:
				do {
					printf("ID da Viatura para reserva: ");
					fgets(aux, DEFAULT_STRING_SIZE, stdin);
					aux[strlen(aux) - 1] = '\0'; 
				} while(strlen(aux) == 0);
				sendMessage("Reservar", aux, "");

				recieveMessage(&mensagemRecebida);
				//msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				login_if_not(mensagemRecebida);
				printf("%s\n", mensagemRecebida.dados.texto);
				break;
			case 3:
				do {
					printf("ID da Viatura para aluguer: ");
					fgets(aux, DEFAULT_STRING_SIZE, stdin);
					aux[strlen(aux) - 1] = '\0';
				} while(strlen(aux) == 0);
				sendMessage("Alugar", aux, "");
	
				recieveMessage(&mensagemRecebida);
				//msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				login_if_not(mensagemRecebida);
				printf("%s\n", mensagemRecebida.dados.texto);
				break;
			case 4:
				sendMessage("Finalizar", "", "");
				
				recieveMessage(&mensagemRecebida);
				//msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				login_if_not(mensagemRecebida);
				printf("%s\n", mensagemRecebida.dados.texto);
				if(mensagemRecebida.dados.status == 2) {
					printf("Gastou: %d minuto(s) ficando com %d minuto(s) de saldo\n", mensagemRecebida.dados.valor1, mensagemRecebida.dados.valor2);
				}
				break;
			case 5:
				do {
					printf("Valor a adicionar ao saldo: ");
					fgets(aux, DEFAULT_STRING_SIZE, stdin);
					valor = atoi(aux);
				} while(valor < 0);
				snprintf(aux, DEFAULT_STRING_SIZE, "%d", valor);
				sendMessage("Carregar", aux, "");
				
				recieveMessage(&mensagemRecebida);
				//msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				login_if_not(mensagemRecebida);
				printf("%s\n", mensagemRecebida.dados.texto);
				printf("Saldo inicial: %d minuto(s). Saldo actual: %d minuto(s).\n", mensagemRecebida.dados.valor1, mensagemRecebida.dados.valor2);
				break;
			case 6:
				sendMessage("Saldo", "", "");
			
				recieveMessage(&mensagemRecebida);
				//msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), id, 0);
				login_if_not(mensagemRecebida);
				printf("O seu saldo e: %d minuto(s)\n", mensagemRecebida.dados.valor1);
				break;
			case 0:
				sendMessage("Logout", "", "");
				
				exit(0);
				break;
			default:
				printf("Opcao rejeitada\n");
		}

	} 	

	return 0;
} 
