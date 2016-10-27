package org.ast4j.agi;

import java.io.IOException;

public interface AgiScript
{
	/**
	 * The run method is called by the AsteriskServer whenever this
	 * AgiScript should handle an incoming AgiRequest.
	 *
	 * @param request the initial data received from Asterisk when requesting
	 *            this script.
	 * @param channel a handle to communicate with Asterisk such as sending
	 *            commands to the channel sending the request.
	 *
	 * @throws IOException any exception thrown by your script will be logged.
	 */
	void run(AgiChannel channel) throws IOException;
}
