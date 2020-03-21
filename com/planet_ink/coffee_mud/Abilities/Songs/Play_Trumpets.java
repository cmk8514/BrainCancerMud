package com.planet_ink.coffee_mud.Abilities.Songs;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Items.interfaces.MusicalInstrument.InstrumentType;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2003-2017 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class Play_Trumpets extends Play_Instrument
{
	@Override
	public String ID()
	{
		return "Play_Trumpets";
	}

	private final static String	localizedName	= CMLib.lang().L("Trumpets");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected InstrumentType requiredInstrumentType()
	{
		return InstrumentType.TRUMPETS;
	}

	@Override
	public String mimicSpell()
	{
		return "Spell_Shatter";
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	private static Ability	theSpell	= null;

	@Override
	protected Ability getSpell()
	{
		if (theSpell != null)
			return theSpell;
		if (mimicSpell().length() == 0)
			return null;
		theSpell = CMClass.getAbility(mimicSpell());
		return theSpell;
	}

}
