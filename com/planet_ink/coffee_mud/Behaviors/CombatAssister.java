package com.planet_ink.coffee_mud.Behaviors;
import com.planet_ink.coffee_mud.Abilities.interfaces.DiseaseAffect;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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
public class CombatAssister extends StdBehavior
{
	@Override
	public String ID()
	{
		return "CombatAssister";
	}

	@Override
	public String accountForYourself()
	{
		if(getParms().length()>0)
			return "protecting of "+CMLib.masking().maskDesc(getParms(),true);
		else
			return "protecting of others";
	}

	@Override
	public void executeMsg(Environmental affecting, CMMsg msg)
	{
		super.executeMsg(affecting,msg);
		if((msg.target()==null)||(!(msg.target() instanceof MOB)))
			return;
		final MOB mob=msg.source();
		final MOB monster=(MOB)affecting;
		final MOB target=(MOB)msg.target();

		if((mob!=monster)
		&&(target!=monster)
		&&(mob!=target)
		&&(CMath.bset(msg.targetMajor(),CMMsg.MASK_MALICIOUS))
		&&(!monster.isInCombat())
		&&(CMLib.flags().canBeSeenBy(mob,monster))
		&&(CMLib.flags().canBeSeenBy(target,monster))
		&&((!(msg.tool() instanceof DiseaseAffect))||(((DiseaseAffect)msg.tool()).isMalicious()))
		&&(CMLib.masking().maskCheck(getParms(),target,false)))
			Aggressive.startFight(monster,mob,true,false,null);
	}
}
