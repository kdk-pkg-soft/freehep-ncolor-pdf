package org.freehep.connexion.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnexionCallback extends Remote {

	public void addDevice(int device) throws RemoteException;

	public void removeDevice(int device) throws RemoteException;

	public void handleAxis(int device, int x, int y, int z, int rx, int ry,
			int rz) throws RemoteException;

	public void handleButtons(int device, int value, int buttons)
			throws RemoteException;
}
