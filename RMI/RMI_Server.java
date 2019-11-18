/************************************
*     Ricatti Luigi Francesco       *
*           RMI_Server.java         *
*                                   *
************************************/


// Implementazione del Server RMI

/*----- import ---- */

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class RMI_Server extends UnicastRemoteObject implements RMI_interfaceFile
{
    private static final long serialVersionUID = 1L;
	
    // qui eventuali variabili e strutture dati 
    /* private static final int N = 10;
     static Noleggio tabella[] = null; */
    
    

    // Costruttore
    public RMI_Server() throws RemoteException 
    {
	super();
	// TODO Auto-generated constructor stub
    }

    
    // Eventuali metodi legati alla struttura dati
    
    /*
	
	public static void stampa()
	{
		System.out.println("Identificatore\tData\tGiorni\tModello\tCosto giornaliero\n");
		
		for(int i=0; i<N; i++)
		{
			System.out.println(tabella[i].id + "\t" + 
				tabella[i].giorno + "/" + tabella[i].mese + "/" + tabella[i].anno + "\t" +
				tabella[i].giorni + "\t" + tabella[i].modello + "\t" + tabella[i].costo );
		}
	}
	
	*/
	
	
	
	// Implementazione dei metodi
		
	@Override
	public int metodo1(int valore) throws RemoteException 
	{
		int numero = valore;
		
		// Se ci sono errori sollevo RemoteException
		if(numero == -1) throw new RemoteException();
		
		return numero;
	}
	
	@Override
	public int metodo2(int valore) throws RemoteException 
	{
		int numero = valore;
		
		// Se ci sono errori sollevo RemoteException
		if(numero == -1) throw new RemoteException();
		
		return numero;
	}
	
	 // Avvio del Server RMI
	public static void main(String[] args) 
	{
		int registryPort = 1099;
	   	String registryHost = "localhost";
	    	String serviceName = "ServerRMI";
	    	
	    	// Controllo dei parametri della riga di comando
		if (args.length != 0 && args.length != 1) 
		{
		      System.out.println("Sintassi: ServerRMI [REGISTRYPORT]");
		      System.exit(1);
		}
		if (args.length == 1) 
		{
		    try
		    {
			registryPort = Integer.parseInt(args[0]);
		    }
		    catch (Exception e) 
		    {
			System.out.println("Sintassi: ServerRMI [REGISTRYPORT], REGISTRYPORT intero");
			System.exit(2);
		    }
		}
	    
	    	// Qui inizializzo eventuali strutture dati
	    	
	    	/*
	    	
	    	tabella = new Noleggio[N];
	    
	    	for(int i=0; i<N; i++)
	    		tabella[i] = new Noleggio();
	    
	    	tabella[0] = new Noleggio("00001", 13, 02, 2013, 10, "uomo", 15);
		tabella[1] = new Noleggio("00002", 24, 04, 2013, 15, "donna", 5);
		tabella[2] = new Noleggio("00003", 20, 03, 2013, 5, "bambino", 10);
		tabella[3] = new Noleggio();
		tabella[3].id = "00004";
		tabella[3].modello = "uomo";
		tabella[3].costo = 20;
		tabella[4] = new Noleggio();
		tabella[4].id = "00005";
		tabella[4].modello = "uomo";
		tabella[4].costo = 25;
		
		stampa();
		
		*/
	    	
	    	// Registrazione del servizio
	   	String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
	    
	   	try
	    	{
	    		RMI_Server serverRMI = new RMI_Server();
	    		Naming.rebind(completeName, serverRMI);
	    		System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
	    	}
	    	catch(Exception e)
	    	{
	    		System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
	    		e.printStackTrace();
	    		System.exit(1);
	    	}
	}
}
