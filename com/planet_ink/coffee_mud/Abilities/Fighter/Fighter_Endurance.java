/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Fighter;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CharState;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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

public class Fighter_Endurance extends FighterSkill
{
	@Override
	public String ID()
	{
		return "Fighter_Endurance";
	}

	private final static String localizedName = CMLib.lang().L("Endurance");

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
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_SELF;
	}

	@Override
	protected int canAffectCode()
	{
		return Ability.CAN_MOBS;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public boolean isAutoInvoked()
	{
		return true;
	}

	@Override
	public boolean canBeUninvoked()
	{
		return false;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL|Ability.DOMAIN_FITNESS;
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!(affected instanceof MOB))
			return super.tick(ticking,tickID);

		final MOB mob=(MOB)affected;

		if(((CMLib.flags().isSitting(mob))||(CMLib.flags().isSleeping(mob)))
		&&(!mob.isInCombat())
		&&((mob.fetchAbility(ID())==null)||proficiencyCheck(null,0,false))
		&&(tickID==Tickable.TICKID_MOB))
		{
			final int bonus=(getXLEVELLevel(mob)/3)+1;
			for(int x=0;x<bonus;x++)
				CMLib.combat().recoverTick(mob);
			final CharState curState = mob.curState();
			final CharState maxState = mob.maxState();
			if((curState.getHitPoints() < maxState.getHitPoints())
			&&(curState.getMana() < maxState.getMana())
			&&(curState.getMovement() < maxState.getMovement()))
				helpProficiency(mob, 0);
		}
		return super.tick(ticking,tickID);
	}
}
