package com.planet_ink.coffee_mud.core.intermud.i3.net;

/**
 * This class is thrown whenever an attempt to create
 * a bad user name is made.
 * Created: 28 September 1996
 * Last modified: 28 September 1996
 * @author George Reese (borg@imaginary.com)
 * @version 1.0
 */
public class InvalidNameException extends Exception
{
	public static final long serialVersionUID=0;
	/**
	 * Constructs a new invalid name exception with
	 * the specified reason.
	 * @param reason the reason for the exception
	 */
	public InvalidNameException(String reason)
	{
		super(reason);
	}
}
