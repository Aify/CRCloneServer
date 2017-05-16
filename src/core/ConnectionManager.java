/**
 * 
 */
package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Aify
 *
 */
public class ConnectionManager extends Thread {
	// keeps track of whether or not the thread is fucked up.
	public boolean daijoubu = false;
	private ServerSocket listener;
	
	// list of currently connected clientthreads
	public volatile ArrayList<ClientThread> cthreads = new ArrayList<ClientThread>();
	
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
				Socket s = listener.accept();
				ClientThread ct = new ClientThread(s);
				ct.start();
				cthreads.add(ct);
				// do other stuff?
			} catch (IOException e) {
				// TODO: Deal with this
			}
		}
	}
}
