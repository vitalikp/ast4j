package org.ast4j.agi.cmd;

public class ChannelStatusCmd extends BaseAgiCmd
{
	/**
     * The name of the channel to query or <code>null</code> for the current
     * channel.
     */
    private final String channel;
	
    public ChannelStatusCmd()
	{
    	this(null);
	}
    
    public ChannelStatusCmd(String channel)
	{
		this.channel = channel;
	}

	public String getChannel()
	{
		return channel;
	}

	@Override
	public String getName()
	{
		return "CHANNEL STATUS" + (channel == null ? "" : " " + escapeAndQuote(channel));
	}
}
