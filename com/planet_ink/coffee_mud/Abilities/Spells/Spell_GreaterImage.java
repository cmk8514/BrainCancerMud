package com.planet_ink.coffee_mud.Abilities.Spells;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2017-2017 Bo Zimmerman

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
public class Spell_GreaterImage extends Spell_MinorImage
{

	@Override
	public String ID()
	{
		return "Spell_GreaterImage";
	}

	private final static String	localizedName	= CMLib.lang().L("Greater Image");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int getDuration(final MOB caster, final int asLevel)
	{
		return 20 + (super.adjustedLevel(caster, asLevel)/2);
	}
	
	@Override
	protected boolean canSeeAppearance()
	{
		return true;
	}
	
	@Override
	protected int canTargetCode()
	{
		return CAN_MOBS;
	}

	@Override
	protected boolean canTargetOthers()
	{
		return true;
	}
}
