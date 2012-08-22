#ifndef CONNEXION_CALLBACK_H
#define CONNEXION_CALLBACK_H 1

class ConnexionCallback {
	
public:
	inline ConnexionCallback() {;}
	inline virtual ~ConnexionCallback() {;}
	virtual void addDevice(int device);
	virtual void removeDevice(int device);
	virtual void handleAxis(int device, int x, int y, int z, int rx, int ry, int rz);
	virtual void handleButtons(int device, int value, int buttons);
};

#endif
