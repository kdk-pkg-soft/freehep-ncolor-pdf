package org.freehep.connexion.test;

import org.freehep.connexion.jni.Connexion;
import org.freehep.connexion.jni.ConnexionCallback;

public class DumpValues {

	static class DumpCallback extends ConnexionCallback {
		public void addDevice(int device) {
			System.err.println("Java Connexion added device: "+device);
		}
		public void removeDevice(int device) {
			System.err.println("Java Connexion removed device: "+device);
		}
		public void handleAxis(int device, int x, int y, int z, int rx, int ry,
				int rz) {
			System.err.println("Java Connexion handle axis for device: "
					+ device + " (" + x + ", " + y + ", " + z + ") (" + rx
					+ ", " + ry + ", " + rz + ")");
		}		
		public void handleButtons(int device, int value, int buttons) {
			System.err.println("Java Connexion handle buttons for device: "+device+" Value: "+value+" Buttons: "+buttons);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connexion connexion = new Connexion();
		ConnexionCallback callback = new DumpCallback();
		connexion.setConnexionCallback(callback);
		connexion.run();
	}
}
