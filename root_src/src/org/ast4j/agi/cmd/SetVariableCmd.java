package org.ast4j.agi.cmd;

public class SetVariableCmd extends BaseAgiCmd
{
	/**
     * The name of the variable to set.
     */
    private String variable;

    /**
     * The value to set.
     */
    private String value;
	
	public SetVariableCmd(String variable, String value)
	{
		this.variable = variable;
		this.value = value;
	}

	public String getVariable()
	{
		return variable;
	}

	public void setVariable(String variable)
	{
		this.variable = variable;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String buildCmd()
	{
		return "SET VARIABLE " + escapeAndQuote(variable) + " " + escapeAndQuote(value);
	}
}
