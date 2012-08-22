
#include <stdio.h>

#include "Connexion.h"

#include "ConnexionDriver.h"

void AddedDevice(io_connect_t connection) {
	ConnexionCallback* cb = Connexion::getCallback();
	if (cb != NULL) cb->addDevice(connection);
}

void RemovedDevice(io_connect_t connection) {
	ConnexionCallback* cb = Connexion::getCallback();
	if (cb != NULL) cb->removeDevice(connection);
}

void HandleMessage(io_connect_t connection, natural_t messageType,
		void *messageArgument) {
	ConnexionCallback* cb = Connexion::getCallback();
	if (cb == NULL) return;
	
	ConnexionDeviceStatePtr msg = (ConnexionDeviceStatePtr)messageArgument;

	switch (messageType) {
	case kConnexionMsgDeviceState:
		/* Device state messages are broadcast to all clients.  It is up to
		 * the client to figure out if the message is meant for them. This
		 * is done by comparing the "client" id sent in the message to our
		 * assigned id when the connection to the driver was established.
		 *
		 */
		switch (msg->command) {
		case kConnexionCmdHandleAxis:
			// msg->axis[0] .. [5] contain X, Y, Z, Rx, Ry, Rz data                           
			cb->handleAxis(connection, msg->axis[0], msg->axis[1],
					msg->axis[2], msg->axis[3], msg->axis[4], msg->axis[5]);
			break;

		case kConnexionCmdHandleButtons:
			// msg->value is the button state
			// msg->buttons is the button ID
			cb->handleButtons(connection, msg->value, msg->buttons);
			break;
		}
		break;

	default:
		// other messageTypes can happen and should be ignored
		break;
	}
	return;
}

