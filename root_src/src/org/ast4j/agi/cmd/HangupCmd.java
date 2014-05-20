package org.ast4j.agi.cmd;

public class HangupCmd extends BaseAgiCmd
{
	/**
     * The name of the channel to hangup or <code>null</code> for the current
     * channel.
     */
    private final String channel;
    
    public HangupCmd()
	{
    	this(null);
	}
	
	public HangupCmd(String channel)
	{
		this.channel = channel;
	}

	public String getChannel()
	{
		return channel;
	}

	@Override
	public String buildCmd()
	{
		return "HANGUP" + (channel == null ? "" : " " + escapeAndQuote(channel));
	}
}
