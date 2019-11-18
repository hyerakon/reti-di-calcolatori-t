/************************************
*     Ricatti Luigi Francesco       *
*     UDP_Unicast_Server.java       *
*                                   *
************************************/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDP_Unicast_Server 
{
  // porta nel range consentito 1024-65535!
  // dichiarata come statica perchè caratterizza il server
  private static final int PORT = 54321;

  public static void main(String[] args) 
  {
    //  dichiarazione socket e strutture dati di supporto
    DatagramSocket socket = null;
    DatagramPacket packet = null;
    byte[] buf = new byte[256];
    int port = -1;

    // controllo argomenti input: 0 oppure 1 argomento (porta)
    if ((args.length == 0)) 
    {
      port = PORT;
    } 
     else if (args.length == 1) 
    {
      try 
      {
	port = Integer.parseInt(args[0]);
	// controllo che la porta sia nel range consentito 1024-65535
      	if (port < 1024 || port > 65535) 
      	{
      	  System.out.println("Usage: java UDP_Unicast_Server [serverPort>1024]");
      	  System.exit(1);
      	}
      } 
      catch (NumberFormatException e) 
      {
      	System.out.println("Usage: java UDP_Unicast_Server [serverPort>1024]");
      	System.exit(1);
      }
    } 
    else 
    {
      System.out.println("Usage: java UDP_Unicast_Server [serverPort>1024]");
      System.exit(1);
    }

    // inizializzazione socket e strutture dati di supporto
    try 
    {
      socket = new DatagramSocket(port);
      packet = new DatagramPacket(buf, buf.length);
      System.out.println("Creata la socket: " + socket);
    } 
    catch (SocketException e) 
    {
      System.out.println("Problemi nella creazione della socket: ");
      e.printStackTrace();
      System.exit(1);
    }

    // relizzazione logica di servizio
    try 
    {
      // dichiarazione oggetti per la lettura/scrittura dei dati come stream di
      // byte nel pacchetto UDP
      ByteArrayInputStream biStream = null;
      DataInputStream diStream = null;
      ByteArrayOutputStream boStream = null;
      DataOutputStream doStream = null;
      byte[] data = null;

      // QUI DICHIARAZIONE DI VARIABILI NECESSARIE PER LA REALIZZAZIONE
      // DELLA LOGICA DEL PROGRAMMA

      while (true) 	
      {
	System.out.println("\nIn attesa di richieste...");

	// ricezione del datagramma	
	try 
	{
	  packet.setData(buf);
	  socket.receive(packet);
	} 
	catch (IOException e)
	{
	  System.err.println("Problemi nella ricezione del datagramma: "+ e.getMessage());
	  e.printStackTrace();
	  continue;
	  // il server continua a fornire il servizio ricominciando dall'inizio
	  // del ciclo
	}

	try 
	{
	  biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
	  diStream = new DataInputStream(biStream);
	  
	  // QUI ESTRAZIONE RICHIESTA DALLO STREAM DI INPUT
	  // ad esempio, per la lettura della stringa 

	  String richiesta;
	  richiesta = diStream.readUTF();
	}
	catch (Exception e) 
	{
	  System.err.println("Problemi nella lettura della richiesta: ");
	    e.printStackTrace();
	  continue;
	  // il server continua a fornire il servizio ricominciando dall'inizio
	  // del ciclo
	}

	// Realizzazione logica di programma e invio della risposta
	try 
	{
	  // QUI LOGICA PER LA REALIZZAZIONE DELLE FUNZIONALITA' RICHIESTE

	  boStream = new ByteArrayOutputStream();
	  doStream = new DataOutputStream(boStream);

	  // QUI INSERIMENTO DATI DI INPUT NELLO STREAM DI OUTPUT
	  doStream.writeUTF("?");

	  // inserimento dati nel pacchetto UDP
	  data = boStream.toByteArray();
	  packet.setData(data, 0, data.length);

	  // invio pacchetto UDP al pari
	  socket.send(packet);
	} 
	catch (IOException e) 
	{
	  System.err.println("Problemi nell'invio della risposta: " + e.getMessage());
	  e.printStackTrace();
	  continue;
	  // il server continua a fornire il servizio ricominciando dall'inizio
	  // del ciclo
	}
      } // while
    }
    // qui catturo le eccezioni non catturate all'interno del while
    // in seguito alle quali il server termina l'esecuzione
    catch (Exception e) 
    {
      e.printStackTrace();
    }
    
    System.out.println("UDP_Unicast_Server: termino...");
    socket.close();
  }
}
