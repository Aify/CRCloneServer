/**
 * 
 */
package core;

import java.util.Date;
import java.util.Scanner;

import util.FileSaver;

/**
 * @author Aify
 *
 */
public class Main {

	// config settings
	public static int PORT = 6789;
	
	public static CoreThread mainProcess;
	public static ConnectionManager connManager;
	public static Cleaner janitor;
	public static boolean verboseMode;
	public static Date serverStartTime;
	public static Scanner scan;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start a debug file right away
		serverStartTime = new Date(); // file name is always the server start time
		
		// start the core thread
		mainProcess = new CoreThread();
		mainProcess.start();
		
		// start the connection manager
		connManager = new ConnectionManager();
		
		// start the cleaner
		janitor = new Cleaner();
		
		// User input loop for server diagnosis and access/commands
		scan = new Scanner(System.in);
		while (true) {
			String input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help")) {
				// display help menu (commands list)
			} else if (input.equalsIgnoreCase("exit")) {
				System.exit(0);
			} else if (input.equalsIgnoreCase("list")) {
			 	// TODO:
			} else if (input.equalsIgnoreCase("clear")) {
				// TODO:
			} else if (input.equalsIgnoreCase("cleanup")) {
				// TODO: see cleanup fnc in Cleaner.java
			} else if (input.equalsIgnoreCase("chkthds")) {
				checkAllThreads();
			} else {
				printFromUI("Sorry, " + input + "is not a listed command. Try 'help' for a list of commands");
			}
		}
		
	}
	
	// lists commands and how to use server
	public static void printHelpMenu() {
		System.out.println("======================================= HELP =======================================");
		System.out.println("Commands:");
		System.out.println("    exit 	- Exits the server");
		System.out.println("    list 	- Lists all current connections and connection details");
		System.out.println("    clear 	- Forces all connections to close");
		System.out.println("    cleanup - Runs the connection cleaner");
		System.out.println("    help    - Displays this menu ");
		System.out.println("    chkthds - Checks to make sure all key threads are still in an ok state");
		System.out.println("            	> Exits the program if threads aren't okay");
	}
	
	public static void checkAllThreads() {
		if (mainProcess.daijoubu) {
			if (connManager.daijoubu) {
				if (janitor.daijoubu) {
					printFromUI("All Threads OK");
					return;
				}
				printFromUI("Cleaner Thread Daijoubu Janaii");
				return;
			}
			printFromUI("Connection Manager Thread Daijoubu Janaii");
			return;
		}
		printFromUI("Core Thread Daijoubu Janaii");
		return;
	}
	
	// the methods that wrap SYSO and stick letters in the front are used
	// to help with debugging so we can see which threads messages are coming from
	public static void printFromUI(String s) {
		System.out.println("UIT === ===: " + s);
	}
	
	public static void printFromCore(String s) {
		System.out.println("=== CRE ===: " + s);
	}
	
	public static void printError(String s) {
		if (verboseMode) {
			printFromUI(s);
		}
		
		// always save error to file
		FileSaver.saveFileAppend(serverStartTime.toString(), s);
	}
	
}
