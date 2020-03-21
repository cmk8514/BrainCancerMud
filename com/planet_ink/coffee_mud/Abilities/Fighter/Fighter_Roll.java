/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Fighter;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
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

public class Fighter_Roll extends FighterSkill
{
	int regain=-1;

	@Override
	public String ID()
	{
		return "Fighter_Roll";
	}

	private final static String localizedName = CMLib.lang().L("Roll With Blows");

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
		return Ability.ACODE_SKILL|Ability.DOMAIN_EVASIVE;
	}

	public boolean doneThisRound=false;

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		regain=-1;
		if(!super.okMessage(myHost,msg))
			return false;

		if(!(affected instanceof MOB))
			return true;

		final MOB mob=(MOB)affected;
		if(msg.amITarget(mob)
		&&(CMLib.flags().isAliveAwakeMobile(mob,true))
		&&(msg.targetMinor()==CMMsg.TYP_DAMAGE)
		&&((msg.value())>0)
		&&(msg.tool() instanceof Weapon)
		&&(mob.rangeToTarget()==0)
		&&(!doneThisRound)
		&&((mob.fetchAbility(ID())==null)||proficiencyCheck(null,-85+mob.charStats().getStat(CharStats.STAT_DEXTERITY)+(2*getXLEVELLevel(mob)),false)))
		{
			doneThisRound=true;
			final double pctRecovery=(CMath.div(proficiency(),100.0)*Math.random());
			regain=(int)Math.round(CMath.mul((msg.value()),pctRecovery));
			msg.setValue(msg.value()-regain);
		}
		return true;
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(tickID==Tickable.TICKID_MOB)
			doneThisRound=false;
		return super.tick(ticking,tickID);
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		super.executeMsg(myHost,msg);

		if(!(affected instanceof MOB))
			return;

		final MOB mob=(MOB)affected;
		if((msg.amITarget(mob))
		&&(msg.targetMinor()==CMMsg.TYP_DAMAGE)
		&&(regain>0))
		{
			msg.addTrailerMsg(CMClass.getMsg(mob,null,CMMsg.MSG_NOISYMOVEMENT,L("<S-NAME> roll(s) with the hit.")));
			helpProficiency(mob, 0);
			regain=-1;
		}
	}
}
