#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/msg.h>
#include "timemsg.h"
#include "estruturas.h"
#include "memoriaPartilhada.h"
#include "semaforos.h"
#include "mensagens.h"

#define MaxUtilizadores 500
#define MaxViaturas 300
#define SIZEOFMESSAGE 100
#define SERVIDORPID "servidor.pid"
#define MESSAGE_TEXT_SIZE 50
#define MESSAGE_RECIEVE_DEFAULT_SIZE 20
#define TEMPO_LIMITE_DE_RESERVA 5*60

// Variavel referente a memoria partilhada
int id;

// Variaveis referentes a fila de mensagens
int idMsg;
//FILE *flog;

int filho;

Tutilizador *u;
Tviatura *v;

void acedeMemoriaPartilhada() {

	int idUtilizador = shmget ( KEYUTILIZADOR, MaxUtilizadores*sizeof(Tutilizador),  0666 );
	exit_on_error(idUtilizador, "Ligação memoria partilhada utilizador");

	int idViatura = shmget ( KEYVIATURA, MaxViaturas*sizeof(Tviatura),  0666 );
	exit_on_error(idViatura, "Ligação memoria partilhada viatura");

	u =(Tutilizador *)shmat(idUtilizador,0,0);
	v =(Tviatura *)shmat(idViatura,0,0);

	exit_on_null(u,"Erro no attach dos Utilizadors");
	exit_on_null(v,"Erro no attach das Viaturas");

}

void sendMessage(char texto[], int valor1, int valor2, int status, long tipo) {
	MsgServerClient mensagem;
	mensagem.tipo = tipo;
	mensagem.dados.valor1 = valor1;
	mensagem.dados.valor2 = valor2;
	mensagem.dados.status = status;

	strcpy(mensagem.dados.texto, texto);

	msgsnd(idMsg, &mensagem, sizeof(mensagem.dados), 0);
}


void write_log(char message[]) {
	char messageWrite[SIZEOFMESSAGE];
	current_time_message(messageWrite, SIZEOFMESSAGE, message);
	printf("%s\n", messageWrite);
	sem_DOWN(2);
	FILE *flog = fopen("servidor.log", "a");
	if(flog == NULL) {
		perror("Erro: Não consegui abrir o servidor.log");
		sem_Up(2);
		exit(1);
	}
	fprintf(flog, "%s\n", messageWrite);
	fclose(flog);
	sem_Up(2);
}

void ligar_sem(){
	idSem = semget(KEYSEM, 3, 0666);
	exit_on_error(idSem, "Error: Ligação Sem");
}

void terminar_fiscal(int signal) {
	printf("\n");
	write_log("O Fiscal terminou sua actividade.");
	exit(0);
}

void terminar_servidor(int signal) {
	kill(filho, SIGINT);
	wait(NULL);
	int removed = remove(SERVIDORPID);
	char remMessage[SIZEOFMESSAGE] = "O ";
	strcat(remMessage, SERVIDORPID);
	if(removed == 0) {
		strcat(remMessage, " foi removido");
	} else {
		strcat(remMessage, " nao foi removido");
	}
	write_log(remMessage);
	write_log("O servidor terminou sua atividade.");
	exit(0);
}

void verificar_dados(int signal) {
	alarm(60);
	time_t t = time(NULL);
	char message[SIZEOFMESSAGE];
	write_log("Fiscal vai verificar os dados.");
	int i, j, encontrei, valor, valoraux;
	sem_DOWN(1); // aqui
	for(i = 0 ; i < MaxViaturas && strcmp(v[i].ID, "-1") != 0 ; i++) {
		if(v[i].reserva.tipo == 1 && ((t - v[i].reserva.tempo) >= TEMPO_LIMITE_DE_RESERVA)) {
			printf("%ld e %d\n", t - v[i].reserva.tempo, TEMPO_LIMITE_DE_RESERVA);
			v[i].reserva.tipo = 0;
			sprintf(message, "libertar_viatura, viatura=%s, reservado_por=%d", v[i].ID, v[i].reserva.id_user);
			write_log(message);

			sem_DOWN(0);
			for(j = 0, encontrei = 0 ; j < MaxUtilizadores && u[j].id != -1 && encontrei == 0 ; j++) {
				if(u[j].id == v[i].reserva.id_user) {
					encontrei = 1;
					if(u[j].status.online == 1 && u[j].status.pid != 0) {
						kill(u[j].status.pid, SIGUSR1);
					}
				}
			}
			sem_Up(0);
		}
		if(v[i].reserva.tipo == 2) {
			sem_DOWN(0);
			for(j = 0, encontrei = 0 ; j < MaxUtilizadores && u[j].id != -1 && encontrei == 0 ; j++) {
				if(u[j].id == v[i].reserva.id_user) {
					encontrei = 1;
					valoraux = ((t - v[i].reserva.tempo)/60);
					valor = u[j].saldo - valoraux;
					if(valor <= 0) {
						v[i].reserva.tipo = 3;
						sprintf(message, "valor_superior_saldo, id=%d, viatura=%s, duracao=%d", u[j].id, v[i].ID, valoraux);
						write_log(message);
						if(u[j].status.online == 1 && u[j].status.pid != 0) {
							kill(u[j].status.pid, SIGUSR2);
						}
					}
				}
			}
			sem_Up(0);
		}
	}
	sem_Up(1); // aqui
}

int getUserIndexWithID(int myid, int wantToSendMessage) {
	int i, index = -1;
	for(i = 0 ; i < MaxUtilizadores && index == -1 ; i++) {
		if(u[i].id == myid && u[i].status.online == 1) {
			index = i;
		}
	}
	if(wantToSendMessage != 0 && index == -1) {
		char message[SIZEOFMESSAGE];
		sprintf(message, "operation_error, id=%d, unknown_user_id", myid);
		write_log(message);

		sendMessage("Por favor faça login antes de efectuar esta ação", -1, -1, LOGGED_OUT, myid);
	}
	return index;
}

void connectToMessageList() {
	idMsg = msgget(KEYMESSAGE, IPC_CREAT | IPC_EXCL | 0666);
	if(idMsg < 0) {
		idMsg = msgget(KEYMESSAGE, 0666);
		exit_on_error(idMsg, "Erro a ligar a lista de mensagens");
		int n = msgctl(idMsg, IPC_RMID, NULL);
		exit_on_error(n, "Erro a criar a reiniciar a lista de mensagens");
		idMsg = msgget(KEYMESSAGE, IPC_CREAT | IPC_EXCL | 0666);
		exit_on_error(idMsg, "Erro a criar a lista de mensagens");
	}
}

void listenToMessages() {
	MsgClientServer mensagemRecebida;
	int encontrei, i, j, myid, valor, valoraux;
	char aux[MESSAGE_TEXT_SIZE];
	char operacao[MESSAGE_RECIEVE_DEFAULT_SIZE];
	char message[SIZEOFMESSAGE];
	while(1) {
		msgrcv(idMsg, &mensagemRecebida, sizeof(mensagemRecebida.dados), 1, 0);
		strcpy(operacao, mensagemRecebida.dados.operacao);
		myid = mensagemRecebida.dados.myid;
		if(strcmp(operacao, "Login") == 0) {
			encontrei = 0;

			sprintf(message, "login, name=%s, pid=%d", mensagemRecebida.dados.info1, myid);
			write_log(message);

			sem_DOWN(0);
			for (i = 0 ; i < MaxUtilizadores && u[i].id != -1 && encontrei == 0 ; i++) {
				if(strcmp(u[i].nick, mensagemRecebida.dados.info1) == 0) {
					encontrei = 1;
					if(strcmp(u[i].pass, mensagemRecebida.dados.info2) == 0) {
						u[i].status.online = 1;
						u[i].status.pid = myid;

						sprintf(message, "login_ok, name=%s, id=%d, pid=%d", mensagemRecebida.dados.info1, u[i].id, myid);
						write_log(message);

						sendMessage("Login efectuado com sucesso!", u[i].id, -1, 1, myid);
					} else {
						sprintf(message, "login_error, name=%s, pid=%d, invalid_password", mensagemRecebida.dados.info1, myid);
						write_log(message);

						sendMessage("Password Incorrecta", -1, -1, -1, myid);
					}
				}
			}
			sem_Up(0);
			if(encontrei == 0) {
				sprintf(message, "login_error, name=%s, pid=%d, unknown_client", mensagemRecebida.dados.info1, myid);
				write_log(message);

				sendMessage("Unknown User", -1, -1, -1, myid);
			}

		} else if(strcmp(operacao, "Viaturas") == 0){
			sem_DOWN(0);	
			i = getUserIndexWithID(myid, 1);
			sem_Up(0);
			if(i != -1) {
				sprintf(message, "list_livres, from=%d", myid);
				write_log(message);

				sem_DOWN(1);
				for(i = 0 ; i < MaxViaturas && strcmp(v[i].ID, "-1") != 0 ; i++) {
					if(v[i].reserva.tipo == 0) {
						sprintf(message, "livres_list, to=%d, info=%s", myid, v[i].ID);
						write_log(message);

						snprintf(aux, MESSAGE_TEXT_SIZE, "Viatura: %s", v[i].ID);
						sendMessage(aux, -1, -1, LOOP, myid);
					}
				}
				sem_Up(1);

				sendMessage("----------------------", -1, -1, 1, myid); 
			}

		} else if(strcmp(operacao, "Reservar") == 0){

			sem_DOWN(0);
			i = getUserIndexWithID(myid, 1);
			if(i != -1) {
				if(u[i].saldo > 0) {
					sem_Up(0);
					j = 0;
					sem_DOWN(1);
					for(j = 0, encontrei = 0 ; j < MaxViaturas && strcmp(v[j].ID, "-1") != 0 && encontrei == 0 ; j++) {
						if(strcmp(v[j].ID, mensagemRecebida.dados.info1) == 0) {
							encontrei = 1;
							if(v[j].reserva.tipo == 0) {
								v[j].reserva.tipo = 1;
								v[j].reserva.id_user = myid;
								time_t t = time(NULL);
								v[j].reserva.tempo = t;
								sprintf(message, "start_reserva, id=%d, viatura=%s", myid, mensagemRecebida.dados.info1);
								write_log(message);

								sendMessage("Reserva efetuada com sucesso!", -1, -1 , 1, myid);
							} else {
								sprintf(message, "reserva_error, id=%d, viatura=%s, vehicle_not_available", myid, mensagemRecebida.dados.info1);
								write_log(message);

								sendMessage("A viatura não se encontra disponível", -1, -1, -1, myid);
							}
						}
					}
					sem_Up(1);
					if(encontrei == 0) {
						sprintf(message, "reserva_error, id=%d, viatura=%s, unknown_vehicle", myid, mensagemRecebida.dados.info1);
						write_log(message);

						sendMessage("A viatura que tenta reservar, não existe", -1, -1, -1, myid);
					}
				} else {
					sem_Up(0);
					sprintf(message, "reserva_error, id=%d, viatura=%s, not_enough_balance", myid, mensagemRecebida.dados.info1);
					write_log(message);

					sendMessage("Saldo Insuficiente", -1, -1, -1, myid);
				}
			}


		} else if(strcmp(operacao, "Alugar") == 0){
			sem_DOWN(0);
			i = getUserIndexWithID(myid, 1);
			if(i != -1) {
				if(u[i].saldo > 0) {
					sem_Up(0);
					j = 0;
					sem_DOWN(1);
					for(j = 0, encontrei = 0 ; j < MaxViaturas && strcmp(v[j].ID, "-1") != 0 && encontrei == 0 ; j++) {
						if(strcmp(v[j].ID, mensagemRecebida.dados.info1) == 0) {
							encontrei = 1;
							if(v[j].reserva.tipo == 0 || (v[j].reserva.tipo == 1 && v[j].reserva.id_user == myid)) {
								v[j].reserva.tipo = 2;
								v[j].reserva.id_user = myid;
								time_t t = time(NULL);
								v[j].reserva.tempo = t;
								sprintf(message, "start_aluguer, id=%d, viatura=%s", myid, mensagemRecebida.dados.info1);
								write_log(message);

								sendMessage("As rodas da viatura foram destrancadas. Boa Viagem!", -1, -1, 1, myid);
							} else {
								sprintf(message, "aluguer_error, id=%d, viatura=%s, vehicle_not_available", myid, mensagemRecebida.dados.info1);
								write_log(message);

								sendMessage("A viatura não se encontra disponível", -1, -1, -1, myid);
							}
						}
					}
					sem_Up(1);
					if(encontrei == 0) {
						sprintf(message, "aluguer_error, id=%d, viatura=%s, unknown_vehicle", myid, mensagemRecebida.dados.info1);
						write_log(message);

						sendMessage("A viatura que tenta alugar, não existe", -1, -1, -1, myid);
					}
				} else {
					sem_Up(0);
					sprintf(message, "aluguer_error, id=%d, viatura=%s, not_enough_balance", myid, mensagemRecebida.dados.info1);
					write_log(message);

					sendMessage("Saldo Insuficiente", -1, -1, -1, myid);
				}
			}

		} else if(strcmp(operacao, "Finalizar") == 0){
			sem_DOWN(1);
			sem_DOWN(0);
			j = getUserIndexWithID(myid, 1);
			if(j != -1) {
				for(i = 0, encontrei = 0 ; i < MaxViaturas && strcmp(v[i].ID, "-1") != 0 && encontrei == 0 ; i++) {
					if(v[i].reserva.tipo != 0 && v[i].reserva.id_user == myid) {
						encontrei = 1;
						if(v[i].reserva.tipo == 2 || v[i].reserva.tipo == 3) {
							v[i].reserva.tipo = 0;
							v[i].reserva.id_user = 0;
							time_t t = time(NULL);
							valor = ((t - v[i].reserva.tempo)/60);
							valoraux = u[j].saldo;
							u[j].saldo -= valor;
							snprintf(aux, MESSAGE_TEXT_SIZE, "As rodas da viatura %s foram trancadas.", v[i].ID);
							sprintf(message, "end_aluguer, id=%d, viatura=%s, duracao=%d", myid, mensagemRecebida.dados.info1, valor);
							write_log(message);

							sprintf(message, "update_saldo, id=%d, initial=%d, used=%d, final=%d", myid, valoraux, valor, u[j].saldo);
							write_log(message);

							sendMessage(aux, valor, u[j].saldo, 2, myid); // mandar o valor gasto e o saldo final?
						} else {
							v[i].reserva.tipo = 0;
							v[i].reserva.id_user = 0;
							snprintf(aux, MESSAGE_TEXT_SIZE, "A reserva para a viatura %s foi cancelada.", v[i].ID);
							sprintf(message, "end_reserva, id=%d, viatura=%s", myid, mensagemRecebida.dados.info1);
							write_log(message);

							sendMessage(aux, -1, -1, 1, myid);
						}
					}
				}
			}
			sem_Up(0);
			sem_Up(1);
			if(encontrei == 0) {
				sprintf(message, "error_end, id=%d, viatura=%s, unknown_vehicle", myid, mensagemRecebida.dados.info1);
				write_log(message);

				sendMessage("Não existem viaturas em seu nome", -1, -1, -1, myid);
			}

		} else if(strcmp(operacao, "Saldo") == 0){

			sem_DOWN(0);
			i = getUserIndexWithID(myid, 1);

			if(i != -1) {
				valor = u[i].saldo;

				if(valor > 0) {
					strcpy(aux, "saldo_ok");
				} else {
					strcpy(aux, "saldo_not_ok");
				}
				sprintf(message, "check_saldo, id=%d, saldo=%d, %s", myid, valor, aux);
				write_log(message);

				sendMessage("O Valor do saldo é: ", valor, -1, 1, myid);
			}
			sem_Up(0);

		} else if(strcmp(operacao, "Carregar") == 0){
			valor = atoi(mensagemRecebida.dados.info1);
			sem_DOWN(0);
			i = getUserIndexWithID(myid, 1);
			if(i != -1) {
				if(valor > 0) {
					valoraux = u[i].saldo;

					u[i].saldo += valor;

					sprintf(message, "add_saldo, id=%d, initial=%d, added=%d, final=%d", myid, valoraux, valor, u[i].saldo);
					write_log(message);

					sendMessage("Saldo adicionado com sucesso", valoraux, u[i].saldo, 1, myid);
				} else {
					sprintf(message, "error_add_saldo, id=%d, added=%d, invalid_value", myid, valor);
					write_log(message);

					sendMessage("Não se pode adicionar saldo negativo ou zero", u[i].saldo, u[i].saldo, -1, myid);
				}
			}
			sem_Up(0);

		} else if(strcmp(operacao, "Logout") == 0){

			sem_DOWN(0);
			i = getUserIndexWithID(myid, 0);
			if(i != -1) {
				u[i].status.online = 0;
				u[i].status.pid = 0;
				sem_Up(0);

				sprintf(message, "logout, id=%d", myid);
				write_log(message);
			}

		} else {

		}

	}
}


int main() {
	ligar_sem();
	acedeMemoriaPartilhada();
	connectToMessageList();
	char message[SIZEOFMESSAGE];
	int pid = getpid();
	sprintf(message, "O servidor comecou a correr. PID = %d", pid);
	write_log(message);

	FILE *f = fopen(SERVIDORPID, "w");
	exit_on_null(f,"Erro: Nao consegui abrir o servidor.pid");
	fprintf(f, "%d\n", pid);
	write_log("O PID do processo foi escrito no servidor.pid");
	fclose(f);
	filho = fork();
	if(filho == 0) {
		write_log("O Fiscal foi criado com sucesso.");
		signal(SIGINT, terminar_fiscal);
		signal(SIGALRM, verificar_dados);
		alarm(60);
		while(1) {
			pause();
		}
		exit(0);
	}
	signal(SIGINT, terminar_servidor);

	listenToMessages();

	return 0;
}


