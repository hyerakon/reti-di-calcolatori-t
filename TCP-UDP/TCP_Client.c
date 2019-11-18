/************************************
*     Ricatti Luigi Francesco       *
*     TCP_Client.c                  *
*           		                    *
************************************/

/* Client Stream versione 1: una socket per ogni richiesta (dentro al ciclo while)
 * il client fa una nuova connect ad ogni ciclo */

/******** #INCLUDE ********/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

/******** #DEFINE *******/
#define DIM_BUFF 256


int main(int argc, char *argv[])
{
      int sd, nread, nwrite, port, num;
      char buff[DIM_BUFF];
      struct hostent *host;
      struct sockaddr_in servaddr;

    /* CONTROLLO ARGOMENTI ---------------------------------- */
      if(argc!=3)
      {
	printf("Error:%s serverAddress serverPort\n", argv[0]);
	exit(1);
      }
      /* PREPARAZIONE INDIRIZZO SERVER ----------------------------- */
      memset((char *)&servaddr, 0, sizeof(struct sockaddr_in));
      servaddr.sin_family = AF_INET;
      host = gethostbyname(argv[1]);

      // Controllo host
      if (host == NULL)
      {
	printf("%s not found in /etc/hosts\n", argv[1]);
	exit(1); 
      }
      // Controllo che la porta sia un intero
      num=0;
	    while( argv[2][num]!= '\0' )
	    {
		  if( (argv[2][num] < '0') || (argv[2][num] > '9') )
		  {
			  printf("Argomento non intero\n");
			  exit(2);
		  }
		  num++;
	    }

      // Controllo che la porta sia nel range
      port = atoi(argv[2]);
      if (port < 1024 || port > 65535)
      {
	printf("Error: porta non valida!\n");
	printf("1024 <= port <= 65535\n");
	exit(2);
      }
       else
  {
    servaddr.sin_addr.s_addr=((struct in_addr*) (host->h_addr))->s_addr;
    servaddr.sin_port = htons(atoi(argv[2]));
  }
  
  printf("Client avviato\n");
  
  
  /* CORPO DEL CLIENT: */
  /* ciclo di accettazione di richieste da utente ------- */
  printf("\n************************************\n");  
  printf("Richieste dell'utente, EOF per terminare: ");
  
  // setto la condizione per il ciclo
  int condition =0;
  while(condition)
  {
    /* CREAZIONE E CONNESSIONE SOCKET (BIND IMPLICITA) ----------------- */
    /* in questo schema è necessario ripetere creazione, settaggio opzioni e connect */
    /* ad ogni ciclo, perchè il client fa una nuova connect ad ogni ciclo */

    sd=socket(AF_INET, SOCK_STREAM, 0);
    if (sd <0)
    {
      perror("apertura socket "); 
      exit(3);
    }
    printf("Creata la socket sd=%d\n", sd);

    if (connect(sd,(struct sockaddr *) &servaddr, sizeof(struct sockaddr))<0)
    {
      perror("Errore in connect");
      exit(4);
    }
    printf("Connect ok\n");

    //qui logica applicativa
    printf("\n************************************\n");

    // Write su socket
    printf("Invio richiesta\n");
    while((nwrite=write(sd, buff, DIM_BUFF))>0)
    {
      if(nwrite < 0
      {
	printf("Errore nella scrittura su socket!");
	close(sd);
	exit(7);
      }
      else
      {
	write(1,buff,nwrite);
	printf("ho scritto su socket %d byte", nwrite);
      }
    }
    printf("Richiesta inviata\n");

    // Chiusura socket in spedizione -> invio dell'EOF
    shutdown(sd,1);

    // Read dalla socket
    printf("Ricevo la risposta:\n");
    while((nread=read(sd,buff,DIM_BUFF))>0)
    {
      if(nread < 0)
      {
	printf("Errore nella lettura su socket!");
	lose(sd);
	exit(6);
      }
      else
      {
	write(1,buff,nread);
	printf("ho letto da socket %d byte", nwrite);
      }
    }
    printf("Risposta ricevuta\n");
    
    // Chiusura socket in ricezione
    shutdown(sd, 0);
    close(sd); // Close fuori dal ciclo per client con una connessione
        
    printf("Nuova richiesta, EOF per terminare: ");
    
  }//while
  
  printf("\nClient: termino...\n");
  exit(0);
  
}//main
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
      
