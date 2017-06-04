/**
 * 
 */
package core;

/**
 * @author Aify
 *
 */
public class Cleaner extends Thread {
	
	public static long CHECK_INTERVAL = 300000; //5min*60sec*1000millis
	public static long MAX_MESSAGE_INTERVAL = 30000; //30s*1000millis
	
	// keeps track of whether or not the thread is fucked up.
	public boolean daijoubu = false;
	private long lastCheckTime;
	
	public Cleaner()
	{
		//Main.printFromCleaner("Cleaner init!");
		lastCheckTime = System.currentTimeMillis();
	}
		
	@Override
	public void run() {
		while (true) {
			//run periodically
			if((System.currentTimeMillis() - lastCheckTime) >= CHECK_INTERVAL)
			{
				lastCheckTime = System.currentTimeMillis();
				cleanup();
			}
		}
	}
	
	// tries to dead connections/threads
	public void cleanup() {
		//check list of client threads
		for(ClientThread ct : Main.connManager.cthreads)
		{
			//remove dead threads (check for daijobou or nonsync)
			boolean threadDeadlocked = (System.currentTimeMillis() - ct.timeOfLastMessage.getTime()) > MAX_MESSAGE_INTERVAL;
			
			if(!ct.daijoubu || threadDeadlocked)
			{
				Main.printFromCleaner("Client thread " + ct.toString() + " not responding and killed");
				
				//attempt to end it gracefully
				ct.interrupt();
			}
		}
		
		//check main thread for brokenness
		if(!Main.checkAllThreads())
		{
			//something really bad happened, burn it all
			Main.printError("Critical thread died, aborting server!");
			System.exit(1);
		}
		
	}
}
