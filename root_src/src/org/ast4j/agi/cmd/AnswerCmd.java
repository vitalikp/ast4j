package org.ast4j.agi.cmd;

public class AnswerCmd implements AgiCmd
{
	@Override
	public String buildCmd()
	{
		return "ANSWER";
	}
}
