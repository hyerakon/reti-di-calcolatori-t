/************************************
*     Ricatti Luigi Francesco       *
*     TCP_Client.java	            *
*           		            *
************************************/


/* CLIENTE CON CONNESSIONE*/

import java.net.*;
import java.io.*;

public class TCP_Client 
{

  /**
  * @param args - Usage: java TCP_Client <address> <port>
  */
  
 	public static void main(String[] args) throws IOException 
  	{
	     /*
	     * Come argomenti si devono passare un nome di host valido e una porta
	     *  
	     */
	     InetAddress addr = null;
	     int port = -1;

	      // Controllo argomenti 
	     try
	     {
	     	if(args.length == 2)
	     	{
	     		addr = InetAddress.getByName(args[0]);
	     		port = Integer.parseInt(args[1]);
	     	} 
	     	else
	     	{
	     		System.out.println("Usage: java TCP_Client serverAddr serverPort");
	     		System.exit(1);
	     	}
	     } 
	     catch(NumberFormatException e)
	     {
	     	System.out.println("Numero di porta errato: " + args[1]);
	     	e.printStackTrace();
	     	System.out.println("Usage: java TCP_Client serverAddr serverPort");
	     	System.exit(1);
	     }
	     catch(Exception e)
	     {
	     	System.out.println("Problemi, i seguenti: ");
	     	e.printStackTrace();
	     	System.out.println("Usage: java TCP_Client serverAddr serverPort");
	     	System.exit(1);
	     }

	     /** ----- Inizializzazione strutture dati Cliente ----- **/
	     Socket socket = null;
	     DataInputStream inSock = null;
	     DataOutputStream outSock = null;
	     String richiesta = null;

	      // Creazione socket 
	      // Creazione stream di input/output su socket
	     try
	     {
	     	socket = new Socket(addr, port);
		    // Setto il timeout per non bloccare indefinitivamente il client
	     	socket.setSoTimeout(30000);
	     	System.out.println("Creata la socket: " + socket);
	     	inSock = new DataInputStream(socket.getInputStream());
	     	outSock = new DataOutputStream(socket.getOutputStream());
	     }
	     catch(IOException e)
	     {
	     	System.out.println("Problemi nella creazione degli stream su socket: ");
	     	e.printStackTrace();
	     	System.exit(1);
	     }
	     catch(Exception e)
	     {
	     	System.out.println("Problemi nella creazione della socket: ");
	     	e.printStackTrace();
	     	System.exit(2);
	     }

	      // Eventuale stream per l'interazione con l'utente da tastiera

	     BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	     System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");
	     try
	     {

	     	while((richiesta = stdIn.readLine()) != null)
	     	{
		  		/*
			  	if(richiesta.equalsIgnoreCase("U") || richiesta.equalsIgnoreCase("D"))
		      	{
		      		outSock.writeUTF(richiesta);
		      		System.out.println("Inviata richiesta: " + richiesta);
		      	}
		      	outSock.flush();

		      	if(richiesta.equalsIgnoreCase("U"))
		      	{*/
		      		try
		      		{
				      // Elaborazione richiesta e invio
		      			outSock.writeUTF(richiesta);
		      			System.out.println("Inviata richiesta: " + richiesta);
				      // chiudo output della socket 
		      			socket.shutdownOutput();
		      			System.out.println("Chiusura output della socket: " + socket);
		      		}
		      		catch(Exception e)
		      		{
		      			System.out.println("Problemi nell'invio della richiesta " + richiesta );
		      			e.printStackTrace();
		      			System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
		      			continue;
				      // il client continua l'esecuzione riprendendo dall'inizio del ciclo
		      		}

			    	//--> qui logica 


			    	// Ricezione della risposta

		      		String risposta;
		      		try
		      		{
		      			risposta = inSock.readUTF();
		      			System.out.println("Risposta: " + risposta);
				      // chiudo input della socket
		      			socket.shutdownInput();
		      			System.out.println("Chiusura input della socket: " + socket);
		      		}
		      		catch(SocketTimeoutException ste)
		      		{
		      			System.out.println("Timeout scattato: ");
		      			ste.printStackTrace();
		      			socket.close();
		      			System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
		      			continue;
				      	// il client continua l'esecuzione riprendendo dall'inizio del ciclo         
		      		}

		      		catch (EOFException e) 
		      		{
		      			System.out.println("Raggiunta la fine delle ricezioni, chiudo...");
		      			socket.close();
		      			System.out.println("TCP_Client: termino...");
		      			System.exit(0);
		      		}
		      		catch(Exception e)
		      		{
		      			System.out.println("Problemi nella ricezione della risposta, i seguenti: ");
		      			e.printStackTrace();
		      			socket.close();
		      			System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
		      			continue;
				      // il client continua l'esecuzione riprendendo dall'inizio del ciclo
		      		}
			/*	}
				else if(richiesta.equalsIgnoreCase("U"))
		      	{
					.... uguale con try catch come sopra ... e se ci sono altri if continua
				} 
				else System.out.println("Errore nella scelta..."); */

				// Tutto ok, pronto per nuova richiesta
		      	System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");	

			}// while

			System.out.println("TCP_Client: termino...");
		}

	    // qui catturo le eccezioni non catturate all'interno del while
	    // quali per esempio la caduta della connessione con il server
	    // in seguito alle quali il client termina l'esecuzione
		catch(Exception e)
		{
			System.err.println("Errore irreversibile, il seguente: ");
			e.printStackTrace();
			System.err.println("Chiudo!");
			System.exit(3); 
		}
	} // main
}


/* CLIENTE CON MULTICONNESSIONE*/


/************************************
*     Ricatti Luigi Francesco       *
*     TCP_Client.java	            *
*           		            *
************************************/

import java.net.*;
import java.io.*;

public class TCP_Client 
{
  public static void main(String[] args) throws IOException 
  {
    // Inizializzazione argomenti
    InetAddress addr = null;
    int port = -1;

    // Controllo argomenti 
    try
    {
      if(args.length == 2)
      {
	addr = InetAddress.getByName(args[0]);
	port = Integer.parseInt(args[1]);
      } 
      else
      {
	System.out.println("Usage: java TCP_Client serverAddr serverPort");
	System.exit(1);
      }
    } 
    catch(NumberFormatException e)
    {
      System.out.println("Numero di porta errato: " + args[1]);
      e.printStackTrace();
      System.out.println("Usage: java TCP_Client serverAddr serverPort");
      System.exit(1);
    }
    catch(Exception e)
    {
      System.out.println("Problemi, i seguenti: ");
      e.printStackTrace();
      System.out.println("Usage: java TCP_Client serverAddr serverPort");
      System.exit(1);
    }

    // Inizializzazione strutture dati Cliente
    Socket socket = null;
    DataInputStream inSock = null;
    DataOutputStream outSock = null;
    String richiesta = null;

    // Eventuale stream per l'interazione con l'utente da tastiera
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");

    try
    {
      while((richiesta = stdIn.readLine()) != null)
      {  
	// Creazione socket
	// Creazione stream di input/output su socket
	try
	{
	  socket = new Socket(addr, port);
	  // Setto il timeout per non bloccare indefinitivamente il client
	  socket.setSoTimeout(30000);
	  System.out.println("Creata la socket: " + socket);
	  inSock = new DataInputStream(socket.getInputStream());
	  outSock = new DataOutputStream(socket.getOutputStream());
	}
	catch(IOException e)
	{
	  System.out.println("Problemi nella creazione degli stream su socket: ");
	  e.printStackTrace();
	  System.out.println("Nuova richiesta: ");
	  System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
	  continue;
	  // il client continua l'esecuzione riprendendo dall'inizio del ciclo
	}
	catch(Exception e)
	{
	  System.out.println("Problemi nella creazione della socket: ");
	  e.printStackTrace();
	  System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");
	  continue;
	  // il client continua l'esecuzione riprendendo dall'inizio del ciclo
	}
	

	// Elaborazione e trasmissione della richiesta
	try
	{
	  // Elaborazione richiesta e invio
	  outSock.writeUTF(richiesta);
	  System.out.println("Inviata richiesta: " + richiesta);
	  // chiudo output della socket 
	  socket.shutdownOutput();
	  System.out.println("Chiusura output della socket: " + socket);
	}
	catch(Exception e)
	{
	  System.out.println("Problemi nell'invio della richiesta " + richiesta );
	  e.printStackTrace();
	  System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
	  continue;
	  // il client continua l'esecuzione riprendendo dall'inizio del ciclo
	}

	// Ricezione della risposta
	String risposta;
	try
	{
	  risposta = inSock.readUTF();
	  System.out.println("Risposta: " + risposta);
	  // chiudo input della socket
	  socket.shutdownInput();
	  System.out.println("Chiusura input della socket: " + socket);
	}
	catch(SocketTimeoutException ste)
	{
	  System.out.println("Timeout scattato: ");
	  ste.printStackTrace();
	  socket.close();
	  System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
	  continue;
	  // il client continua l'esecuzione riprendendo dall'inizio del ciclo         
	}
	catch (EOFException e) 
	{
	  System.out.println("Raggiunta la fine delle ricezioni, chiudo...");
	  socket.close();
	  System.out.println("GetFileClient: termino...");
	  System.exit(0);
	}
	catch(Exception e)
	{
	  System.out.println("Problemi nella ricezione della risposta, i seguenti: ");
	  e.printStackTrace();
	  socket.close();
	  System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            
	  continue;
	  // il client continua l'esecuzione riprendendo dall'inizio del ciclo
	}

	// Tutto ok, pronto per nuova richiesta
	System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, oppure immetti input: ");		            

      }// while
      
      System.out.println("TCP_Client: termino...");
    }
    // qui catturo le eccezioni non catturate all'interno del while
    // quali per esempio la caduta della connessione con il server
    // in seguito alle quali il client termina l'esecuzione
    
    catch(Exception e)
    {
      System.err.println("Errore irreversibile, il seguente: ");
      e.printStackTrace();
      System.err.println("Chiudo!");
      System.exit(3); 
    }

  } // main
}

*/
