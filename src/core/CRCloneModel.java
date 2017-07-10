package core;

public class CRCloneModel {
	
	private class Point {
		public final int x;
		public final int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private final int FIELD_WIDTH = 700;
	private final int FIELD_HEIGHT = 700;
	
	public static int MAX_PLAYERS = 2; //dunno why I const'ed this, the algorithms won't work for any other player count
	
	private ClientThread[] players;
	private int nextPlayer = 1;
	private volatile boolean started = false;
	
	public CRCloneModel() {
		players = new ClientThread[MAX_PLAYERS+1]; //we will be using 1-4
	}
	
	public void registerPlayer(ClientThread ct)	{	
		if(nextPlayer > MAX_PLAYERS) {
			//TODO: throw an exception or print an error
			return;
		}
		else {
			players[nextPlayer] = ct;
			nextPlayer++;
			if(nextPlayer > MAX_PLAYERS) {
				//we have all the players, so we can start
				started = true;
			}
		}
	}
	
	public void propagateSpawnMessage(String message, String senderIP, int portnum) {
		//decode message
		String[] msgPieces = message.split(",");
		String cardName = msgPieces[0];
		int x = Integer.parseInt(msgPieces[1]);
		int y = Integer.parseInt(msgPieces[2]);
		
		//figure out who sent it
		int originatingPlayer = 0;
		for(int p = 1; p <= MAX_PLAYERS; p++) {
			if(players[p].connection.getInetAddress().toString().equals(senderIP) && players[p].connection.getPort() == portnum) {
				originatingPlayer = p;
				break;
			}				
		}
		
		//transform coordinates to P1 basis
		Point location = transformPointToP1(new Point(x,y), originatingPlayer);
		
		//transform and propagate to *all other* players
		for(int p = 1; p <= MAX_PLAYERS; p++) {
			if(p != originatingPlayer) {
				Point locationPSpace = transformPointToPx(location, p);
				String messageOut = "4{" + cardName + "," + locationPSpace.x + "," + locationPSpace.y + "," + originatingPlayer;
				players[p].outStream.println(messageOut);
			}
		}
		
	}
	
	public boolean isStarted() {
		return started;
	}
	
	private Point transformPointToP1(Point original, int player) {
		switch(player) {
		case 2:
			return new Point(original.y,FIELD_HEIGHT-original.x);
		case 3:
			return new Point(FIELD_WIDTH - original.x, FIELD_HEIGHT - original.y);
		case 4:
			return new Point(FIELD_WIDTH-original.y, original.x);
		default:
			return original;
		}
	}
	
	private Point transformPointToPx(Point original, int targetPlayer) {
		switch(targetPlayer) {
		case 2:
			return new Point(FIELD_WIDTH-original.y, original.x);
		case 3:
			return new Point(FIELD_WIDTH - original.x, FIELD_HEIGHT - original.y);
		case 4:
			return new Point(original.y,FIELD_HEIGHT-original.x);
		default:
			return original;
		}
	}

	public int getPlayerNumber(String senderIP)
	{
		int originatingPlayer = 0;
		for(int p = 1; p <= MAX_PLAYERS; p++) {
			if(players[p].connection.getInetAddress().toString().equals(senderIP)) {
				originatingPlayer = p;
				break;
			}				
		}
		return originatingPlayer;
	}

}
