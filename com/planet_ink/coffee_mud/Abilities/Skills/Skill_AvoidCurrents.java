package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.BoardableShip;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
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

public class Skill_AvoidCurrents extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_AvoidCurrents";
	}

	private final static String	localizedName	= CMLib.lang().L("Avoid Currents");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String	localizedStaticDisplay	= CMLib.lang().L("(Avoid Currents)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_MOBS;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL | Ability.DOMAIN_SEATRAVEL;
	}

	@Override
	public int usageType()
	{
		return USAGE_MOVEMENT|USAGE_MANA;
	}
	
	@Override
	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if(!super.okMessage(myHost, msg))
			return false;
		if((msg.target() instanceof BoardableShip)
		&&(msg.tool() != null)
		&&(msg.target() == affected)
		&&(msg.tool().ID().equals("AWaterCurrent"))
		&&(affected instanceof Item))
		{
			final MOB M=invoker();
			if((M!=null)
			&&(M.location()!=null)
			&&(M.location().getArea() instanceof BoardableShip)
			&&(((BoardableShip)M.location().getArea()).getShipItem() == msg.target())
			&&(super.proficiencyCheck(M, 0, false)))
			{
				super.helpProficiency(M, 0);
				final Room R=CMLib.map().roomLocation(msg.target());
				if(R!=null)
				{
					R.show(M, msg.target(), CMMsg.MSG_OK_VISUAL, L("<S-YOUPOSS> superior sailing skills keeps <T-NAME> from being swept away by currents."));
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if((CMLib.flags().isSitting(mob)||CMLib.flags().isSleeping(mob)))
		{
			mob.tell(L("You are on the floor!"));
			return false;
		}

		if(!CMLib.flags().isAliveAwakeMobileUnbound(mob,false))
			return false;

		final Room R=mob.location();
		if(R==null)
			return false;

		final Item target;
		if((R.getArea() instanceof BoardableShip)
		&&(((BoardableShip)R.getArea()).getShipItem() instanceof BoardableShip))
		{
			target=((BoardableShip)R.getArea()).getShipItem();
		}
		else
		{
			mob.tell(L("You must be on a sailing ship to steer around currents!"));
			return false;
		}

		if(target.fetchEffect(ID())!=null)
		{
			mob.tell(L("Your ship is already being steered around currents!"));
			return false;
		}
		
		Room shipR=CMLib.map().roomLocation(target);
		if((shipR==null)||(!CMLib.flags().isWaterySurfaceRoom(shipR))||(!target.subjectToWearAndTear()))
		{
			mob.tell(L("You must be on a sailing ship to steer around currents!"));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,CMMsg.MASK_MALICIOUS|CMMsg.MSG_NOISYMOVEMENT,auto?L("<T-NAME> is avoiding the currents!"):L("<S-NAME> plot(s) a course for <T-NAME> around the currents!"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				beneficialAffect(mob, target, asLevel, 0);
			}
		}
		else
			return beneficialVisualFizzle(mob,null,L("<S-NAME> attempt(s) to do plot a course around the currents, but mess(es) it up."));
		return success;
	}
}
