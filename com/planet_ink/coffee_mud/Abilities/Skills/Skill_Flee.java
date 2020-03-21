package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.collections.XVector;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2008-2017 Bo Zimmerman

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
public class Skill_Flee extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_Flee";
	}

	private final static String	localizedName	= CMLib.lang().L("Flee");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	private static final String[]	triggerStrings	= I(new String[] { "FLEE" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL | Ability.DOMAIN_DIRTYFIGHTING;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=(!mob.isInCombat())||proficiencyCheck(mob,getXLEVELLevel(mob)*10,auto);
		if(success)
		{
			final Vector<String> V=new XVector<String>("FLEE");
			V.addAll(commands);
			CMLib.commands().forceStandardCommand(mob, "FLEE", V);
		}
		else
			beneficialWordsFizzle(mob,null,L("<S-NAME> attempt(s) to flee, but fail(s) to get away"));

		// return whether it worked
		return success;
	}

}

