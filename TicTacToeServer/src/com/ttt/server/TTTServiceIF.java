package com.ttt.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TTTServiceIF extends Remote{

	String currentBoard() throws RemoteException;
	
	boolean play(int row, int column, int player) throws RemoteException;
	
	int checkWinner() throws RemoteException;
}