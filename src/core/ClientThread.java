/**
 * 
 */
package core;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Aify
 *
 */
public class ClientThread extends Thread {
	// keeps track of whether or not the thread is fucked up.
	public boolean daijoubu = false;
	
	public Scanner inStream;
	public PrintStream outStream;
	
	public volatile Date timeOfLastMessage;
	
	public Socket connection;
	
	public ClientThread(Socket s) {
		try {
			connection = s;
			inStream = new Scanner(s.getInputStream());
			outStream = new PrintStream(s.getOutputStream());
		} catch (IOException e) {
			Main.printError("Unable to open streams: " + e.toString());
		}
	}
	
	@Override
	public void run() {
		while (daijoubu) {
			try {
				// read input
				String s = inStream.nextLine(); // messages from client should be temrinated with a new line char (\n)
				Message m = new Message(s, connection.getInetAddress().toString());
				Main.mainProcess.addMessageToQueue(m);
				timeOfLastMessage = new Date();
				
			} catch (Exception e) {
				daijoubu = false;
				Main.printError(e.toString());
			}
		}
		
		inStream.close();
		outStream.close();
	}
}
