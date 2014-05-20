package org.ast4j.agi.cmd;

public class AnswerCmd implements IAgiCmd
{
	@Override
	public String buildCmd()
	{
		return "ANSWER";
	}
}
