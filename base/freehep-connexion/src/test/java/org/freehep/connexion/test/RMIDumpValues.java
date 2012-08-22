package org.freehep.connexion.test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import org.freehep.connexion.rmi.ConnexionCallback;

public class RMIDumpValues extends UnicastRemoteObject implements ConnexionCallback {

	private static Registry registry;
	
	public RMIDumpValues() throws RemoteException {
	}

	public void addDevice(int device) throws RemoteException {
		System.err.println("RMI Connexion added device: " + device);
	}

	public void removeDevice(int device) throws RemoteException {
		System.err.println("RMI Connexion removed device: " + device);
	}

	public void handleAxis(int device, int x, int y, int z, int rx, int ry,
			int rz) throws RemoteException {
		System.err.println("RMI Connexion handle axis for device: " + device
				+ " (" + x + ", " + y + ", " + z + ") (" + rx + ", " + ry
				+ ", " + rz + ")");
	}

	public void handleButtons(int device, int value, int buttons)
			throws RemoteException {
		System.err.println("RMI Connexion handle buttons for device: " + device
				+ " Value: " + value + " Buttons: " + buttons);
	}

	public static void main(String args[]) throws Exception {
		ConnexionCallback server = new RMIDumpValues();
		try {
			// create a registry
			registry = LocateRegistry.createRegistry(1099);
		} catch (ExportException e) {
			// use default registry
			registry = LocateRegistry.getRegistry();
		}
		registry.rebind("Connexion", server);
		System.err.println("RMIDumpValues ready");
	}
}
