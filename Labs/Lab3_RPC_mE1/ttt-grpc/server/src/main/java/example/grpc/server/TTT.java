package example.grpc.server;

public class TTT {
	char board[][] = {
			{ '1', '2', '3' }, /* Initial values are reference numbers */
			{ '4', '5', '6' }, /* used to select a vacant square for */
			{ '7', '8', '9' } /* a turn. */
	};
	int nextPlayer = 0;
	int numPlays = 0;
	char player_0_sym = 'O';
	char player_1_sym = 'X';

	public String currentBoard() {
		String s = "\n\n " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " " + "\n---+---+---\n "
				+ board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " " + "\n---+---+---\n " + board[2][0]
				+ " | " + board[2][1] + " | " + board[2][2] + " \n";
		return s;
	}

	public boolean play(int row, int column, int player) {
		if (!(row >= 0 && row < 3 && column >= 0 && column < 3))
			return false;
		if (board[row][column] > '9')
			return false;
		if (player != nextPlayer)
			return false;

		if (numPlays == 9)
			return false;

		board[row][column] = (player == 1) ? player_1_sym
				: player_0_sym; /* Insert player symbol */
		nextPlayer = (nextPlayer + 1) % 2;
		numPlays++;
		return true;
	}

	public int checkWinner() {
		int i;
		/* Check for a winning line - diagonals first */
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2])
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0])) {
			if (board[1][1] == player_1_sym)
				return 1;
			else
				return 0;
		} else
			/* Check rows and columns for a winning line */
			for (i = 0; i <= 2; i++) {
				if ((board[i][0] == board[i][1] && board[i][0] == board[i][2])) {
					if (board[i][0] == player_1_sym)
						return 1;
					else
						return 0;
				}

				if ((board[0][i] == board[1][i] && board[0][i] == board[2][i])) {
					if (board[0][i] == player_1_sym)
						return 1;
					else
						return 0;
				}
			}
		if (numPlays == 9)
			return 2; /* A draw! */
		else
			return -1; /* Game is not over yet */
	}

	public void outroSimbolo(int player){
		if(player == 0) {
			player_0_sym = 'o';
		}
			
		else {
			player_1_sym = 'x';
		}
			

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (player == 0 && board[i][j] == 'O') {
					board[i][j] = 'o';
				}
				else if (player == 1 && board[i][j] == 'X') {
					board[i][j] = 'x';
				}
			}
		}
	}

}
