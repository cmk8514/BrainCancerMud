/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.Climate;
import com.planet_ink.coffee_mud.Common.interfaces.TimeClock;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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

public class Chant_Chlorophyll extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_Chlorophyll";
	}

	private final static String localizedName = CMLib.lang().L("Chlorophyll");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String localizedStaticDisplay = CMLib.lang().L("(Chlorophyll)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_SHAPE_SHIFTING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_BENEFICIAL_OTHERS;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_MOBS;
	}

	@Override
	public void unInvoke()
	{
		// undo the affects of this spell
		if(!(affected instanceof MOB))
			return;
		final MOB mob=(MOB)affected;
		super.unInvoke();

		if(canBeUninvoked())
			if((mob.location()!=null)&&(!mob.amDead()))
				mob.location().show(mob,null,CMMsg.MSG_OK_VISUAL,L("<S-YOUPOSS> skin returns to a normal color."));
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking,tickID))
			return false;
		if(!(affected instanceof MOB))
		{
			unInvoke();
			return false;
		}
		final MOB mob=(MOB)affected;
		final Room R=mob.location();
		if((R!=null)
		&&((R.getArea().getTimeObj().getTODCode()==TimeClock.TimeOfDay.DAY)||(R.getArea().getTimeObj().getTODCode()==TimeClock.TimeOfDay.DAWN))
		&&((R.domainType()&Room.INDOORS)==0)
		&&((R.getArea().getClimateObj().weatherType(R)==Climate.WEATHER_CLEAR)
		   ||(R.getArea().getClimateObj().weatherType(R)==Climate.WEATHER_DROUGHT)
		   ||(R.getArea().getClimateObj().weatherType(R)==Climate.WEATHER_WINDY)
		   ||(R.getArea().getClimateObj().weatherType(R)==Climate.WEATHER_WINTER_COLD)
		   ||(R.getArea().getClimateObj().weatherType(R)==Climate.WEATHER_HEAT_WAVE)))
		mob.curState().adjHunger(2,mob.maxState().maxHunger(mob.baseWeight()));
		return true;
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if(mob!=null)
		{
			if(target instanceof MOB)
			{
				if(((MOB)target).isInCombat())
					return Ability.QUALITY_INDIFFERENT;
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=super.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			invoker=mob;
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?L("<S-NAME> gain(s) chlorophyll in <S-HIS-HER> skin!"):L("^S<S-NAME> chant(s) to <T-NAMESELF>, turning <T-HIM-HER> a light shade of chlorophyll green!^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				beneficialAffect(mob,target,asLevel,0);
			}
		}
		else
			return beneficialWordsFizzle(mob,target,L("<S-NAME> chant(s), but nothing more happens."));

		// return whether it worked
		return success;
	}
}
