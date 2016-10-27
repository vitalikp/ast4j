package org.ast4j.agi.cmd;

public class WaitForDigitCmd implements AgiCmd
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
