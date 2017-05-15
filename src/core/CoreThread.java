/**
 * 
 */
package core;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author Aify
 *
 */
public class CoreThread extends Thread {
	// keeps track of whether or not the thread is fucked up.
	public boolean daijoubu = false;
	
	// this queue represents a list of "actions" for the core thread to execute. Every sub thread (connection) 
	// adds messages to this queue in order for the core of the server to update its state, which is essentially the
	// master game state. 
	private LinkedList<Message> messageQueue = new LinkedList<Message>();

	public synchronized void addMessageToQueue(Message m) {
		messageQueue.add(m);
	}
	
	public synchronized Message getMessageFromQueue() throws NoSuchElementException {
		return messageQueue.removeFirst();
	}
	
	@Override
	public void run() {
		
		try {
			while (true) {
				try {
					Message m = getMessageFromQueue();
					m.execute();
				} catch (NoSuchElementException e) {
					Main.printError("=CORE= : Empty Queue");
				}
				
			}
		} catch (Exception e) {
			Main.printError(e.toString());
		}
		
		System.out.println("====================Core Thread Terminated====================");
	}
	
}
