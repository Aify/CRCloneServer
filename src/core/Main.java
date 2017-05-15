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

	public static CoreThread mainProcess;
	public static boolean verboseMode;
	public static Date serverStartTime;
	public static Scanner scan;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start a debug file right away
		serverStartTime = new Date(); // file name is always the server start time
		
		// start the connection manager
		
		// start the cleaner
		
		// start the core thread
		mainProcess = new CoreThread();
		mainProcess.start();
		
		// User input loop for server diagnosis and access/commands
		scan = new Scanner(System.in);
		while (true) {
			String input = scan.nextLine();
			
			if (input.equalsIgnoreCase("help")) {
				// display help menu (commands list)
			} else if (input.equalsIgnoreCase("exit")) {
				// TODO
			} else if (input.equalsIgnoreCase("list")) {
			 	// TODO
			} else if (input.equalsIgnoreCase("clear")) {
				// TODO
			} else if (input.equalsIgnoreCase("cleanup")) {
				// TODO
			} else {
				System.out.println("Sorry, " + input + "is not a listed command. Try 'help' for a list of commands");
			}
		}
		
	}
	
	// lists commands and how to use server
	public static void printHelpMenu() {
		System.out.println("============================== HELP ==============================");
		System.out.println("Commands:");
		System.out.println("    exit 	- Exits the server");
		System.out.println("    list 	- Lists all current connections and connection details");
		System.out.println("    clear 	- Forces all connections to close");
		System.out.println("    cleanup - Runs the connection cleaner");
		System.out.println("    help    - Displays this menu ");
	}
	
	
	public static void printError(String s) {
		if (verboseMode) {
			System.out.println(s);
		}
		
		// always save error to file
		FileSaver.saveFileAppend(serverStartTime.toString(), s);
	}
	
}
