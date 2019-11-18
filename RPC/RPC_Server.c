/************************************
*     Ricatti Luigi Francesco       *
*           RPC_Server.c            *
*                                   *
************************************/

#include <stdio.h>
#include <rpc/rpc.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <dirent.h>
#include <fcntl.h>
	
#include "RPC_xFile.h"


/*

// Eventuali strutture di supporto

#define TABLEDIM 10
#define LISTADIM 3

typedef struct
{
	char parolaChiave[LENGTH];
}Parola;

typedef struct
{
  	char titolo [LENGTH];
  	char nomeFile [LENGTH];
  	char partita [LENGTH];
  	int inizio;
  	int fine;
  	Parola parole [LISTADIM];
} Moviola;

static Moviola tabella[TABLEDIM];
static int inizializzato=0;

// Eventuali funzioni per la visualizzazione e inizializzazione di una struttura dati

void stampa()
{
	int i,j;
	
	printf("\nTitolo\tFile\tPartita\tInizio\tFine\t");
	for(j=0;j<LISTADIM;j++)
	{
		printf("Parola%d\t", j+1);
	}
	printf("\n");

	for(i=0;i<TABLEDIM;i++)
	{
		printf("\n%s\t%s\t%s\t%d\t%d\t", tabella[i].titolo, 
						tabella[i].nomeFile, 
						tabella[i].partita,
						tabella[i].inizio, 
						tabella[i].fine);
		for(j=0;j<LISTADIM;j++)
		{
			printf("%s\t", tabella[i].parole[j].parolaChiave);
		}
	}
}

void inizializza()
{
	int i,j;

	if(inizializzato==1)
		return;

	for(i=0;i<TABLEDIM;i++)
	{
		strcpy(tabella[i].titolo,"L");
		strcpy(tabella[i].nomeFile,"L");
		strcpy(tabella[i].partita,"L");
		tabella[i].inizio = -1;
		tabella[i].fine = -1;
		for(j=0;j<LISTADIM;j++)
		{
			strcpy(tabella[i].parole[j].parolaChiave,"L");
		}
	}

	strcpy(tabella[0].titolo,"mov1");
	strcpy(tabella[0].nomeFile,"1.avi");
	strcpy(tabella[0].partita,"14062010_Italia_Paraguay");
	tabella[0].inizio = 1800;
	tabella[0].fine = 1845;
	strcpy(tabella[0].parole[0].parolaChiave,"fallo");
	strcpy(tabella[0].parole[2].parolaChiave,"Gattuso");

	inizializzato = 1;

	stampa();
}

*/

int * primafunzione_1_svc(Operandi *op, struct svc_req *rp)
{
	static int ris;
	
	// inizializza() qui richiamo l'inizializzazione della struttura dati se presente
	
	// Calcolo ris secondo la logica della funzione
	
	// stampa();
	
	return (&ris);
}

int * secondafunzione_1_svc(Operandi *op, struct svc_req *rp)
{
	static int ris;
	
	// inizializza() qui richiamo l'inizializzazione della struttura dati se presente
	
	// Calcolo ris secondo la logica della funzione
	
	// stampa();
	
	return (&ris);
}

