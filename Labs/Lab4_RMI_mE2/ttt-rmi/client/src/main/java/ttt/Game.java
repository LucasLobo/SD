package ttt;

import java.rmi.RemoteException;
import java.util.Scanner;

class Game {
    private TTTService ttt;
    private Scanner keyboardSc;
    private int winner = 0;
    private int player = 1;

    Game(TTTService tttService) {
        ttt = tttService;
        keyboardSc = new Scanner(System.in);
    }

    private int readPlay() {
        int play;
        do {
            System.out.printf(
                    "\nPlayer %d, please enter the number of the square "
                            + "where you want to place your %c (or 0 to refresh the board or 101 to check last plays): \n",
                    player, (player == 1) ? 'X' : 'O');
            play = keyboardSc.nextInt();
        } while ((play > 9 && play != 101) || play < 0);
        return play;
    }

    void playGame() throws RemoteException {
        int play;
        boolean playAccepted;

        do {
            player = ++player % 2;
            do {
                System.out.println(ttt.currentBoard());
                play = readPlay();
                if (play == 101) {
                	int[] lastPlays = ttt.ultimasJogadas();
                	System.out.println("\nLast plays:\n\tplayer 0: " + lastPlays[0] + "\n\tplayer 1: " + lastPlays[1]);
                	playAccepted = false;
                }
                else if (play != 0) {
                    playAccepted = ttt.play(--play / 3, play % 3, player);
                    if (!playAccepted)
                        System.out.println("Invalid play! Try again.");
                } else
                    playAccepted = false;
            } while (!playAccepted);
            winner = ttt.checkWinner();
        } while (winner == -1);
        System.out.println(ttt.currentBoard());
    }

    void congratulate() {
        if (winner == 2)
            System.out.print("\nHow boring, it is a draw\n");
        else
            System.out.printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", winner);
    }

}
