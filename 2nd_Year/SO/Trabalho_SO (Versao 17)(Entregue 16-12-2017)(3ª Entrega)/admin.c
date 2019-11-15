#include <time.h>
#include "estruturas.h"
#include "memoriaPartilhada.h"
#include "semaforos.h"

#define MaxUtilizadores 500
#define MaxViaturas 300
#define NICKNAME_SIZE 50
#define PASSWORD_SIZE 50
#define VEICULO_ID_SIZE 20
#define VEICULO_COLOR_SIZE 20
#define VEICULO_MARCA_SIZE 50
#define VEICULO_MODELO_SIZE 30
#define VEICULO_TIPO_SIZE 20
#define VEICULO_MATRICULA_SIZE 15
#define AUX_SIZE 250
#define FICHEIRO_UTILIZADORES "utilizadores"
#define FICHEIRO_VIATURAS "viaturas"

Tutilizador *u;
Tviatura *v;


void obter_substring(char aux[], char resultado[], char separador, int indice) {
	int i, j=0, meu_indice = 0;
	for (i=0; aux[i] != '\0'; i++) {
		if ( aux[i] == separador ) { meu_indice++;}
		else if (meu_indice == indice) { resultado[j++]=aux[i];}
	}
	resultado[j]='\0';
}

void cria_sem(){
	idSem = semget(KEYSEM, 3, IPC_CREAT | IPC_EXCL | 0666);
	if(idSem < 0) {
		idSem = semget(KEYSEM, 3, IPC_CREAT | 0666);
		exit_on_error(idSem, "Criação/Ligação Sem");
	} else {
		int status = semctl(idSem, 0, SETVAL, 1);
		exit_on_error(status, "SETVAL0");

		status = semctl(idSem, 1, SETVAL, 1);
		exit_on_error(status, "SETVAL1");

		status = semctl(idSem, 2, SETVAL, 1);
		exit_on_error(status, "SETVAL2");
	}
}

void criarMemoriaPartilhada() {
	int i = 0;
	idUtilizador = shmget(KEYUTILIZADOR, MaxUtilizadores*sizeof(Tutilizador), IPC_CREAT | IPC_EXCL | 0666);
	if(idUtilizador < 0) {
		idUtilizador = shmget(KEYUTILIZADOR, MaxUtilizadores*sizeof(Tutilizador), 0666 );
		exit_on_error(idUtilizador, "Criação/Ligação memoria partilhada utilizador");
		u =(Tutilizador *)shmat(idUtilizador,0,0);
		exit_on_null(u, "Error: Attach of Utilizador Memory");
	} else {
		u =(Tutilizador *)shmat(idUtilizador,0,0);
		exit_on_null(u, "Error: Attach of Utilizador Memory");
		for (i = 0 ; i < MaxUtilizadores ; i++) { // Inicializar a memoria se esta for criada
			u[i].id = -1;
			u[i].status.online = 0;
			u[i].status.pid = 0;
		}

	}
	idViatura = shmget(KEYVIATURA, MaxViaturas*sizeof(Tviatura), IPC_CREAT | IPC_EXCL | 0666);
	if(idViatura < 0) {
		idViatura = shmget(KEYVIATURA, MaxViaturas*sizeof(Tviatura), 0666);
		exit_on_error(idViatura, "Criação/Ligação memoria partilhada viatura");
		v =(Tviatura *)shmat(idViatura,0,0);
		exit_on_null(v, "Error: Attach of Viatura Memory");
	} else {
		v =(Tviatura *)shmat(idViatura,0,0);
		exit_on_null(v, "Error: Attach of Viatura Memory");
		for (i = 0 ; i < MaxViaturas ; i++) { // Inicializar a memoria se esta for criada
			strcpy (v[i].ID, "-1");
			v[i].reserva.tipo = 0;
			v[i].reserva.id_user = 0;
			v[i].reserva.tempo = 0;
		}

	}
}

void lerFicheiro_TXT (char nomef[]) {
	FILE *f = fopen(nomef, "r");
	char aux[AUX_SIZE],tmp[AUX_SIZE];
	int i=0;
	if (f==NULL) {
		perror("Erro de leitura: ");
		exit(1);
	}
	printf("O nome do ficheiro: %s \n",nomef);
	//tem de ir para aqui // como o outro vai sair, ele esta bem
	if(strcmp(nomef,"utilizadores.txt") == 0){
		sem_DOWN(0); //acho que isto tem de ir para cima
		for (i = 0 ; i < MaxUtilizadores ; i++) {
			u[i].id = -1;
		}
		i=0;
		while (fgets(aux, AUX_SIZE, f) != NULL) {
			if (i == MaxUtilizadores) {
				printf("Memoria cheia\n"); exit(1);
			}
			aux[strlen(aux)-1]='\0';
			if(strcmp(aux," ")!=0){
				Tutilizador tutilizador;
				obter_substring(aux, tutilizador.nick, ';', 0);
				obter_substring(aux, tutilizador.pass, ';', 1);
				obter_substring(aux, tmp, ';', 2);
				tutilizador.id = atof(tmp);
				obter_substring(aux, tutilizador.nome, ';', 3);
				obter_substring(aux, tutilizador.email, ';', 4);
				obter_substring(aux, tutilizador.turma, ';', 5);
				obter_substring(aux, tmp, ';', 6);
				tutilizador.saldo = atof(tmp);
				tutilizador.status.online = 0; // para tirar
				tutilizador.status.pid = 0; // para tirar
				u[i++] = tutilizador;
			}
		}
		sem_Up(0);
	}else if(strcmp(nomef,"viaturas.txt") == 0){
		sem_DOWN(1);
		for (i = 0 ; i < MaxViaturas ; i++) {
			strcpy (v[i].ID, "-1");
		}
		i=0;
		while (fgets(aux, AUX_SIZE, f) != NULL) {
			if (i == MaxViaturas) {
				printf("Memoria cheia\n"); exit(1);
			}
			aux[strlen(aux)-1]='\0';
			if(strcmp(aux," ")!=0){
				Tviatura tviatura;
				obter_substring(aux, tviatura.ID,';', 0);
				obter_substring(aux, tviatura.cor,';', 1);
				obter_substring(aux, tviatura.marca,';', 2);
				obter_substring(aux, tviatura.modelo,';', 3);
				obter_substring(aux, tviatura.tipo, ';', 4);
				obter_substring(aux, tmp, ';', 5);
				tviatura.mudancas = atof(tmp);
				obter_substring(aux, tviatura.matricula, ';', 6);
				tviatura.reserva.tipo = 0;
				tviatura.reserva.id_user = 0;
				tviatura.reserva.tempo = 0;
				printf("v:%s \n",tviatura.ID );
				v[i++] = tviatura;
			}
		}
		sem_Up(1);
	}
	fclose(f);
}

void lerFicheiro_DAT (char nomef[]) {
	int i;
	printf("o nome do ficheiro: %s \n",nomef);
	FILE *f = fopen (nomef, "r");
	if(strcmp(nomef,"utilizadores.dat")==0){
		for (i = 0 ; i < MaxUtilizadores ; i++) {
			u[i].id = -1;
		}
		i = 0; Tutilizador tutilizador;
		sem_DOWN(0);
		while (fread (&tutilizador, sizeof(tutilizador), 1, f) > 0) {
			if (i == MaxUtilizadores) {
				printf ("há demasiados utilizadores no .dat \n");
				exit (2);
			}
			tutilizador.status.online = 0;
			tutilizador.status.pid = 0;
			u[i++] = tutilizador;
		}
		sem_Up(0);
	}else if(strcmp(nomef,"viaturas.dat")==0){
		sem_DOWN(1);
		for (i = 0; i < MaxViaturas ; i++) {
			strcpy (v[i].ID, "-1");
		}
		i = 0; Tviatura tviatura;
		while (fread (&tviatura, sizeof(tviatura), 1, f) > 0) {
			if (i == MaxViaturas) {
				printf ("há demasiadas viaturas no .dat \n");
				exit(2);
			}
			tviatura.reserva.tipo = 0;
			tviatura.reserva.id_user = 0;
			tviatura.reserva.tempo = 0;
			v[i++] = tviatura;
		}
		sem_Up(1);
	}
	fclose(f);
}

void leitura (char nomef[]) {
	char txt[] = ".txt", dat[] = ".dat",aux[AUX_SIZE];
	strcpy(aux,nomef);
	strcat(aux,dat);
	if (access(aux,F_OK)==-1) {
		strcpy(aux,nomef);
		strcat(aux,txt);
		lerFicheiro_TXT(aux);
	}else{
		lerFicheiro_DAT(aux);
	}
}

int existeUtilizador (char nick[]) {
	int i, encontrei = 0;
	sem_DOWN(0);
	for (i = 0 ; i < MaxUtilizadores && encontrei == 0 ; i++) {
		if (strcmp(u[i].nick, nick) == 0)
			encontrei = 1;
	}
	sem_Up(0);
	return encontrei;
}

void alterarUtilizador (char nick[],char pass[],int saldo) {
	int i;
	sem_DOWN(0);
	for (i = 0 ; i < MaxUtilizadores ; i++) {
		if (strcmp (u[i].nick, nick) == 0) {
			strcpy (u[i].pass, pass);
			u[i].saldo = saldo;
		}
	}
	sem_Up(0);
}

int existeViatura (char ID[]) {
	int i, encontrei = 0;
	sem_DOWN(1);
	for (i = 0 ; i < MaxViaturas && encontrei == 0 ; i++) {
		if (strcmp(v[i].ID, ID) == 0)
			encontrei = 1;
	}
	sem_Up(1);
	return encontrei;
}

void alterarViatura (char ID[],char cor[],char marca[]
		,char modelo[],char tipo[],int mudancas,char matricula[]) {
	int i;
	sem_DOWN(1);
	for (i = 0 ; i < MaxViaturas ; i++) {
		if (strcmp (v[i].ID, ID) == 0) {
			strcpy (v[i].cor, cor);
			strcpy (v[i].marca, marca);
			strcpy (v[i].modelo, modelo);
			strcpy (v[i].tipo, tipo);
			v[i].mudancas = mudancas;
			strcpy (v[i].matricula, matricula);
		}
	}
	sem_Up(1);
}

void escreverFicheiro_TXT (char nomef[]) {
	char txt[] = ".txt", aux[AUX_SIZE]="";
	strcpy(aux,nomef);
	strcat(aux,txt);
	FILE *f = fopen (aux, "w");
	if (f == NULL) {
		perror ("Erro de escrita");
		exit (1);
	}
	if(strcmp(aux,"utilizadores.txt") == 0){
		if (f==NULL) { perror("Erro de escrita: "); exit(1); } ;
		int i;
		sem_DOWN(0);
		for (i = 0 ; (i < MaxUtilizadores) && u[i].id != -1 ; i++) {
			Tutilizador tutilizador = u[i];
			fprintf(f, "%s;%s;%d;%s;%s;%s;%d\n",tutilizador.nick,tutilizador.pass
					,tutilizador.id,tutilizador.nome,tutilizador.email,tutilizador.turma
					,tutilizador.saldo);
		}
		sem_Up(0);
	}else if(strcmp(aux,"viaturas.txt") == 0){
		if (f==NULL) { perror("Erro de escrita: "); exit(1); } ;
		sem_DOWN(1);
		int i;
		for (i = 0 ; (i < MaxViaturas) && strcmp(v[i].ID,"-1") != 0 ; i++) {
			Tviatura tviatura = v[i];
			fprintf(f, "%s;%s;%s;%s;%s;%d;%s\n",tviatura.ID,tviatura.cor
					,tviatura.marca,tviatura.modelo,tviatura.tipo,tviatura.mudancas
					,tviatura.matricula);
		}
		sem_Up(1);
	}
	fclose(f);
	printf("o nome do ficheiro: %s \n",aux);
}

void escreverFicheiro_DAT (char nomef[]) {
	char dat[] = ".dat", aux[AUX_SIZE]="";
	strcpy(aux,nomef);
	strcat(aux,dat);
	FILE *f = fopen (aux, "w");
	int i;
	if (f == NULL) {
		perror("Erro de escrita no ficheiro .dat");
		exit(1);
	}
	if(strcmp(nomef,"utilizadores")==0){
		sem_DOWN(0);
		for(i = 0 ; i < MaxUtilizadores && u[i].id != -1 ; i++) {
			Tutilizador tutilizador = u[i];
			fwrite (&tutilizador, sizeof(Tutilizador), 1, f);
		}
		sem_Up(0);
	}else if(strcmp(nomef,"viaturas")==0){
		sem_DOWN(1);
		for (i = 0 ; i < MaxViaturas && strcmp(v[i].ID,"-1") != 0 ; i++) {
			Tviatura tviatura = v[i];
			fwrite (&tviatura, sizeof(Tviatura), 1, f);
		}
		sem_Up(1);
	}
	printf("o nome do ficheiro: %s \n",aux);
	fclose(f);
}

void imprimirMemoria () {
	int i, count = 0;
	printf("\nLista de utilizadores: \n\n");
	sem_DOWN(0);
	for(i = 0 ; i < MaxUtilizadores && u[i].id != -1 ; i++) {
		printf ("%s; ", u[i].nick);
		printf ("%s; ", u[i].pass);
		printf ("%d; ", u[i].id);
		printf ("%s; ", u[i].nome);
		printf ("%s; ", u[i].email);
		printf ("%s; ", u[i].turma);
		printf ("%d\n", u[i].saldo);
		count++;
	}
	sem_Up(0);
	printf ("Ha %d utilizadores na base de dados.\n", count);
	count = 0;
	printf ("\nLista de viaturas: \n\n");
	sem_DOWN(1);
	for (i = 0 ; i < MaxViaturas && (strcmp(v[i].ID, "-1") != 0) ; i++) {
		printf ("%s; ", v[i].ID);
		printf ("%s; ", v[i].cor);
		printf ("%s; ", v[i].marca);
		printf ("%s; ", v[i].modelo);
		printf ("%s; ", v[i].tipo);
		printf ("%d; ", v[i].mudancas);
		printf ("%s\n", v[i].matricula);
		count++;
	}
	sem_Up(1);
	printf ("Ha %d viaturas na base de dados.\n", count);
}

void viaturasOcupadas() {
	int i, count = 0;
	sem_DOWN(1);
	count = 0;
	time_t t = time(NULL);
	printf ("\nLista de viaturas ocupadas: \n\n");
	for (i = 0 ; i < MaxViaturas && (strcmp(v[i].ID, "-1") != 0) ; i++) {
		if(v[i].reserva.tipo != 0){
			printf ("%s; ", v[i].ID);
			switch (v[i].reserva.tipo) {
				case 1:
					printf ("foi reservada");
					break;
				case 2:
					printf ("foi alugada");
					break;
				case 3:
					printf ("foi alugada");
					break;
				default:
					break;
			}
			count++;
			printf(", há %ld minutos.\n",(t - v[i].reserva.tempo)/60);
		}

	}
	sem_Up(1);
	printf ("\nHa %d viaturas ocupadas.\n", count);
}

void viaturasLivres() {
	int i, count = 0;
	sem_DOWN(1);
	count = 0;
	printf ("\nLista de viaturas livres: \n\n");
	for (i = 0 ; i < MaxViaturas && (strcmp(v[i].ID, "-1") != 0) ; i++) {
		if(v[i].reserva.tipo == 0){
			printf (" %s \n", v[i].ID);
			count++;
		}

	}
	sem_Up(1);
	printf ("\nHa %d viaturas livres.\n", count);
}

int verificaPontoEVirgula(char toBeTested[]){
	if(strstr(toBeTested,";")) return 1;
	else return 0;
}

int isNumber(char aux[]) {
	int i;
	for(i = 0 ; aux[i] != '\0' ; i++) {
		if(!(isdigit(aux[i])))
			return 0;
	}
	return 1;
}
void menu () {
	int opcao=-1;
	while (opcao != 0) {
		char nick[NICKNAME_SIZE], pass[PASSWORD_SIZE], ID [VEICULO_ID_SIZE],
		cor[VEICULO_COLOR_SIZE], marca[VEICULO_MARCA_SIZE],
		modelo[VEICULO_MODELO_SIZE],tipo[VEICULO_TIPO_SIZE],
		matricula[VEICULO_MATRICULA_SIZE],aux[AUX_SIZE];
		int saldo = 0, mudancas = 0, b;
		printf ("\nMenu\n");
		printf ("1 - Ler dados para memória \n");
		printf ("2 - Imprimir memória \n");
		printf ("3 - Alterar utilizador \n");
		printf ("4 - Alterar viatura \n");
		printf ("5 - Guardar dados \n");
		printf ("6 - Viaturas Ocupadas \n");
		printf ("7 - Viaturas disponíveis \n");
		printf ("0 - Sair \n");
		printf ("\nEscolha uma opcao: ");
		fgets (aux, AUX_SIZE, stdin);

		opcao = atoi(aux);
		switch (opcao) {
			case 1:
				leitura (FICHEIRO_UTILIZADORES);
				leitura (FICHEIRO_VIATURAS); // mudar
				break;
			case 2:
				imprimirMemoria ();
				break;
			case 3:
				b=0;
				do {
					printf ("Insira o nickname do utilizador que pretende alterar: ");
					fgets (nick, NICKNAME_SIZE, stdin);
					nick[strlen(nick)-1] = '\0';
					if(existeUtilizador(nick) == 0)
						printf ("O nickname inserido nao pertence a nenhum dos utilizadores registados.\n");
					else
						b=1;
				} while (b==0);

				do {
					printf ("Introduza uma password com no máximo %d caracteres: ", PASSWORD_SIZE);
					fgets (pass, PASSWORD_SIZE, stdin);
					pass[strlen(pass)-1] = '\0';
				} while(verificaPontoEVirgula(pass));

				do {
					printf ("Insira o saldo (positivo ou zero): ");
					fgets (aux, AUX_SIZE, stdin);
					aux[strlen(aux)-1] = '\0';
					saldo = atoi(aux);
					if(!isNumber(aux))
						printf("Apenas insira caracteres numericos no saldo!\n");
				} while((!(isNumber(aux))) || (saldo < 0));
				printf("Feito!\n");
				alterarUtilizador(nick, pass, saldo);
				break;

			case 4:
				b=0;
				do {
					printf ("Insira o ID da viatura que pretende alterar: ");
					fgets (ID, VEICULO_ID_SIZE, stdin);
					ID[strlen(ID)-1] = '\0';
					if(existeViatura(ID) == 0)
						printf("O ID nao pertence a nenhuma das viaturas registadas.\n");
					else
						b=1;
				} while (b == 0);

				do {
					printf("Introduza uma cor com no máximo %d caracteres: ", VEICULO_COLOR_SIZE);
					fgets(cor, VEICULO_COLOR_SIZE, stdin);
					cor[strlen(cor)-1] = '\0';
				} while(verificaPontoEVirgula(cor));

				do {
					printf("Introduza uma marca com no máximo %d caracteres: ", VEICULO_MARCA_SIZE);
					fgets(marca, VEICULO_MARCA_SIZE, stdin);
					marca[strlen(marca)-1] = '\0';
				} while(verificaPontoEVirgula(marca));

				do {
					printf("Introduza um modelo com no máximo %d caracteres: ", VEICULO_MODELO_SIZE);
					fgets(modelo, VEICULO_MODELO_SIZE, stdin);
					modelo[strlen(modelo)-1] = '\0';
				} while(verificaPontoEVirgula(modelo));

				do {
					printf("Introduza um tipo (electrica/manual): ");
					fgets(tipo, VEICULO_TIPO_SIZE, stdin);
					tipo[strlen(tipo)-1] = '\0';
				} while((strcmp(tipo,"electrica")!=0) && (strcmp(tipo,"manual")!=0));

				do {
					printf("Insira o numero de mudancas: ");
					fgets(aux, AUX_SIZE, stdin);
					aux[strlen(aux)-1] = '\0';
					mudancas = atoi(aux);
					if(!isNumber(aux))
						printf("Apenas insira caracteres numericos nas mudancas!\n");
				} while((!(isNumber(aux))) || (mudancas <= 0));

				do {
					printf("Introduza uma matricula com no máximo %d caracteres: ", VEICULO_MATRICULA_SIZE);
					fgets(matricula, VEICULO_MATRICULA_SIZE, stdin);
					matricula[strlen(matricula)-1] = '\0';
				} while(verificaPontoEVirgula(matricula));

				printf("Feito!\n");
				alterarViatura(ID,cor,marca,modelo,tipo,mudancas,matricula);
				break;
			case 5:
				escreverFicheiro_DAT ("utilizadores");
				escreverFicheiro_DAT ("viaturas");
				escreverFicheiro_TXT("utilizadores");
				escreverFicheiro_TXT("viaturas");
				break;
			case 6:
				viaturasOcupadas();
				break;
			case 7:
				viaturasLivres();
				break;

			case 0:
				exit (0);
				break;
			default:
				printf ("\nOpcao rejeitada.");
		}
	}
}

int main () {
	cria_sem();
	criarMemoriaPartilhada();
	//cria_sem();
	menu ();
}


//v4

