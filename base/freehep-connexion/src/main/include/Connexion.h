#ifndef CONNEXION_H
#define CONNEXION_H 1

#include "ConnexionDriver.h"
#include "ConnexionCallback.h"

class Connexion {

private:
	static ConnexionCallback* callback;

public:
	inline Connexion() {;}
	inline ~Connexion() {;}

	inline void setConnexionCallback(ConnexionCallback* connexionCallback) {
		callback = connexionCallback;
	}
	
	inline static ConnexionCallback* getCallback() {
		return callback;
	}

	void run();
};

#endif
