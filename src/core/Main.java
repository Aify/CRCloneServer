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
	public static int PORT = 9876;
	
	public static CoreThread mainProcess;
	public static ConnectionManager connManager;
	public static Cleaner janitor;
	public static CRCloneModel gameModel;
	public static boolean verboseMode = true; // default true
	public static Date serverStartTime;
	public static Scanner scan;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start a debug file right away
		serverStartTime = new Date(); // file name is always the server start time
		
		if (args.length >= 1) {
			CRCloneModel.MAX_PLAYERS = Integer.parseInt(args[0]); 
		}
		
		// create a game model
		gameModel = new CRCloneModel();
		
		// start the core thread
		mainProcess = new CoreThread();
		mainProcess.start();

		// start the connection manager
		connManager = new ConnectionManager();
		connManager.start();
		
		// start the cleaner
		janitor = new Cleaner();
		janitor.start();	
		
		// User input loop for server diagnosis and access/commands
		scan = new Scanner(System.in);
		while (true) {
			String input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help")) {
				printHelpMenu();
			} else if (input.equalsIgnoreCase("exit")) {
				System.exit(0);
			} else if (input.equalsIgnoreCase("list")) {
			 	// TODO:
			} else if (input.equalsIgnoreCase("clear")) {
				// TODO:
			} else if (input.equalsIgnoreCase("cleanup")) {
				// TODO: see cleanup fnc in Cleaner.java
			} else if (input.equalsIgnoreCase("chkthds")) {
				if (!checkAllThreads()) {
					System.exit(1);
				}
			} else {
				printFromUI("Sorry, " + input + "is not a listed command. Try 'help' for a list of commands");
			}
		}
		
	}
	
	// lists commands and how to use server
	public static void printHelpMenu() {
		System.out.println("======================================= HELP =======================================");
		System.out.println("Usage: java -jar CRServer.java <number of player>");
		System.out.println("Commands:");
		System.out.println("    exit 	- Exits the server");
		System.out.println("    list 	- Lists all current connections and connection details");
		System.out.println("    clear 	- Forces all connections to close");
		System.out.println("    cleanup - Runs the connection cleaner");
		System.out.println("    help    - Displays this menu ");
		System.out.println("    chkthds - Checks to make sure all key threads are still in an ok state");
		System.out.println("            	> Exits the program if threads aren't okay");
	}
	
	public static boolean checkAllThreads() {
		if (mainProcess.daijoubu) {
			if (connManager.daijoubu) {
				if (janitor.daijoubu) {
					printFromUI("All Threads OK");
					return true;
				} else {
					printFromUI("Cleaner Thread Daijoubu Janaii");
					return false;
				}
			} else {
				printFromUI("Connection Manager Thread Daijoubu Janaii");
				return false;
			}
		} else {
			printFromUI("Core Thread Daijoubu Janaii");
			return false;
		}
	}
	
	
	/** 
		the methods that wrap SYSO and stick letters in the front are used
		to help with debugging so we can see which threads messages are coming from 
	**/
	
	// use this to print something from the Main class
	public static void printFromUI(String s) {
		System.out.println("UIT === === ===: " + s);
		FileSaver.saveFileAppend(serverStartTime.getTime() + ".txt", "UIT === === ===: " + s);
	}
	
	// use this to print something from the CoreThread
	public static void printFromCore(String s) {
		System.out.println("=== CRE === ===: " + s);
		FileSaver.saveFileAppend(serverStartTime.getTime() + ".txt", "=== CRE === ===: " + s);
	}
	
	// use this to print something from the CoreThread
	public static void printFromCleaner(String s) {
		System.out.println("=== === JAN ===: " + s);
		FileSaver.saveFileAppend(serverStartTime.getTime() + ".txt", "=== === JAN ===: " + s);
	}
	
	// use this to print something from the connection manager
	public static void printFromCM(String s) {
		System.out.println("=== === === CMT: " + s);
		FileSaver.saveFileAppend(serverStartTime.getTime() + ".txt", "=== === === CMT: " + s);
	}
	
	public static void printFromClient(String s) {
		System.out.println("=== === === CLT: " + s);
		FileSaver.saveFileAppend(serverStartTime.getTime() + ".txt", "=== === === CTT: " + s);
	}
	
	public static void printError(String s) {
		if (verboseMode) {
			System.out.println("EEE EEE EEE EEE: " + s);
		}
		
		// always save error to file
		FileSaver.saveFileAppend(serverStartTime.getTime() + ".txt", "EEE EEE EEE EEE: " + s);
		
	}
	
}
