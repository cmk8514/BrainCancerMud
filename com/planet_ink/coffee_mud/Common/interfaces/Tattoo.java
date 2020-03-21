package com.planet_ink.coffee_mud.Common.interfaces;
import com.planet_ink.coffee_mud.core.interfaces.CMObject;

/**
 * Tattoos are arbitrary markers or flags.  They server no other
 * purpose other than to be checked by other things for their existence.
 * They can automatically expire by setting a tick down, or a number of
 * ticks to live.
 */
public interface Tattoo extends Cloneable, CMObject, CMCommon
{
	
	/**
	 * Set the tattoo name
	 * @param name the tattoo name
	 * @return this
	 */
	public Tattoo set(String name);

	/**
	 * Set the tatoo name and tick-down
	 * @param name the tatoo name
	 * @param down the tick down life span
	 * @return this
	 */
	public Tattoo set(String name, int down);

	/**
	 * Returns the current tick-down
	 * @return the tickDown
	 */
	public int getTickDown();

	/**
	 * Reduces the tick down by one and returns the new value
	 * @return the new tick down
	 */
	public int tickDown();
	
	/**
	 * Returns the tattoo Name
	 * @return the tattooName
	 */
	public String getTattooName();
	
	/**
	 * Parse a new tattoo object from the
	 * coded data, of the form:
	 * TATOONAME
	 * or
	 * NUMBER TATTOONAME
	 * 
	 * @param tattooCode coded data
	 * @return this tattoo
	 */
	public Tattoo parse(String tattooCode);
}
