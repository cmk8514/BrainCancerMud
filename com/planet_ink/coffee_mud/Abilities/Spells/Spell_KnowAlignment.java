package com.planet_ink.coffee_mud.Abilities.Spells;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.Faction;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2001-2017 Bo Zimmerman

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

public class Spell_KnowAlignment extends Spell
{

	@Override
	public String ID()
	{
		return "Spell_KnowAlignment";
	}

	private final static String localizedName = CMLib.lang().L("Know Alignment");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL|Ability.DOMAIN_DIVINATION;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;
		if(target==mob)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^SYou draw out <T-NAME>s disposition.^?"),verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> draw(s) out your disposition.^?"),verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> draws out <T-NAME>s disposition.^?"));
		if(mob.location().okMessage(mob,msg))
		{
			mob.location().send(mob,msg);
			if(success)
				mob.tell(mob,target,null,L("<T-NAME> seem(s) like <T-HE-SHE> is @x1.",CMLib.flags().getAlignmentName(target).toLowerCase()));
			else
			{
				mob.tell(mob,target,null,L("<T-NAME> seem(s) like <T-HE-SHE> is @x1.",Faction.Align.values()[CMLib.dice().roll(1,Faction.Align.values().length-1,0)].toString().toLowerCase()));
			}
		}

		// return whether it worked
		return success;
	}
}
