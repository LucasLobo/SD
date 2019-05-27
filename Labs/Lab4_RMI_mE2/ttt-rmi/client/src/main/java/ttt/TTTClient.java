package ttt;

import java.rmi.Naming;
import java.rmi.RemoteException;

/** This is the client of the Tic Tac Toe game. */
public class TTTClient {


	/** The program starts running in the main method. */
	public static void main(String[] args) throws Exception {
		System.out.println("Main OK");
		try {
			TTTService ttt = (TTTService) Naming.lookup("//localhost:55005/TTT");
			System.out.println("After connect");
			Game game = new Game(ttt);
			System.out.println("After create");
			game.playGame();
			game.congratulate();

		} catch (RemoteException e) {
			System.out.println("TTT: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Lookup: " + e.getMessage());
		}
	}

}
