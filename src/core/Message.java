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
	public String senderIP;
	
	public Message(String m, String originatingIP) {
		message = m;
		senderIP = originatingIP;
	}
	
	// this is called when the message is read so that we can parse and deal with 
	// whatever the server needs to do. 
	public void execute() {
		// parse message
		
		String[] components = message.split("\\{");
		
		int audience = Integer.parseInt(components[0]);
		
		Main.printFromCore("Message From IP: " + senderIP);
		
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
			case 3: // sync message
				for (ClientThread t : Main.connManager.cthreads) {
					if (t.connection.getInetAddress().toString().equals(senderIP)) {
						t.outStream.println("3{SYNC");
					}
				}
				break;
			case 4: //CRClone specific rotate and propagate spawn to other players
				//message will look like 4{MagicBox,1,2
				Main.gameModel.propagateSpawnMessage(components[1], senderIP);
				Main.printFromCore("Message type 4 Handled - rotation propogation");
				break;
			case 5: //CRClone specific ask if server is started
				for (ClientThread t : Main.connManager.cthreads) {
					if (t.connection.getInetAddress().toString().equals(senderIP)) {
						String outMsg = "5{" + (Main.gameModel.isStarted() ? "true" : "false");						
						t.outStream.println(outMsg);
						t.outStream.flush();
						break;
					}
				}
				Main.printFromCore("Message type 5 Handled: Client Pinging for isStarted = " + Main.gameModel.isStarted());
				break;
			default:
				break;
		}
	}
}
