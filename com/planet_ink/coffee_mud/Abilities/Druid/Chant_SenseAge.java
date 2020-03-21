/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

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

public class Chant_SenseAge extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_SenseAge";
	}

	private final static String localizedName = CMLib.lang().L("Sense Age");

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
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_BREEDING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	@Override
	protected int overrideMana()
	{
		return 5;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final Physical target=getAnyTarget(mob,commands,givenTarget,Wearable.FILTER_ANY);
		if(target==null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> chant(s) over <T-NAMESELF>.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Ability A=target.fetchEffect("Age");
				if((!(target instanceof MOB))&&(A==null))
				{
					mob.tell(L("You have no way to determining the age of @x1.",target.name(mob)));
					success=false;
				}
				else
				if((target instanceof MOB)&&((A==null)||(A.displayText().length()==0)))
				{
					final MOB M=(MOB)target;
					if(M.baseCharStats().getStat(CharStats.STAT_AGE)<=0)
						mob.tell(L("You can't determine how old @x1 is with this magic.",target.name(mob)));
					else
						mob.tell(L("@x1 is @x2 @x3, aged @x4 years.",target.name(mob),CMLib.english().startWithAorAn(M.baseCharStats().ageName().toLowerCase()),M.baseCharStats().raceName(),""+M.baseCharStats().getStat(CharStats.STAT_AGE)));
				}
				else
				{
					String s=A.displayText();
					if(s.startsWith("(")) s=s.substring(1);
					if(s.endsWith(")")
						) s=s.substring(0,s.length()-1);
					mob.tell(L("@x1 is @x2.",target.name(mob),s));
				}
			}
		}
		else
			return beneficialWordsFizzle(mob,target,L("<S-NAME> chant(s) over <T-NAMESELF>, but the magic fades."));

		// return whether it worked
		return success;
	}
}
