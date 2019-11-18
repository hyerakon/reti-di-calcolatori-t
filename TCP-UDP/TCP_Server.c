/************************************
*     Ricatti Luigi Francesco       *
*     TCP_Server.c                  *
*           		            *
************************************/


/******** #INCLUDE ********/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

/*** #DEFINE **/
#define DIM_BUFF 256
/********************************************************/
// Gestore dei figli
void gestore(int signo)
{
	int stato;
	printf("Esecuzione gestore di SIGCHILD\n");
	wait(&stato);
}
/********************************************************/

int main(int argc, char **argv)
{
	int  listen_sd, conn_sd, port, len, num, pid;
	char buff[DIM_BUFF];
	const int on = 1; //--> usata in setsockopt
	struct sockaddr_in cliaddr, servaddr;
	struct hostent *host;

	/*** ----  Controllo argomenti ---- **/
	if(argc!=2)
	{
		printf("Error: %s port\n", argv[0]);
		exit(1);
	}

	// Controllo che la porta sia un intero
	num=0;
	while( argv[1][num]!= '\0' )
	{
		if( (argv[1][num] < '0') || (argv[1][num] > '9') )
		{
			printf("Argomento non intero\n");
			exit(2);
		}
		num++;
	}

	// Controllo che la porta sia nel range di validità
	port = atoi(argv[1]);
	if (port < 1024 || port > 65535)
	{
		printf("Error: porta fuori range");
		printf("1024 <= port <= 65535\n");
		exit(2);
	}
	printf("Server avviato\n");

	// **  INIZIALIZZAZIONE INDIRIZZO SERVER E BIND **/
	memset ((char *)&servaddr, 0, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = INADDR_ANY;
	servaddr.sin_port = htons(port);

	// Creazione, Bind e settaggio della Socket di ascolto
	listen_sd=socket(AF_INET, SOCK_STREAM, 0);

	if(listen_sd <0)
	{
		perror("creazione socket ");
		exit(1);
	}
	printf("Server: creata la socket d'ascolto per le richieste di ordinamento, fd=%d\n", listen_sd);

	if(setsockopt(listen_sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on))<0)
	{
		perror("set opzioni socket d'ascolto");
		exit(1);
	}
	printf("Server: set opzioni socket d'ascolto ok\n");

	if(bind(listen_sd,(struct sockaddr *) &servaddr, sizeof(servaddr))<0)
	{
		perror("bind socket d'ascolto");
		exit(1);
	}
	printf("Server: bind socket d'ascolto ok\n");

	if (listen(listen_sd, 5)<0)
	{
		perror("listen");
		exit(1);
	}
	printf("Server: listen ok\n");

	// AGGANCIO GESTORE PER EVITARE FIGLI ZOMBIE
	signal(SIGCHILD, gestore);

	// Ciclo di ricezione delle richieste
	for(;;)
	{
		len=sizeof(struct sockaddr_in);
		if((conn_sd = accept(listen_sd, (struct sockaddr *)&clientaddr, &len))<0)
		{
		    if(errno=EINTR)
		    {
			// forzo la continuazione della Accept a fronte della terminazione di un figlio
			continue;
		    }
		    else
		    {
		      perror("accept");
		      exit(1);
		    }
		}

		if((pid=fork()) < 0)
		{
		    perror("fork error\n");
		    exit(1);
		}

		 /* PROCESSO FIGLIO */
		else if( pid == 0 )
		{
			close(listen_sd);

			host=gethostbyaddr((char *)&clientaddr.sin_addr, sizeof(clientaddr.sin_addr), AF_INET);

			if (host == NULL)
			{
				printf("client host information not found\n");
				continue;
			}
			else
				printf("Server (figlio pid: %d): host client e' %s \n",getpid(), host->h_name);

			printf("Ricezione dal client\n");
			read(conn_sd,buff,sizeof(buff));
			printf("Server legge %s\n", buff);

			printf("Server risponde..");
			write(conn_sd,buff,sizeof(buff));

			close(conn_sd);
		}

		// Padre
		else
		{
			close(conn_sd);
		}

	} // ciclo for infinito
	/* NEVER ARRIVES HERE */
  exit(0);
}
