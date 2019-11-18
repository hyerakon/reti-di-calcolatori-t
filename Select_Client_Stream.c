/************************************
*     Ricatti Luigi Francesco       *
*     Select_Client_Stream.c        *
*           		            *
************************************/

 /* ---- VERSIONE 1 -------------------------------------------- */
/* 	Una connessione per trasferimento
*	ossia per ogni ciclo si attua una richiesta su una connessione
*	che si può chiudere al termine del file 
*	-- LA CREAZIONE DELLA SOCKET ALL'INTERNO DEL CICLO -- */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#define DIM_BUFF 100

//#define LENGTH_FILE_NAME 20


int main(int argc, char *argv[])
{
      int sd, nread, nwrite, port;
      char ok, buff[DIM_BUFF];
      struct hostent *host;
      struct sockaddr_in serverAddress;

      //char nome_file[LENGTH_FILE_NAME];

      /* CONTROLLO ARGOMENTI ---------------------------------- */
      if(argc!=3)
      {
          printf("Error:%s serverAddress serverPort\n", argv[0]);
          exit(1);
      }

      printf("Client avviato\n");

      /* PREPARAZIONE INDIRIZZO SERVER ----------------------------- */
      memset((char *)&serverAddress, 0, sizeof(struct sockaddr_in));
      serverAddress.sin_family = AF_INET;

      host = gethostbyname(argv[1]);
      if (host == NULL)
      {
          printf("%s not found in /etc/hosts\n", argv[1]);
          exit(2);
      }

      //Controllo secondo argomento --> numero di porta intero
      nread =0;
      while(argv[2][nread] != '\0')
      {
          if(argv[2][nread] < '0' || argv[2][nread] >'9')
          {
              printf("Secondo argomento non intero\n");
              exit(2);
          }
          nread++;
      }
      port = atoi(argv[2]);
      if (port <1024 || port >65535)
      {
          printf("Porta non corretta, range numerico sbagliato\n");
          exit(3);
      }

      serverAddress.sin_addr.s_addr=((struct in_addr*) (host->h_addr))->s_addr;
      serverAddress.sin_port = htons(atoi(argv[2]));


      /* CORPO DEL CLIENT: */
      /* ciclo di accettazione di richieste di file ------- */

    printf("Richieste di file fino alla fine del file di input\n");
    printf("Qualsiasi tasto per procedere, EOF per terminare\n");
    printf("-- specificare qui le richieste --- ");

    while (gets(...))
    {
        	/* CREAZIONE E CONNESSIONE SOCKET (BIND IMPLICITA) ----------------- */
        	/* in questo schema è necessario ripetere creazione, settaggio opzioni e connect */
        	/* ad ogni ciclo, perchè il client fa una nuova connect ad ogni ciclo */

          sd=socket(AF_INET, SOCK_STREAM, 0);
          if (sd <0)
          {perror("apertura socket "); exit(3);}
          printf("Creata la socket sd=%d\n", sd);

          if (connect(sd,(struct sockaddr *) &serverAddress, sizeof(struct sockaddr))<0)
          {perror("Errore in connect"); exit(4);}
          printf("Connect ok\n");


        	/*if (write(sd, nome_file, (strlen(nome_file)+1))<0)
        	{
        	     perror("write");
        	     close(sd);
        	     printf("Nome del file da richiedere: ");
        	     continue;
        	}
        	printf("Richiesta del file %s inviata... \n", nome_file);

        	if (read(sd, &ok, 1)<0)
        	{
        	     perror("read");
        	     close(sd);
        	     printf("Nome del file da richiedere: ");
        	     continue;
        	}

	       if (ok=='S')
	       {
	             printf("Ricevo il file:\n");

	             while((nread=read(sd, buff, sizeof(buff)))>0)
	             {
              	    if ((nwrite=write(1, buff, nread))<0)
              	    {
              	       perror("write");
              	       break;
              	    }
	             }
          	  if ( nread<0 )
          	  {
            	    perror("read");
            	    close(sd);
            	    printf("Nome del file da richiedere: ");
            	    continue;
          	  }      
	       }
	       else if (ok=='N') printf("File inesistente\n");

        printf("Chiudo connessione\n");
        close(sd);
      	// chiudo qua perchè faccio una nuova connect ad ogni ciclo...

        printf("Nome del file da richiedere: ");*/

      }//while

      printf("\nClient Stream: termino...\n");
      exit(0);

    }








 /* ---- VERSIONE 2 -------------------------------------------- */
/* 	Una UNICA connessione 
*	-- LA CREAZIONE DELLA SOCKET FUORI DEL CICLO -- */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#define DIM_BUFF 100
#define LENGTH_FILE_NAME 20

/********************************************************
typedef struct
{
    struttura dati
}
Request;
/********************************************************/

int main(int argc, char *argv[])
{
    int sd, nread, port;
    char c, ok, nome_file[LENGTH_FILE_NAME];
    struct hostent *host;
    struct sockaddr_in serverAddress;  



    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if(argc!=3)
    {
        printf("Error:%s serverAddress serverPort\n", argv[0]);
        exit(1);
    }
  
    printf("Client avviato\n");


    /* PREPARAZIONE INDIRIZZO SERVER ----------------------------- */
    memset((char *)&serverAddress, 0, sizeof(struct sockaddr_in));
    serverAddress.sin_family = AF_INET;

    host = gethostbyname(argv[1]);
    if (host == NULL)
    {
        printf("%s not found in /etc/hosts\n", argv[1]);
        exit(2);
    }

    //Controllo secondo argomento --> numero di porta intero
    nread =0;
    while(argv[2][nread] != '\0')
    {
        if(argv[2][nread] < '0' || argv[2][nread] >'9')
        {
            printf("Secondo argomento non intero\n");
            exit(2);
        }
        nread++;
    }
    port = atoi(argv[2]);
    if (port <1024 || port >65535)
    {
        printf("Porta non corretta, range numerico sbagliato\n");
        exit(3);
    }

  
    serverAddress.sin_addr.s_addr=((struct in_addr*) (host->h_addr))->s_addr;
    serverAddress.sin_port = htons(atoi(argv[2]));


    /* CREAZIONE E CONNESSIONE SOCKET (BIND IMPLICITA) ----------------- */
    sd=socket(AF_INET, SOCK_STREAM, 0);
    if (sd <0)
      {perror("apertura socket "); exit(3);}
    printf("Creata la socket sd=%d\n", sd);

    if (connect(sd,(struct sockaddr *) &serverAddress, sizeof(struct sockaddr))<0)
      {perror("Errore in connect"); exit(4);}
    printf("Connect ok\n");


    /* CORPO DEL CLIENT: */
    /* ciclo di accettazione di richieste di file ------- */

    printf("Richieste di file fino alla fine del file di input\n");
    printf("Qualsiasi tasto per procedere, EOF per terminare\n");
    printf("---richieste ---: ");

    while (gets(...))
    {
/*
          if (write(sd, nome_file, (strlen(nome_file)+1))<0)
          {
              perror("write");
              break;
          }
          printf("Richiesta del file %s inviata... \n", nome_file);

          if (read(sd, &ok, 1)<0)
          {
              perror("read");      
              break;
          }

          if (ok=='S')
          {
            printf("Ricevo il file:\n");
            while((nread=read(sd,&c,1))>0) // leggo a caratteri per individuare il fine file
              if (c!='\0')
              {
                write(1,&c,1);
              }
              else break;
            if( nread < 0 )
            {
              perror("read");
              break;
            }
          }
          else if (ok=='N') printf("File inesistente\n");

          printf("Nome del file da richiedere: ");*/

    }//while

    printf("\nClient Stream: termino...\n");
  
    // Chiusura socket
    close(sd);

    exit(0);
 
}














