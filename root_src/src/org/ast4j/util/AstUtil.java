package org.ast4j.util;

public final class AstUtil
{
	private AstUtil()
	{
		
	}
	
	/**
     * Parses a string for caller id information.
     * <p>
     * The caller id string should be in the form <code>"Some Name" &lt;1234&gt;</code>.
     * <p>
     * This resembles <code>ast_callerid_parse</code> in
     * <code>callerid.c</code> but strips any whitespace.
     * 
     * @param s the string to parse
     * @return a String[] with name (index 0) and number (index 1)
     */
    public static String[] parseCallerId(String s)
    {
        final String[] result = new String[2];
        final int lbPosition;
        final int rbPosition;
        String name;
        String number;

        if (s == null)
        {
            return result;
        }

        lbPosition = s.lastIndexOf('<');
        rbPosition = s.lastIndexOf('>');

        // no opening and closing brace? use value as CallerId name
        if (lbPosition < 0 || rbPosition < 0)
        {
            name = s.trim();
            if (name.length() == 0)
            {
                name = null;
            }
            result[0] = name;
            return result;
        }
        else
        {
            number = s.substring(lbPosition + 1, rbPosition).trim();
            if (number.length() == 0)
            {
                number = null;
            }
        }

        name = s.substring(0, lbPosition).trim();
        if (name.startsWith("\"") && name.endsWith("\"") && name.length() > 1)
        {
            name = name.substring(1, name.length() - 1).trim();
        }
        if (name.length() == 0)
        {
            name = null;
        }

        result[0] = name;
        result[1] = number;
        return result;
    }
}
