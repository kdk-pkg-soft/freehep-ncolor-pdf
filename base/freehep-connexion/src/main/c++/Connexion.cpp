#include "Connexion.h"

ConnexionCallback* Connexion::callback;

void Connexion::run() {
	/* OSErr err = */InstallConnexionHandlers (HandleMessage, AddedDevice, RemovedDevice);
	UInt32 signature = kConnexionClientWildcard;
	UInt8 *name = (UInt8*)"";
	UInt16 mode = kConnexionClientModeTakeOver; /* kConnexionClientModePlugin; */
	UInt32 mask = kConnexionMaskAll;
	UInt16 myID = RegisterConnexionClient(signature, name, mode, mask);
	CFRunLoopRun();
	UnregisterConnexionClient(myID);
	CleanupConnexionHandlers();
}
