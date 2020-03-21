/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
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

public class Chant_ClearMoon extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_ClearMoon";
	}

	private final static String localizedName = CMLib.lang().L("Clear Moon");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
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
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_MOONALTERING;
	}

	public void clearMoons(Physical P)
	{
		if(P!=null)
		for(int a=P.numEffects()-1;a>=0;a--) // personal and reverse enumeration
		{
			final Ability A=P.fetchEffect(a);
			if((A!=null)
			&&(((A.classificationCode()&Ability.ALL_DOMAINS)==Ability.DOMAIN_MOONALTERING)
			   ||((A.classificationCode()&Ability.ALL_DOMAINS)==Ability.DOMAIN_MOONSUMMONING)))
				A.unInvoke();
		}
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(!success)
			this.beneficialVisualFizzle(mob,null,L("<S-NAME> chant(s) for a clear moon, but the magic fades."));
		else
		{
			final CMMsg msg=CMClass.getMsg(mob,null,this,verbalCastCode(mob,null,auto),L("^S<S-NAME> chant(s) for a clear moon.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Room thatRoom=mob.location();
				clearMoons(thatRoom);
				for(int i=0;i<thatRoom.numInhabitants();i++)
				{
					final MOB M=thatRoom.fetchInhabitant(i);
					clearMoons(M);
				}
				for(int i=0;i<thatRoom.numItems();i++)
				{
					final Item I=thatRoom.getItem(i);
					clearMoons(I);
				}
			}
		}

		return success;
	}
}
