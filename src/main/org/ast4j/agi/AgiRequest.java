package org.ast4j.agi;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ast4j.util.AstUtil;

class AgiRequest implements IAgiRequest
{
	private final static Logger log = LogManager.getLogger(AgiRequest.class);
	
	private static final Pattern SCRIPT_PATTERN		= Pattern.compile("^([^\\?]*)\\?(.*)$");
    private static final Pattern PARAMETER_PATTERN	= Pattern.compile("^(.*)=(.*)$");
    
    private String rawCallerId;
	
	private Map<String, String> request;
	
	/**
     * A map assigning the values of a parameter (an array of Strings) to the
     * name of the parameter.
     */
    private Map<String, String[]> parameterMap;
	
	private String parameters;
	private String script;
	private InetAddress localAddress;
    private int localPort;
    private InetAddress remoteAddress;
    private int remotePort;
	
	public AgiRequest(List<String> environment)
	{
		if (environment == null)
        {
            throw new IllegalArgumentException("Environment must not be null.");
        }
		request = buildMap(environment);
		
		script = (String) request.get("network_script");
        if (script != null)
        {
            Matcher scriptMatcher = SCRIPT_PATTERN.matcher(script);
            if (scriptMatcher.matches())
            {
                script = scriptMatcher.group(1);
                parameters = scriptMatcher.group(2);
            }
        }
	}
	
	/**
     * Builds a map containing variable names as key (with the "agi_" prefix
     * stripped) and the corresponding values.<p>
     * Syntactically invalid and empty variables are skipped.
     * 
     * @param lines the environment to transform.
     * @return a map with the variables set corresponding to the given
     *         environment.
     */
    private Map<String, String> buildMap(final Collection<String> lines)
    {
        Map<String, String> map;

        map = new HashMap<String, String>();

        for (String line : lines)
        {
            int colonPosition;
            String key;
            String value;

            colonPosition = line.indexOf(':');

            // no colon on the line?
            if (colonPosition < 0)
            {
                continue;
            }

            // key doesn't start with agi_?
            if (!line.startsWith("agi_"))
            {
                continue;
            }

            // first colon in line is last character -> no value present?
            if (line.length() < colonPosition + 2)
            {
                continue;
            }

            key = line.substring(4, colonPosition).toLowerCase(Locale.ENGLISH);
            value = line.substring(colonPosition + 2);

            if (value.length() != 0)
            {
                map.put(key, value);
            }
        }

        return map;
    }

	@Override
	public String getScript()
	{
		return script;
	}

	@Override
	public String getRequestURL()
	{
		return request.get("request");
	}

	@Override
	public String getChannel()
	{
		return request.get("channel");
	}

	@Override
	public String getUniqueId()
	{
		return request.get("uniqueid");
	}
	
	@Override
	public String getCallerIdNumber()
    {
        String callerIdName;
        String callerId;

        callerIdName = request.get("calleridname");
        callerId = request.get("callerid");
        if (callerIdName != null)
        {
            // Asterisk 1.2
            if (callerId == null || "unknown".equals(callerId))
            {
                return null;
            }

            return callerId;
        }
        else
        {
            // Asterisk 1.0
            return getCallerId10();
        }
    }
	
	private boolean callerIdCreated;
	
	/**
     * Returns the Caller*ID number using Asterisk 1.0 logic.
     * 
     * @return the Caller*ID number
     */
    private String getCallerId10()
    {
        final String[] parsedCallerId;

        if (!callerIdCreated)
        {
            rawCallerId = (String) request.get("callerid");
            callerIdCreated = true;
        }

        parsedCallerId = AstUtil.parseCallerId(rawCallerId);
        if (parsedCallerId[1] == null)
        {
            return parsedCallerId[0];
        }
        else
        {
            return parsedCallerId[1];
        }
    }
    
    @Override
    public String getDnid()
    {
        String dnid;
        
        dnid = (String) request.get("dnid");
        
        if (dnid == null || "unknown".equals(dnid))
        {
            return null;
        }
        
        return dnid; 
    }

	@Override
	public String getContext()
	{
		return request.get("context");
	}
	
	@Override
	public String getParameter(String name)
    {
        String[] values;

        values = getParameterValues(name);

        if (values == null || values.length == 0)
        {
            return null;
        }

        return values[0];
    }

	@Override
    public String[] getParameterValues(String name)
    {
        if (getParameterMap().isEmpty())
        {
            return null;
        }

        return parameterMap.get(name);
    }

	@Override
    public Map<String, String[]> getParameterMap()
    {
        if (parameterMap == null)
        {
            parameterMap = parseParameters(parameters);
        }
        return parameterMap;
    }
	
	/**
     * Parses the given parameter string and caches the result.
     * 
     * @param s the parameter string to parse
     * @return a Map made up of parameter names their values
     */
    private Map<String, String[]> parseParameters(String s)
    {
        Map<String, List<String>> parameterMap;
        Map<String, String[]> result;
        StringTokenizer st;

        parameterMap = new HashMap<String, List<String>>();
        result = new HashMap<String, String[]>();

        if (s == null)
        {
            return result;
        }

        st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens())
        {
            String parameter;
            Matcher parameterMatcher;
            String name;
            String value;
            List<String> values;

            parameter = st.nextToken();
            parameterMatcher = PARAMETER_PATTERN.matcher(parameter);
            if (parameterMatcher.matches())
            {
                try
                {
                    name = URLDecoder
                            .decode(parameterMatcher.group(1), "UTF-8");
                    value = URLDecoder.decode(parameterMatcher.group(2),
                            "UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    log.error("Unable to decode parameter '" + parameter + "'", e);
                    continue;
                }
            }
            else
            {
                try
                {
                    name = URLDecoder.decode(parameter, "UTF-8");
                    value = "";
                }
                catch (UnsupportedEncodingException e)
                {
                    log.error("Unable to decode parameter '" + parameter + "'", e);
                    continue;
                }
            }

            if (parameterMap.get(name) == null)
            {
                values = new ArrayList<String>();
                values.add(value);
                parameterMap.put(name, values);
            }
            else
            {
                values = parameterMap.get(name);
                values.add(value);
            }
        }

        for (String name : parameterMap.keySet())
        {
            List<String> values;
            String[] valueArray;

            values = parameterMap.get(name);
            valueArray = new String[values.size()];
            result.put(name, values.toArray(valueArray));
        }

        return result;
    }

	public InetAddress getLocalAddress()
	{
		return localAddress;
	}

	public void setLocalAddress(InetAddress localAddress)
	{
		this.localAddress = localAddress;
	}

	public int getLocalPort()
	{
		return localPort;
	}

	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

	public InetAddress getRemoteAddress()
	{
		return remoteAddress;
	}

	public void setRemoteAddress(InetAddress remoteAddress)
	{
		this.remoteAddress = remoteAddress;
	}

	public int getRemotePort()
	{
		return remotePort;
	}

	public void setRemotePort(int remotePort)
	{
		this.remotePort = remotePort;
	}
}
