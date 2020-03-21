package com.planet_ink.coffee_mud.core.intermud.i3.server;

/**
 * This exception gets thrown by the server when some
 * class tries to perform an operation it should not
 * be allowed to perform.
 * @author George Reese (borg@imaginary.com)
 * @version 1.0
 */
public class ServerSecurityException extends Exception
{
	public static final long serialVersionUID=0;
	/**
	 * Constructs a new security excetption with a generic
	 * message.
	 */
	public ServerSecurityException()
	{
		this("A general security exception occurred.");
	}

	/**
	 * Constructs a new security exception with the
	 * specified error message,
	 * @param err the error message
	 */
	public ServerSecurityException(String err)
	{
		super(err);
	}
}
