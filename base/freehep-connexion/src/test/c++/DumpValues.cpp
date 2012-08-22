
#include <stdio.h>

#include "Connexion.h"

int main(int argc, char *argv[])
{
	Connexion* connexion = new Connexion();
	ConnexionCallback* callback = new ConnexionCallback();
	connexion->setConnexionCallback(callback);
	connexion->run();
	return 0;
} 
