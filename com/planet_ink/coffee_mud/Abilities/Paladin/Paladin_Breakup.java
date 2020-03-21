package com.planet_ink.coffee_mud.Abilities.Paladin;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2002-2017 Bo Zimmerman

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

public class Paladin_Breakup extends StdAbility
{
	@Override
	public String ID()
	{
		return "Paladin_Breakup";
	}

	private final static String localizedName = CMLib.lang().L("Breakup Fight");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final String[] triggerStrings =I(new String[] {"BREAKUP"});
	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	protected int canTargetCode()
	{
		return Ability.CAN_MOBS;
	}

	@Override
	public int usageType()
	{
		return USAGE_MOVEMENT;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL|Ability.DOMAIN_LEGAL;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(mob.isInCombat())
		{
			mob.tell(L("You must end combat before trying to break up someone elses fight."));
			return false;
		}
		if((!auto)&&(!(CMLib.flags().isGood(mob))))
		{
			mob.tell(L("You don't feel worthy of a such a good act."));
			return false;
		}
		final MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;
		if(!target.isInCombat())
		{
			mob.tell(L("@x1 is not fighting anyone!",target.name(mob)));
			return false;
		}

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,CMMsg.MSG_NOISYMOVEMENT,auto?L("<T-NAME> exude(s) a peaceful aura."):L("<S-NAME> break(s) up the fight between <T-NAME> and @x1.",target.getVictim().name()));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				target.makePeace(true);
				final MOB victim=target.getVictim();
				if((victim!=null)
				   &&(victim.getVictim()==target))
					victim.makePeace(true);
			}
		}
		else
			beneficialVisualFizzle(mob,target,L("<S-NAME> attempt(s) to break up <T-NAME>'s fight, but fail(s)."));

		// return whether it worked
		return success;
	}
}
