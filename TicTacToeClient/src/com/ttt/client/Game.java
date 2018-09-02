package com.ttt.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.ttt.server.ServerIF;
import com.ttt.server.TTTServiceIF;

public class Game {

	TTTServiceIF game;
	Scanner sc;
	int winner = 0;
	int player = 1;
	static ServerIF playerCount;

	public Game(int gameNum) throws MalformedURLException, RemoteException, NotBoundException {
		game = (TTTServiceIF) Naming.lookup("TTT" + gameNum);
		sc = new Scanner(System.in);
	}

	public int readPlay() {
		int play;
		do {
			System.out.printf(
					"\nPlayer %d, enter the number of the square to place %c (0 to refresh the board) \n",
					player+1, (player == 0) ? 'X' : 'O');
			play = sc.nextInt();
		} while (play > 9 || play < 0);
		return play;
	}

	public  void playGame() throws RemoteException {
		int play;
		boolean playAccepted;

		do {
			player = ++player % 2;
			do {
				System.out.println(game.currentBoard());
				play = readPlay();
				if (play != 0) {
					playAccepted = game.play(--play / 3, play % 3, player);
					if (!playAccepted)
						System.out.println("Invalid move! Try again...");
				} else
					playAccepted = false;
			} while (!playAccepted);
			winner = game.checkWinner();
		} while (winner == -1);
		System.out.println(game.currentBoard());
	}

	public void congratulate() {
		if (winner == 2)
			System.out.printf("\nIts a DRAW, no one loses...\n");
		else
			System.out.printf("\nPlayer %d is the WINNER..!!!\n", winner+1);
	}

	/** The program starts running in the main method. */
	public static void main(String[] args) {
		/* TO DO */
		try {
			playerCount = (ServerIF) Naming.lookup("Count");
			playerCount.setNumberOfPlayers();
			int count = playerCount.getNumberOfPlayers();
			int gameNum = count/2;
			playerCount.updateGameNumber(gameNum);
			Game g = new Game(gameNum);
			g.playGame();
			g.congratulate();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
