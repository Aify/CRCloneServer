/**
 * 
 */
package core;

/**
 * @author Aify
 *
 */
public class Message {

	public String message;
	
	public Message(String m) {
		message = m;
	}
	
	// this is called when the message is read so that we can parse and deal with 
	// whatever the server needs to do. 
	public void execute() {
		// parse message
		String[] components = message.split("{");
		
		int audience = Integer.parseInt(components[1]);
		
		switch (audience) {
			case 0:
				// system message
				String msg = components[1];
				break;
			case 1:
				// message to everyone
				for (ClientThread t : Main.connManager.cthreads) {
					t.outStream.println(components[1]);
				}
				break;
			case 2:
				// message to specific target
				String targetIP = components[2];
				// send message TODO
				break;
			default:
				break;
		}
	}
}
