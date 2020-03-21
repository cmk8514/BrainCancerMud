/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Fighter;
import java.util.Enumeration;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.Shield;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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

public class Fighter_ImprovedShieldDefence extends FighterSkill
{
	@Override
	public String ID()
	{
		return "Fighter_ImprovedShieldDefence";
	}

	private final static String localizedName = CMLib.lang().L("Improved Shield Defence");

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
		return Ability.ACODE_SKILL|Ability.DOMAIN_SHIELDUSE;
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

	protected volatile int amountOfShieldArmor=-1;

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		if((!(affected instanceof MOB))||(amountOfShieldArmor<=0))
			return;
		affectableStats.setArmor(affectableStats.armor()-((int)Math.round(CMath.mul(amountOfShieldArmor,(CMath.div(proficiency()+(5.0*getXLEVELLevel(invoker())),100.0))))));
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		super.executeMsg(myHost,msg);

		if(!(affected instanceof MOB))
			return;

		final MOB mob=(MOB)affected;

		if((msg.amITarget(mob))
		&&(msg.targetMinor()==CMMsg.TYP_WEAPONATTACK)
		&&(amountOfShieldArmor>0)
		&&(mob.isInCombat())
		&&(CMLib.dice().rollPercentage()==1)
		&&(!mob.amDead()))
			helpProficiency(mob, 0);
		else
		if(msg.amISource(mob)&&(msg.target() instanceof Shield))
			amountOfShieldArmor=-1;
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking, tickID))
			return false;
		if((amountOfShieldArmor<0)&&(tickID==Tickable.TICKID_MOB)&&(ticking instanceof MOB))
		{
			amountOfShieldArmor=0;
			for(final Enumeration<Item> i=((MOB)ticking).items(); i.hasMoreElements(); )
			{
				final Item I=i.nextElement();
				if((I instanceof Shield)
				&&(I.amWearingAt(Wearable.WORN_HELD)||I.amWearingAt(Wearable.WORN_WIELD))
				&&(I.owner()==ticking)
				&&(I.container() == null))
					amountOfShieldArmor+=I.phyStats().armor();
			}
			((MOB)ticking).recoverPhyStats();
		}
		return true;
	}

	@Override
	public boolean autoInvocation(MOB mob, boolean force)
	{
		amountOfShieldArmor=-1;
		return super.autoInvocation(mob, force);
	}
}
