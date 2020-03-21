package com.planet_ink.coffee_mud.core.intermud.i3.net;

/**
 * This class is designed to accept user input
 * outside of any command parsing structure.
 * You will generally subclass this to provide a
 * temporary place to direct user input when asking
 * the user questions and so on.  When the user types
 * something that has been redirected to a subclass of
 * Input, the string they type gets passed to the
 * input() method for processing.
 * Created: 28 September 1996
 * Last modified: 28 September 1996
 * @author George Reese (borg@imaginary.com)
 * @version 1.0
 * @see com.planet_ink.coffee_mud.core.intermud.i3.net.Interactive#redirectInput
 */

public interface Input 
{
	/**
	 * Implementations of this interface will define this
	 * method so that it processes user input.
	 * @param user the user? Honestly, I've no idea
	 * @param arg the input string
	 */
	public abstract void input(Interactive user, String arg);
}
