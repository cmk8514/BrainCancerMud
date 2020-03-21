package com.planet_ink.coffee_mud.Abilities.Misc;
import com.planet_ink.coffee_mud.core.CMLib;

public class Firebreath extends Dragonbreath
{
	@Override
	public String ID()
	{
		return "Firebreath";
	}

	private final static String	localizedName	= CMLib.lang().L("Firebreath");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String text()
	{
		return "fire";
	}

	@Override
	public void setMiscText(String newText)
	{
		super.setMiscText(text());
	}

	private static final String[]	triggerStrings	= I(new String[] { "FIREBREATH", "BREATHEFIRE", "BREATHFIRE" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}
}
