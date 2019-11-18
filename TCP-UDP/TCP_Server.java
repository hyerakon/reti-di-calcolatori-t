/************************************
*     Ricatti Luigi Francesco       *
*     TCP_Server.java		    *
*           		            *
************************************/
import java.io.*;
import java.net.*;


public class TCP_Server
{
    // porta nel range consentito 1024-65535!
    // dichiarata come statica perche caratterizza il server
	private static final int PORT = 54321;

	public static void main (String[] args) throws IOException
	{
      	// Porta sulla quale ascolta il server
		int port = -1;

      	// Controllo degli argomenti
		try 
		{
			if (args.length == 1) 
			{
				port = Integer.parseInt(args[0]);
	    		// controllo che la porta sia nel range consentito 1024-65535
				if (port < 1024 || port > 65535) 
				{
					System.out.println("Usage: java TCP_Server [serverPort>1024]");
					System.exit(1);
				}
			} 
			else if (args.length == 0) 
			{
				port = PORT;
			} 
			else 
			{
				System.out.println("Usage: java TCP_Server or java TCP_Server port");
				System.exit(1);
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Problemi, i seguenti: ");
			e.printStackTrace();
			System.out.println("Usage: java TCP_Server or java TCP_Server port");
			System.exit(1);
		}
		/******** INIZIALIZZAZIONE TABELLA ************/

		/*********************************************/

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try 
		{
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			System.out.println("TCP_Server: avviato ");
			System.out.println("Server: creata la server socket: " + serverSocket);
		}
		catch (Exception e) 
		{
			System.err.println("Server: problemi nella creazione della server socket: "+ e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		try 
		{
			while (true) 
			{
				System.out.println("Server: in attesa di richieste...\n");

				try 
				{
	    			// bloccante finchè non avviene una connessione
					clientSocket = serverSocket.accept();
	    			// timeout per evitare che un thread si blocchi indefinitivamente
					clientSocket.setSoTimeout(60000);

					System.out.println("Server: connessione accettata: " + clientSocket);
				}
				catch (Exception e) 
				{
					System.err.println("Server: problemi nella accettazione della connessione: "+ e.getMessage());
					e.printStackTrace();
					continue;
	    			// il server continua a fornire il servizio ricominciando dall'inizio del ciclo
				}

	  			// delego il servizio ad un nuovo thread
				try 
				{
					new TCP_ServerThread(clientSocket).start();
				}
				catch (Exception e) 
				{
					System.err.println("Server: problemi nel server thread: "+ e.getMessage());
					e.printStackTrace();
					continue;
	   				// il server continua a fornire il servizio ricominciando dall'inizio del ciclo
				}

			} 
		}
	     // qui catturo le eccezioni non catturate all'interno del while
	     // in seguito alle quali il server termina l'esecuzione
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("TCP_Server: termino...");
			System.exit(2);
		}

	}
} // TCP_Server


// Thread lanciato per ogni richiesta accettata
class TCP_ServerThread extends Thread
{
	private Socket clientSocket = null;

    //COSTRUTTORE - va opportunamente creato
	public TCP_ServerThread(Socket clientSocket) 
	{
		this.clientSocket = clientSocket;
	}

	public void run() 
	{
		System.out.println("Info: Attivazione server thread. (Thread=" + getName() + ")");
		DataInputStream inSock;
		DataOutputStream outSock;
		try 
		{
			try 
			{
				// creazione stream di input e out da socket
				inSock = new DataInputStream(clientSocket.getInputStream());
				outSock = new DataOutputStream(clientSocket.getOutputStream());
			}
			catch (IOException ioe) 
			{
				System.out.println("Problemi nella creazione degli stream di input/output "+ "su socket: ");
				ioe.printStackTrace();
				// il server continua l'esecuzione riprendendo dall'inizio del ciclo
				return;
			}
			catch (Exception e) 
			{
				System.out.println("Problemi nella creazione degli stream di input/output "+ "su socket: ");
				e.printStackTrace();
				// il server continua l'esecuzione riprendendo dall'inizio del ciclo
				return;
			}

	    	// Ricezione della richiesta
			String richiesta;

			try 
			{
				richiesta = inSock.readUTF();
				if(richiesta == null)
				{
					System.out.println("Problemi nella ricezione");
					clientSocket.shutdownInput();
				}
				System.out.println("Richiesta: " + richiesta);
				System.out.println("chiusura input della socket " + clientSocket);
			}
			catch(SocketTimeoutException ste)
			{
				System.out.println("Timeout scattato: ");
				ste.printStackTrace();
				clientSocket.close();
				System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, solo invio per continuare: ");
				// il client continua l'esecuzione riprendendo dall'inizio del ciclo
				return;          
			}        
			catch (Exception e) 
			{
				System.out.println("Problemi nella ricezione della richiesta: ");
				e.printStackTrace();
				// servo nuove richieste
				return;
			}

		/*	if(richiesta.equalsIgnoreCase("U"))
			{																*/
				String risposta = null;
	    		// Elaborazione e invio della risposta

				try
				{
					outSock.writeUTF(risposta);
					clientSocket.shutdownOutput();
					System.out.println("Terminata connessione con " + clientSocket);
				}
				catch(SocketTimeoutException ste)
				{
					System.out.println("Timeout scattato: ");
					ste.printStackTrace();
					clientSocket.close();
					System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, solo invio per continuare: ");
					// il client continua l'esecuzione riprendendo dall'inizio del ciclo
					return;          
				}              
				catch (Exception e) 
				{
					System.err.println("\nProblemi nell'invio della risposta: "+ e.getMessage());
					e.printStackTrace();
					clientSocket.close();
					System.out.println("Terminata connessione con " + clientSocket);
					return;
					// il server continua a fornire il servizio ricominciando
					// dall'inizio del ciclo esterno
				}
			}
	/*		else if(richiesta.equalsIgnoreCase("D"))
			{
				//....come primma
			} 												*/
		}
	// qui catturo le eccezioni non catturate all'interno del while
	// in seguito alle quali il server termina l'esecuzione
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Errore irreversibile, TCP_ServerThread: termino...");
			System.exit(3);
		}
    } // run

} // TCP_ServerThread


