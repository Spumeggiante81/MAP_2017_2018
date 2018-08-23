package server;

import java.net.*;
import java.io.*;

/**
 * Classe avviabile che gestisce le varie connessioni in ingresso, smistandole
 * in thread separati.
 */
public class MultiServer {
	private static int PORT = 8080;
	private static ServerSocket serverSocket;

	public static void main(String[] args) {
		int port = PORT;
		if (args.length > 1)
		{   
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
			}
		}
		new MultiServer(port).run();
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializza la porta ed invoca Run
	 * @param port porta la quale il server ascolter� connessioni in ingresso
	 */
	public MultiServer(int port)
	{
		try
		{
			InetAddress addr = InetAddress.getByName(null);
			System.out.println("Current machine address: " + addr.toString());
			serverSocket = new ServerSocket(port);
		}
		catch (UnknownHostException e)
		{
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	
	/**
	 * Istanzia un oggetto ServerSocket che pone in attesa di richiesta
	 * connessioni da parte del client. Ad ogni nuova richiesta di
	 * connessione verr� istanziato un oggetto ServerOneClient
	 */
	private void run()
	{
		while (true)
		{
			try
			{
				System.out.println("Waiting for an incoming connection...");
				Socket s = serverSocket.accept();
				System.out.println("Client connected!" + s.toString());
				new ServerOneClient(s).start();
			}
			catch (UnknownHostException e)
			{
				System.out.println(e.getMessage());
				break;
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			}
	
		}
	}
}
