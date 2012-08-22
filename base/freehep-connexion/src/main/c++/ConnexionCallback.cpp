
#include <iostream>

#include "ConnexionCallback.h"

using namespace std;

void ConnexionCallback::addDevice(int device) {
	cerr << "Connexion added device: " << device << endl;
}

void ConnexionCallback::removeDevice(int device) {
	cerr << "Connexion removed device: " << device << endl;
}
void ConnexionCallback::handleAxis(int device, int x, int y, int z, int rx, int ry, int rz) {
	cerr << "Connexion handle axis for device: " << device << " (" << x << ", " << y << ", " << z << ") (" << rx << ", " << ry << ", " << rz << ")" << endl;	
}
void ConnexionCallback::handleButtons(int device, int value, int buttons) {
	cerr << "Connexion handle buttons for device: " << device << " Value: " << value << " Buttons: " << buttons << endl;
}
