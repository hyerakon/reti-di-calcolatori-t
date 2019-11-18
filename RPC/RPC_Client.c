/************************************
*     Ricatti Luigi Francesco       *
*           RPC_Client.c            *
*                                   *
************************************/

#include <stdio.h>
#include <rpc/rpc.h>
#include <stdlib.h>
#include <string.h>

#include "RPC_xFile.h"

#define LENGTH 256

main(int argc, char *argv[])
{

	CLIENT *cl;/*Gestore del trasporto*/
	char *server; /*Nome HOST*/
	
	int *ris;
	char input[LENGTH];
	Operandi op;
	int numero;
	// Qui eventuali altre variabili di strutture utilizzate
	// Input in;
	// Aggiunta aggiunta;
	// Lista* lista;
	
	/********** CONTROLLI ***************/	
	if (argc != 2)
	{
		fprintf(stderr, "Usage: %s host\n", argv[0]);
		exit(1);
	}

	server = argv[1];

	// Possibilità di cambiare il protocollo di trasporto
	cl = clnt_create(server, OPERAZIONIPROG, OPERAZIONIVERS, "udp");
	if (cl == NULL)
	{
		clnt_pcreateerror(server);
		exit(1);
	}

	// Interazione utente
	printf("\nInserire: \n1 Funzione1 \n2 Funzione2 \n ^D per terminare: ");

	while(gets(input))
	{
		if(strcmp(input, "1")!=0 && strcmp(input, "2")!=0)
		{
			printf("Argomento ERRATO!\n");
		}
		
		if(strcmp(input, "1") == 0)
		{
		    printf("\nScelta: Funzione1\n");

		    // .....interagisco con l'utente per caricare la struttura dati da utilizzare
		    /*
		    		RICORDA COME SI FA LA MALLOC 

				   printf("Inserisci Primo Parametro: ");
				   gets(input);
				   op.primo_paramentro =(char*)malloc(strlen(input) + 1);
				   strcpy(op.primo_paramentro, input);
	

				RICORDA COME SI FA PER I NUMERI: 
				
				   printf("Inserisci Secondo parametro: ");
				   while (scanf("%i", &numero) != 1)
				   {
				      do
				      {
					      printf("%c ", c);
				      }while (c!= '\n');
				      printf("Inserisci Secondo Parametro: ");
				    }
				    op.secondo_paramentro = numero;
				    gets(input);
				
				OPPURE
				printf("Inserisci NUMERO: ");
				gets(input);
				numero = atoi(input);


		    */
		    // Invocazione remota dopo aver caricato la struttura dati
		    ris = primafunzione_1(&op, cl);

		    // Controllo del risultato
		    if(ris == NULL) 
		    { 
			    clnt_perror(cl, server); 
			    exit(1); 
		    }
		    //Eventuale errore di logica del programma
		    if(*ris == -1) 
		    {  
			    printf("Problemi nell'esecuzione\n");
		    }
		    //Tutto ok      	
		    else 
		    {   
			    printf("Risultato ricevuto da %s: %i\n", server, *ris);           
		    }
		    printf("\nFunzione1 conclusa\n");
		}
		
		if(strcmp(input, "2") == 0)
		{
		    printf("\nScelta: Funzione2\n");

		    // .....interagisco con l'utente per caricare la struttura dati da utilizzare

		    // Invocazione remota dopo aver caricato la struttura dati
		    ris = secondafunzione_1(&op, cl);

		    // Controllo del risultato
		    if(ris == NULL) 
		    { 
			    clnt_perror(cl, server); 
			    exit(1); 
		    }
		    //Eventuale errore di logica del programma
		    if(*ris == -1) 
		    {  
			    printf("Problemi nell'esecuzione\n");
		    }
		    //Tutto ok      	
		    else 
		    {   
			    printf("Risultato ricevuto da %s: %i\n", server, *ris);             
		    }
		    printf("\nFunzione2 conclusa\n");
		}

		printf("\nInserire: \n1 Funzione1 \n2 Funzione2 \n ^D per terminare: ");
	}

	// Libero le risorse distruggendo il gestore di trasporto
	clnt_destroy(cl);
	printf("Fine Client \n");
}
