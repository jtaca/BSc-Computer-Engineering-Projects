typedef struct {
	long tipo;
	struct {
		char operacao[20];
		char info1[20];
		char info2[20];
		int myid;
	} dados;
} MsgClientServer;

typedef struct {
	long tipo;
	struct {
		char texto[50];
		int valor1;
		int valor2;
		int status;
	} dados;
} MsgServerClient;

#define KEYMESSAGE 0x781781

//"Mascaras" dos status
#define LOGGED_OUT -10
#define LOOP 0

