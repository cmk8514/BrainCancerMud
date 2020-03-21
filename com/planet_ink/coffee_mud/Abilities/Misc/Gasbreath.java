package com.planet_ink.coffee_mud.Abilities.Misc;
import com.planet_ink.coffee_mud.core.CMLib;

public class Gasbreath extends Dragonbreath
{
	@Override
	public String ID()
	{
		return "Gasbreath";
	}

	private final static String	localizedName	= CMLib.lang().L("Gasbreath");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String text()
	{
		return "gas";
	}

	@Override
	public void setMiscText(String newText)
	{
		super.setMiscText(text());
	}

	private static final String[]	triggerStrings	= I(new String[] { "GASBREATH" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}
}
