package com.planet_ink.coffee_mud.Abilities.Misc;
import com.planet_ink.coffee_mud.core.CMLib;

public class Acidbreath extends Dragonbreath
{
	@Override
	public String ID()
	{
		return "Acidbreath";
	}

	private final static String	localizedName	= CMLib.lang().L("Acidbreath");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String text()
	{
		return "acid";
	}

	@Override
	public void setMiscText(String newText)
	{
		super.setMiscText(text());
	}

	private static final String[]	triggerStrings	= I(new String[] { "ACIDBREATH" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}
}
