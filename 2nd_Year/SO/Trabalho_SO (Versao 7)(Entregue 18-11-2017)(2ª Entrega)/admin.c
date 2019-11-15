#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <ctype.h>
#include "estruturas.h"

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

Tutilizador m[MaxUtilizadores];
Tviatura n[MaxViaturas];

// Funcao auxiliar usada na aula pratica da semana 6
void obter_substring(char aux[], char resultado[], char separador, int indice) {
	int i, j=0, meu_indice = 0;
	for (i=0; aux[i] != '\0'; i++) {
		if ( aux[i] == separador ) { meu_indice++;}
		else if (meu_indice == indice) { resultado[j++]=aux[i];}
	}
	resultado[j]='\0';
}

void lerFicheiro_TXT (char nomef[]) {
	FILE *f = fopen(nomef, "r");
	char aux[AUX_SIZE],tmp[250];
	int i=0;
	if (f==NULL) {
		perror("Erro de leitura: ");
		exit(1);
	}
	if(strcmp(nomef,"utilizadores.txt") == 0){
		printf("nome: %s \n",nomef);
		for (i=0; i<MaxUtilizadores; i++) {
			strcpy (m[i].nick, "-1");
    }
		i=0;
		while ( fgets(aux, 250, f) != NULL ) {
			if (i == MaxUtilizadores) {
				printf("Memoria cheia\n"); exit(1);
			}
			aux[strlen(aux)-1]='\0';
			if(!(strcmp(aux," ")==0)){
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
				m[i++] = tutilizador;
			}
		}
	}else if(strcmp(nomef,"viaturas.txt") == 0){
		printf("nome: %s \n",nomef);
		for (i=0; i<MaxViaturas; i++) {
                strcpy (n[i].ID, "-1");
        }
		i=0;
		while ( fgets(aux, 250, f) != NULL ) {
			if (i == MaxViaturas) {
				printf("Memoria cheia\n"); exit(1);
			}
			aux[strlen(aux)-1]='\0';
			if(!(strcmp(aux," ")==0)){
				Tviatura tviatura;
				obter_substring(aux, tviatura.ID,';', 0);
				obter_substring(aux, tviatura.cor,';', 1);
				obter_substring(aux, tviatura.marca,';', 2);
				obter_substring(aux, tviatura.modelo,';', 3);
				obter_substring(aux, tviatura.tipo, ';', 4);
				obter_substring(aux, tmp, ';', 5);
				tviatura.mudancas = atof(tmp);
				obter_substring(aux, tviatura.matricula, ';', 6);
				n[i++] = tviatura;
			}
		}
	}
	fclose(f);
}

void lerFicheiro_DAT (char nomef[]) {
	int i;
		FILE *f = fopen (nomef, "r");
		if(strcmp(nomef,"utilizadores.dat")==0){
			printf("o nome do ficheiro: %s \n",nomef);
			for (i=0; i<MaxUtilizadores; i++) {
				strcpy (m[i].nick, "-1");
			}
			i=0; Tutilizador tutilizador;
			while (fread (&tutilizador, sizeof(tutilizador), 1, f) > 0) {
				if (i == MaxUtilizadores) {
					printf ("há demasiados utilizadores no .dat \n");
					exit (2);
				}
				m[i++] = tutilizador;
			}
		}else if(strcmp(nomef,"viaturas.dat")==0){
			printf("o nome do ficheiro: %s \n",nomef);
			for (i=0; i<MaxViaturas; i++) {
				strcpy (n[i].ID, "-1");
			}
			i=0; Tviatura tviatura;
			while (fread (&tviatura, sizeof(tviatura), 1, f) > 0) {
				if (i == MaxViaturas) {
					printf ("há demasiadas viaturas no .dat \n");
					exit (2);
				}
				n[i++] = tviatura;
			}
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
	int i;
	for (i=0; i<MaxUtilizadores; i++) {
		if (strcmp(m[i].nick, nick) == 0)
			return 1;
	}
	return 0;
}

void alterarUtilizador (char nick[],char pass[],int saldo) {
	int i;
	for (i=0; i<MaxUtilizadores; i++) {
		if (strcmp (m[i].nick, nick) == 0) {
			strcpy (m[i].pass, pass);
			m[i].saldo = saldo;
		}
	}
}

int existeViatura (char ID[]) {
	int i;
	for (i=0; i<MaxUtilizadores; i++) {
		if (strcmp(n[i].ID, ID) == 0)
			return 1;
	}
	return 0;
}

void alterarViatura (char ID[],char cor[],char marca[]
		,char modelo[],char tipo[],int mudancas,char matricula[]) {
	int i;
	for (i=0; i<MaxViaturas; i++) {
		if (strcmp (n[i].ID, ID) == 0) {
			strcpy (n[i].cor, cor);
			strcpy (n[i].marca, marca);
			strcpy (n[i].modelo, modelo);
			strcpy (n[i].tipo, tipo);
			n[i].mudancas = mudancas;
			strcpy (n[i].matricula, matricula);
		}
	}
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
		printf("o nome do ficheiro: %s \n",aux);
		if (f==NULL) { perror("Erro de escrita: "); exit(1); } ;
		int i;
		for (i=0; (i < MaxUtilizadores) && (strcmp(m[i].nick,"-1")) ; i++ ) {
			Tutilizador tutilizador = m[i];
			fprintf(f, "%s;%s;%d;%s;%s;%s;%d\n",tutilizador.nick,tutilizador.pass
					,tutilizador.id,tutilizador.nome,tutilizador.email,tutilizador.turma
					,tutilizador.saldo);
		}
	}else if(strcmp(aux,"viaturas.txt") == 0){
		printf("o nome do ficheiro: %s \n",aux);
		if (f==NULL) { perror("Erro de escrita: "); exit(1); } ;
		int i;
		for (i=0; (i < MaxUtilizadores) && ( !(strcmp(n[i].ID,"-1")==0) ) ; i++ ) {
			Tviatura tviatura = n[i];
			fprintf(f, "%s;%s;%s;%s;%s;%d;%s\n",tviatura.ID,tviatura.cor
					,tviatura.marca,tviatura.modelo,tviatura.tipo,tviatura.mudancas
					,tviatura.matricula);
		}
	}
	fclose(f);
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
		printf("o nome do ficheiro: %s \n",aux);
		for (i=0; i < MaxUtilizadores && strcmp(m[i].nick,"-1");i++) {
			Tutilizador u = m[i];
			fwrite (&u, sizeof(Tutilizador), 1, f);
		}
	}else if(strcmp(nomef,"viaturas")==0){
		printf("o nome do ficheiro: %s \n",aux);
		for (i=0; i < MaxViaturas && ( !(strcmp(n[i].ID,"-1")==0));i++) {

			Tviatura v = n[i];
			fwrite (&v, sizeof(Tviatura), 1, f);

		}
	}
	fclose(f);
}

void imprimirMemoria () {
	int i;
	int count = 0;
	printf ("\nLista de utilizadores: \n\n");
	for (i=0; i<MaxUtilizadores; i++) {
		if (!(strcmp(m[i].nick,"-1")==0)) {
			printf ("%s; ", m[i].nick);
			printf ("%s; ", m[i].pass);
			printf ("%d; ", m[i].id);
			printf ("%s; ", m[i].nome);
			printf ("%s; ", m[i].email);
			printf ("%s; ", m[i].turma);
			printf ("%d\n\n", m[i].saldo);
			count++;
		}
	}
	printf ("Ha %d utilizadores na base de dados.\n", count);
	count = 0;
	printf ("\nLista de viaturas: \n\n");
	for (i=0; i<MaxViaturas; i++) {
		if (!(strcmp(n[i].ID,"-1")==0)) {
			printf ("%s; ", n[i].ID);
			printf ("%s; ", n[i].cor);
			printf ("%s; ", n[i].marca);
			printf ("%s; ", n[i].modelo);
			printf ("%s; ", n[i].tipo);
			printf ("%d; ", n[i].mudancas);
			printf ("%s\n\n",n[i].matricula);
			count++;
		}
	}
	printf ("Ha %d viaturas na base de dados.\n",count);
}

int verificaPontoEVirgula(char toBeTested[]){ ////////////////////////////////////
	if(strstr(toBeTested,";"))
		return 1;
	else
		return 0;
}

int isNumber(char aux[]) { // Se vier o sinal - ou + ele também conta como que se não fosse um numero
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
		int saldo = 0, mudancas = 0;
		printf ("\nMenu\n");
		printf ("1 - Ler dados para memória \n");
		printf ("2 - Imprimir memória \n");
		printf ("3 - Alterar utilizador \n");
		printf ("4 - Alterar viatura \n");
		printf ("5 - Guardar dados \n");
		printf ("0 - Sair \n");
		printf ("\nEscolha uma opcao: ");
		fgets (aux, AUX_SIZE, stdin);

		opcao = atoi(aux);
		switch (opcao) {
			case 1:
				leitura ("utilizadores");
				leitura ("viaturas");
				break;
			case 2:
				imprimirMemoria ();
				break;
			case 3:
				do {
					printf ("Insira o nickname do utilizador que pretende alterar: ");
					fgets (nick, NICKNAME_SIZE, stdin);
					nick[strlen(nick)-1] = '\0';
					if(existeUtilizador(nick) == 0)
						printf ("O nickname inserido nao pertence a nenhum dos utilizadores registados.\n");
				} while (existeUtilizador(nick) == 0);

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

				//printf ("Saldo: %d\n",saldo);
				printf("Feito!\n");
				alterarUtilizador(nick, pass, saldo);
				break;

			case 4:
				do {
					printf ("Insira o ID da viatura que pretende alterar: ");
					fgets (ID, VEICULO_ID_SIZE, stdin);
					ID[strlen(ID)-1] = '\0';
					if(existeViatura(ID) == 0)	
						printf("O ID nao pertence a nenhuma das viaturas registadas.\n");
				} while (existeViatura(ID) == 0);

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
			case 0:
				exit (0);
				break;
			default:
				printf ("\nOpcao rejeitada.");
		}
	}
}

int main () {
	menu ();
}
