/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
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

public class Chant_FreeVine extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_FreeVine";
	}

	private final static String localizedName = CMLib.lang().L("Free Vine");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String displayText()
	{
		return "";
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_PLANTCONTROL;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_MOBS;
	}

	@Override
	protected int canTargetCode()
	{
		return CAN_MOBS;
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		if((affected!=null)
		&&(affected instanceof MOB)
		&&(msg.amISource((MOB)affected)))
		{
			if(((msg.targetMinor()==CMMsg.TYP_LEAVE)
			||(msg.sourceMinor()==CMMsg.TYP_ADVANCE)
			||(msg.sourceMinor()==CMMsg.TYP_RETREAT)))
				unInvoke();
		}
		super.executeMsg(myHost,msg);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;

		if(!target.charStats().getMyRace().ID().equals("Vine"))
		{
			mob.tell(L("@x1 can not be uprooted.",target.name(mob)));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> chant(s)freely to <T-NAMESELF>.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				if(msg.value()<=0)
				{
					mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("<S-NAME> pull(s) <S-HIS-HER> roots up!"));
					beneficialAffect(mob,target,asLevel,0);
				}
			}
		}
		else
			return maliciousFizzle(mob,target,L("<S-NAME> chant(s) freely to <T-NAMESELF>, but the magic fades"));

		// return whether it worked
		return success;
	}
}
