package org.ast4j.agi.cmd;

public class VerboseCmd extends BaseAgiCmd
{
	/**
     * The message to send.
     */
    private String message;

    /**
     * The verbosity level to use.<p>
     * Must be in [1..4]
     */
    private int level;
	
	public VerboseCmd(String message, int level)
	{
		this.message = message;
		this.level = level;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	@Override
	public String getName()
	{
		return "VERBOSE " + escapeAndQuote(message) + " " + level;
	}
}
