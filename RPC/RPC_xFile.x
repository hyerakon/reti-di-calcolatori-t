/************************************
*     Ricatti Luigi Francesco       *
*            RPC_xFile.x            *
*                                   *
************************************/

// Struttura dati da utilizzare in questo esempio
struct Operandi
{
	string primo_parametro<40>;
	int secondo_parametro;
};

/* Esempi di altre strutture dati utilizzabili

const LENGTH=30;

struct Input
{
	char nomePartita[LENGTH];
  	int inizio;
  	int fine;
};

struct MoviolaRisposta
{
	char titolo[LENGTH];
	char nomeFile[LENGTH];
	int inizio;
	int fine;
};

struct Lista
{
	int numMoviole;
	MoviolaRisposta listaMoviole[8];
};

struct Aggiunta
{
	char titolo[LENGTH];
	char parolaChiave[LENGTH];
};

*/

program OPERAZIONIPROG
{
	version OPERAZIONIVERS
	{
		int PRIMAFUNZIONE(Operandi) = 1;
		int SECONDAFUNZIONE(Operandi) = 2;
		// Ed eventuali altre funzioni
	} = 1;
} = 0x20000013;
