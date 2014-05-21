package org.ast4j.agi;

import java.util.Map;

public interface IAgiRequest
{
	/**
     * Returns the name of the script to execute including its full path.<p>
     * This corresponds to the request url with protocol, host, port and
     * parameters stripped off.
     * 
     * @return the name of the script to execute.
     */
    String getScript();
    
    /**
     * Returns the full URL of the requestURL in the form
     * agi://host[:port][/script][?param1=value1&param2=value2].
     * 
     * @return the full URL of the requestURL in the form
     *         agi://host[:port][/script][?param1=value1&param2=value2].
     */
    String getRequestURL();
	
	/**
     * Returns the name of the channel.
     * 
     * @return the name of the channel.
     */
    String getChannel();
    
    /**
     * Returns the unqiue id of the channel.
     * 
     * @return the unqiue id of the channel.
     */
    String getUniqueId();
    
    /**
     * Returns the Caller*ID number, for example "1234".<p>
     * Note: even with Asterisk 1.0 is contains only the numerical part
     * of the Caller ID.
     * 
     * @return the Caller*ID number, for example "1234", if no Caller*ID is set or it
     *         is "unknown" <code>null</code> is returned.
     */
    String getCallerIdNumber();
    
    /**
     * Returns the number, that has been dialed by the user.
     * 
     * @return the dialed number, if no DNID is available or it is "unknown"
     *         <code>null</code> is returned.
     */
    String getDnid();
    
    /**
     * Returns the context in the dial plan from which the AGI script was
     * called.
     * 
     * @return the context in the dial plan from which the AGI script was
     *         called.
     */
    String getContext();
    
    /**
     * Returns the value of a request parameter as a String, or
     * <code>null</code> if the parameter does not exist. You should only use
     * this method when you are sure the parameter has only one value.<p>
     * If the parameter might have more than one value, use
     * {@link #getParameterValues(String)}.<p>
     * If you use this method with a multivalued parameter, the value returned
     * is equal to the first value in the array returned by
     * <code>getParameterValues</code>.
     * 
     * @param name a String containing the name of the parameter whose value is
     *            requested.
     * @return a String representing the single value of the parameter.
     * @see #getParameterValues(String)
     */
    String getParameter(String name);

    /**
     * Returns an array of String objects containing all of the values the given
     * request parameter has, or
     * <code>null</coder> if the parameter does not exist.<p>
     * If the parameter has a single value, the array has a length of 1.
     * 
     * @param name a String containing the name of the parameter whose value is requested.
     * @return an array of String objects containing the parameter's values.
     */
    String[] getParameterValues(String name);

    /**
     * Returns a java.util.Map of the parameters of this request.
     * 
     * @return a java.util.Map containing parameter names as keys and parameter
     *         values as map values. The keys in the parameter map are of type
     *         String. The values in the parameter map are of type String array.
     */
    Map<String, String[]> getParameterMap();
}
