package com.planet_ink.coffee_mud.Abilities.Paladin;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;

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

public class Paladin_ImprovedResists extends PaladinSkill
{
	@Override
	public String ID()
	{
		return "Paladin_ImprovedResists";
	}

	private final static String localizedName = CMLib.lang().L("Paladin`s Resistance");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL|Ability.DOMAIN_HOLYPROTECTION;
	}

	@Override
	public void affectCharStats(MOB affected, CharStats affectableStats)
	{
		super.affectCharStats(affected,affectableStats);
		if((affected!=null)&&(CMLib.flags().isGood(affected)))
		{
			final int amount=(int)Math.round(CMath.mul(CMath.div(proficiency(),100.0),affected.phyStats().level()+(2*getXLEVELLevel(invoker))));
			for(final int i : CharStats.CODES.SAVING_THROWS())
				affectableStats.setStat(i,affectableStats.getStat(i)+amount);
		}
	}
}
