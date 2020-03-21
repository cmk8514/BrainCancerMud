package com.planet_ink.coffee_mud.Abilities.Spells;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Exits.interfaces.Exit;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2002-2017 Bo Zimmerman

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

public class Spell_IllusoryWall extends Spell
{

	@Override
	public String ID()
	{
		return "Spell_IllusoryWall";
	}

	private final static String localizedName = CMLib.lang().L("Illusory Wall");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_EXITS;
	}

	@Override
	protected int canTargetCode()
	{
		return CAN_EXITS;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL|Ability.DOMAIN_ILLUSION;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected,affectableStats);
		// when this spell is on a MOBs Affected list,
		// it should consistantly put the mob into
		// a sleeping state, so that nothing they do
		// can get them out of it.
		affectableStats.setDisposition(affectableStats.disposition()|PhyStats.IS_INVISIBLE);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{

		final String whatToOpen=CMParms.combine(commands,0);
		final int dirCode=CMLib.directions().getGoodDirectionCode(whatToOpen);
		if(dirCode<0)
		{
			mob.tell(L("Cast which direction?!"));
			return false;
		}

		final Exit exit=mob.location().getExitInDir(dirCode);
		final Room room=mob.location().getRoomInDir(dirCode);

		if((exit==null)||(room==null)||(!CMLib.flags().canBeSeenBy(exit,mob)))
		{
			mob.tell(L("That way is already closed."));
			return false;
		}
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(!success)
			beneficialVisualFizzle(mob,null,L("<S-NAME> whisper(s) @x1, but nothing happens.",CMLib.directions().getDirectionName(dirCode)));
		else
		{
			final CMMsg msg=CMClass.getMsg(mob,exit,this,verbalCastCode(mob,exit,auto),auto?"":L("^S<S-NAME> whisper(s) @x1.^?",CMLib.directions().getDirectionName(dirCode)));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				beneficialAffect(mob,exit,asLevel,0);
			}
		}

		return success;
	}
}
