/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Fighter;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

/*
   Copyright 2004-2017 Bo Zimmerman

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

public class Fighter_CounterAttack extends FighterSkill
{
	@Override
	public String ID()
	{
		return "Fighter_CounterAttack";
	}

	private final static String localizedName = CMLib.lang().L("Counter-Attack");

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
	protected int canAffectCode()
	{
		return CAN_MOBS;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_BENEFICIAL_SELF;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL|Ability.DOMAIN_MARTIALLORE;
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
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!(affected instanceof MOB))
			return true;

		final MOB mob=(MOB)affected;

		if(msg.amISource(mob)
		&&(CMLib.flags().isAliveAwakeMobileUnbound(mob,true))
		&&(msg.target() instanceof MOB)
		&&(msg.tool() instanceof Ability)
		&&((mob.fetchAbility(ID())==null)||proficiencyCheck(mob,0,false))
		&&(mob.rangeToTarget()==0))
		{
			if(msg.tool().ID().equals("Skill_Parry"))
			{
				final CMMsg msg2=CMClass.getMsg(mob,msg.target(),this,CMMsg.MSG_NOISYMOVEMENT,L("<S-NAME> position(s) <S-HIM-HERSELF> for a counterattack!"));
				msg.addTrailerMsg(msg2);
			}
			else
			if(msg.tool().ID().equals(ID()))
			{
				CMLib.combat().postAttack(mob,(MOB)msg.target(),mob.fetchWieldedItem());
				super.helpProficiency(mob, 0);
			}
		}
		return true;
	}
}
