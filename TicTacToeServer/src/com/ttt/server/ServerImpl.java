package com.ttt.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements ServerIF {

	protected ServerImpl() throws RemoteException {
		super();
	}

	private static int numOfPlayers = 0;

	public static void main(String args[]) {

		Registry reg;
		try {
			ServerImpl playerCounter = new ServerImpl();
			reg = LocateRegistry.createRegistry(1099);
			reg.rebind("Count", playerCounter);
			System.err.println("Server is up");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getNumberOfPlayers() throws RemoteException {
		return numOfPlayers;
	}
	
	@Override
	public void setNumberOfPlayers() throws RemoteException {
		numOfPlayers = numOfPlayers+1;
	}

	@Override
	public void updateGameNumber(int gameNum) throws RemoteException{
		TTTServiceImpl ttt = new TTTServiceImpl();
		Registry reg = LocateRegistry.getRegistry(1099);
		reg.rebind("TTT"+gameNum, ttt);
		System.err.println("Game is initialized...");
	}

}
