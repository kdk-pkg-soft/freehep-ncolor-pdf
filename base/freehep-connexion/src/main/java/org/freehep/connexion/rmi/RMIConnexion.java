package org.freehep.connexion.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.freehep.connexion.jni.Connexion;
import org.freehep.connexion.jni.ConnexionCallback;

public class RMIConnexion extends Connexion {

	static class RMICallback extends ConnexionCallback {
		private Registry registry;
		private org.freehep.connexion.rmi.ConnexionCallback server;

		public void addDevice(int device) {
			try {
				getServer().addDevice(device);
			} catch (Exception e) {
				// ignore
				System.err.println(e);
				registry = null;
			}
		}

		public void removeDevice(int device) {
			try {
				getServer().removeDevice(device);
			} catch (Exception e) {
				// ignore
				System.err.println(e);
				registry = null;
			}
		}

		public void handleAxis(int device, int x, int y, int z, int rx,
				int ry, int rz) {
			try {
				getServer().handleAxis(device, x, y, z, rx, ry, rz);
			} catch (Exception e)	{
				// ignore
				System.err.println(e);
				registry = null;
			}
		}

		public void handleButtons(int device, int value, int buttons) {
			try {
				getServer().handleButtons(device, value, buttons);
			} catch (Exception e) {
				// ignore
				System.err.println(e);
				registry = null;
			}
		}
		
		private org.freehep.connexion.rmi.ConnexionCallback getServer() throws RemoteException, NotBoundException {
			if (registry == null) {
				registry = LocateRegistry.getRegistry();
				server = null;
			}
			if (server == null) {
				server = (org.freehep.connexion.rmi.ConnexionCallback) registry.lookup("Connexion");
			}
			return server;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connexion connexion = new RMIConnexion();
		connexion.setConnexionCallback(new RMICallback());
		connexion.run();
	}
}
