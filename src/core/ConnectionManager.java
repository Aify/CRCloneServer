/**
 * 
 */
package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * @author Aify
 *
 */
public class ConnectionManager extends Thread {
	// keeps track of whether or not the thread is fucked up.
	public boolean daijoubu = false;
	
	// list of currently connected clientthreads
	private ArrayList<ClientThread> cthreads = new ArrayList<ClientThread>();
	private ServerSocket listener;
	
	@Override
	public void run() {
		// initialize the listening socket
		try {
			listener = new ServerSocket(Main.PORT);
		} catch (IOException e) {
			Main.printError(e.toString());
		}
		
		daijoubu = true;
		
		// listen for connections 
		while (true) {
			try {
				listener.accept();
			} catch (IOException e) {
				// TODO: Deal with this
			}
		}
	}
}
