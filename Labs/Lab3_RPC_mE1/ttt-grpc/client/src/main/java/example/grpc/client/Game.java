package example.grpc.client;

import example.grpc.TicTacToe;
import example.grpc.TicTacToeServiceGrpc;
import java.util.Scanner;

public class Game {
	TicTacToeServiceGrpc.TicTacToeServiceBlockingStub stub;
	Scanner keyboardSc;
	int winner = 0;
	int player = 1;

	public Game(TicTacToeServiceGrpc.TicTacToeServiceBlockingStub newStub) {
		stub = newStub;
		keyboardSc = new Scanner(System.in);
	}

	public int readPlay() {
		int play;
		do {
			System.out.printf(
					"\nPlayer %d, please enter the number of the square "
							+ "where you want to place your %c (or 0 to refresh the board): \n",
					player, (player == 1) ? 'X' : 'O');
			play = keyboardSc.nextInt();
		} while (play > 9 || play < 0);
		return play;
	}

	public void playGame() {
		int play;
		boolean playAccepted;

		do {
			player = ++player % 2;
			do {
				System.out.println(stub.currentBoard(TicTacToe.CurrentBoardRequest.getDefaultInstance()).getBoard());
				play = readPlay();
				if (play != 0) {
                    TicTacToe.PlayRequest request = TicTacToe.PlayRequest.newBuilder().setRow(--play / 3).setColumn(play % 3).setPlayer(player).build();
					playAccepted = stub.play(request).getAccepted();
					if (!playAccepted)
						System.out.println("Invalid play! Try again.");
				} else
					playAccepted = false;
			} while (!playAccepted);
			winner = stub.checkWinner(TicTacToe.CheckWinnerRequest.getDefaultInstance()).getPlayer();
		} while (winner == -1);
	}

	public void congratulate() {
		System.out.println(stub.currentBoard(TicTacToe.CurrentBoardRequest.getDefaultInstance()).getBoard());
		if (winner == 2)
			System.out.printf("\nHow boring, it is a draw\n");
		else
			System.out.printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", winner);
	}
}
