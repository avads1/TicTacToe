package com.ttt.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIF extends Remote{
	
	int getNumberOfPlayers() throws RemoteException;

	void setNumberOfPlayers() throws RemoteException;

	void updateGameNumber(int gameNum) throws RemoteException;
}
