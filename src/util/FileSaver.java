package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 */

/**
 * @author Aify
 *
 */
public class FileSaver {
	
	/**
	 * This function saves a string to a file, overwriting all its internal contents
	 * 
	 * @param filename which file we want to save it to
	 * @param contents contents of the file
	 * @return whether or not we successfully saved the file
	 */
	public static boolean saveFileOverwrite(String filename, String contents) {
		try {
			File f = new File(filename);		
		
			FileWriter writer = new FileWriter(f);
			
			writer.write(contents);
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * This function saves a string to a file, appending to the end of what's in the file
	 * 
	 * @param filename which file we want to save it to
	 * @param contents contents of the file
	 * @return whether or not we successfully saved the file
	 */
	public static boolean saveFileAppend(String filename, String contents) {
		try {	
		
			File f = new File(filename);		
			
			FileWriter writer = new FileWriter(f, true);
			
			writer.write(contents);
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static String readFile(String filename) {
		
		String data = "";
		try {
			File f = new File(filename);
			
			if (!f.exists()) {
				return "";
			} 
			
			FileReader reader = new FileReader(f);
			
			while (reader.ready()) {
				data += (char) reader.read();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
}
