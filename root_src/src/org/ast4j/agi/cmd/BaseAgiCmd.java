package org.ast4j.agi.cmd;

import org.ast4j.cmd.Cmd;

public abstract class BaseAgiCmd implements Cmd
{
	/**
     * Escapes and quotes a given String according to the rules set by
     * Asterisk's AGI.
     * 
     * @param s the String to escape and quote
     * @return the transformed String
     */
    protected String escapeAndQuote(String s)
    {
        String tmp;

        if (s == null)
        {
            return "\"\"";
        }

        tmp = s;
        tmp = tmp.replaceAll("\\\"", "\\\\\""); // escape quotes
        tmp = tmp.replaceAll("\\\n", ""); // filter newline
        return "\"" + tmp + "\""; // add quotes
    }
}
