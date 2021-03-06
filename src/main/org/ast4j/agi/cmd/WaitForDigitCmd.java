package org.ast4j.agi.cmd;

import org.ast4j.cmd.Cmd;

public class WaitForDigitCmd implements Cmd
{
	/**
     * The milliseconds to wait for the channel to receive a DTMF digit.
     */
    private long timeout;
	
	public WaitForDigitCmd(long timeout)
	{
		this.timeout = timeout;
	}

	@Override
	public String getName()
	{
		return "WAIT FOR DIGIT " + timeout;
	}
}
