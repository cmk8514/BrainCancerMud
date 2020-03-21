/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.Arrays;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2016-2017 Bo Zimmerman

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

public class Chant_BreatheAir extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_BreatheAir";
	}

	private final static String	localizedName	= CMLib.lang().L("Land Lungs");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String	localizedStaticDisplay	= CMLib.lang().L("(Land Lungs)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT | Ability.DOMAIN_SHAPE_SHIFTING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	protected int[]	lastSet	= null;
	protected int[]	newSet	= null;

	@Override
	public void unInvoke()
	{
		if(!(affected instanceof MOB))
			return;
		final MOB mob=(MOB)affected;

		super.unInvoke();
		if(canBeUninvoked())
			mob.tell(L("Your land lungs disappear."));
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if(mob!=null)
		{
			if(target instanceof MOB)
			{
				final MOB targetM=(MOB)target;
				if((targetM.fetchEffect(this.ID())!=null)
				||(CMParms.contains(targetM.charStats().getBreathables(),RawMaterial.RESOURCE_AIR)))
					return Ability.QUALITY_INDIFFERENT;
				final Room R=targetM.location();
				if((R!=null)
				&&(!CMLib.flags().isUnderWateryRoom(R)))
					return Ability.QUALITY_BENEFICIAL_OTHERS;
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public void affectCharStats(MOB affected, CharStats affectableStats)
	{
		super.affectCharStats(affected,affectableStats);
		final int[] breatheables=affectableStats.getBreathables();
		if(breatheables.length==0)
			return;
		if((lastSet!=breatheables)||(newSet==null))
		{
			newSet=Arrays.copyOf(affectableStats.getBreathables(),affectableStats.getBreathables().length+1);
			newSet[newSet.length-1]=RawMaterial.RESOURCE_AIR;
			Arrays.sort(newSet);
			lastSet=breatheables;
		}
		affectableStats.setBreathables(newSet);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		MOB target=super.getTarget(mob, commands, givenTarget, false, false);
		if(target == null)
			return false;
		if((target.fetchEffect(this.ID())!=null)
		||(CMParms.contains(target.charStats().getBreathables(),RawMaterial.RESOURCE_AIR)))
		{
			mob.tell(target,null,null,L("<S-NAME> <S-IS-ARE> already an air breather."));
			return false;
		}
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> chant(s) to <T-NAMESELF>.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("<S-NAME> grow(s) a pair of lungs!"));
				beneficialAffect(mob,target,asLevel,0);
			}
		}
		else
			beneficialWordsFizzle(mob,target,L("<S-NAME> chant(s) to <T-NAMESELF>, but nothing happens."));

		return success;
	}
}
