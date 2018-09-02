package com.ttt.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TTTServiceImpl extends UnicastRemoteObject implements TTTServiceIF {

	/** The Game Board */
	private char board[][] = {
			{ '1', '2', '3' }, /* Initial values are reference numbers */
			{ '4', '5', '6' }, /* used to select a vacant square for */
			{ '7', '8', '9' } /* a turn. */
	};
	private int nextPlayer = 0;
	private int numPlays = 0;

	public TTTServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public String currentBoard() throws RemoteException {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n ");

		synchronized (this) {
			sb.append(board[0][0]).append(" | ");
			sb.append(board[0][1]).append(" | ");
			sb.append(board[0][2]).append(" ");
			sb.append("\n---+---+---\n ");
			sb.append(board[1][0]).append(" | ");
			sb.append(board[1][1]).append(" | ");
			sb.append(board[1][2]).append(" ");
			sb.append("\n---+---+---\n ");
			sb.append(board[2][0]).append(" | ");
			sb.append(board[2][1]).append(" | ");
			sb.append(board[2][2]).append(" \n");
		}
		return sb.toString();
	}

	@Override
	public boolean play(int row, int column, int player) throws RemoteException {
		if (!(row >= 0 && row < 3 && column >= 0 && column < 3))
			return false;

		synchronized (this) {
			// invalid square
			if (board[row][column] > '9')
				return false;
			// not player's turn
			if (player != nextPlayer)
				return false;
			// no more moves left
			if (numPlays == 9)
				return false;

			/* insert player symbol */
			board[row][column] = (player == 1) ? 'O' : 'X';
			nextPlayer = (nextPlayer + 1) % 2;
			numPlays++;
			return true;
		}
		// unlock on return

	}

	@Override
	public synchronized int checkWinner() throws RemoteException {
		int i;
		// Check diagonal
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2])
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0])) {
			if (board[1][1] == 'O')
				return 1;
			else
				return 0;
		} else {
			// Check rows and columns
			for (i = 0; i <= 2; i++) {
				if ((board[i][0] == board[i][1] && board[i][0] == board[i][2])) {
					if (board[i][0] == 'O')
						return 1;
					else
						return 0;
				}

				if ((board[0][i] == board[1][i] && board[0][i] == board[2][i])) {
					if (board[0][i] == 'O')
						return 1;
					else
						return 0;
				}
			}
		}
		if (numPlays == 9)
			// Draw
			return 2;
		else
			// Continue game
			return -1;
	}

}
