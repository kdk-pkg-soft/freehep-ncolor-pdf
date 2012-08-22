#ifndef CONNEXION_DRIVER_H
#define CONNEXION_DRIVER_H 1

#include <CoreFoundation/CoreFoundation.h>

#include "3DconnexionClient/ConnexionClientAPI.h"

extern "C" {
	void AddedDevice(io_connect_t connection);
	void RemovedDevice(io_connect_t connection);
	void HandleMessage(io_connect_t connection, natural_t messageType, void *messageArgument);
}

#endif