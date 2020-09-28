package Server.ServerController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Server.ServerController.ServerThread;

/**
 * Creates the object instances of the class and connects with client to get and
 * pass the informations requested by the user
 * 
 * @author A. Mohar, T. Pritchard, P. Patel
 * @version 2.0
 * @since April 14, 2020
 */
public class ServerCommunication {
	/**
	 * server Socket
	 */
	private ServerSocket serverSocket;

	/**
	 * Socket where client connects
	 */
	private Socket aSocket;

	/**
	 * Threadpool to runs multiple clients(coming soon)
	 */
	 private ExecutorService pool;

	/**
	 * Tracks number of clients connected
	 */
    private int clientCount;
    
    private ServerThread myT;

	/**
	 * Initializes the server object, also calls other functions which connects to
	 * client
	 * 
	 * @param port port where server is hosted
	 */
	public ServerCommunication(int port) {
		try {
			serverSocket = new ServerSocket(port);
			 pool = Executors.newCachedThreadPool();
			System.out.println("Server is initialized!!	waiting for connection... ");
		} catch (Exception e) {
			e.getMessage();
		}
		clientCount = 0;

		initializeServer(); // initialize server with a client
	}

	/**
	 * Connects to the client, defines the input and output paths
	 */
	public void initializeServer() {
		while (true) {
			try {
				aSocket = serverSocket.accept(); // client joining
				clientCount++;
                System.out.println("Connection accepted by server! Client number: " + clientCount + " joined.");
                //new ServerThread(aSocket, this);
                pool.execute(new ServerThread(aSocket, this));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Asks the server administration if the server is still running or needs to be
	 * closed
	 */
	public void clientDisconnect() {
		clientCount--;
		System.out.println(clientCount + " clients are still connected.");
		while (true) {
			if (clientCount == 0) {
				System.out.println(
						"\nDo you want to close server? No client is connected.\nPress 'y' to close, 'n' will keep server running.");
				String input = (new Scanner(System.in)).nextLine();
				if (input.equals("y") || input.equals("Y")) {
					System.out.println("Server closed");
					System.exit(0);
				} else if (input.equals("n") || input.equals("N")) {
					System.out.println("Server is ready to accept clients");
					break;
				} else {
					System.out.println("\n\nInput not recognised");
					continue;
				}
			}
		}
	}

	public static void main(String[] args) {
		new ServerCommunication(9898);
	}
}