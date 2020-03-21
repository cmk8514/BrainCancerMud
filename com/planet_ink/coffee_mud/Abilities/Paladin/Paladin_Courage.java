package com.planet_ink.coffee_mud.Abilities.Paladin;
import java.util.HashSet;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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

public class Paladin_Courage extends PaladinSkill
{
	@Override
	public String ID()
	{
		return "Paladin_Courage";
	}

	private final static String localizedName = CMLib.lang().L("Paladin`s Courage");

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

	public Paladin_Courage()
	{
		super();
		paladinsGroup=new HashSet<MOB>();
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;
		if((invoker==null)||(!(CMLib.flags().isGood(invoker))))
			return true;
		if(affected==null)
			return true;
		if(!(affected instanceof MOB))
			return true;

		if((msg.target() instanceof MOB)
		&&(paladinsGroup.contains(msg.target()))
		&&(!paladinsGroup.contains(msg.source()))
		&&(msg.source()!=invoker)
		&&(((MOB)msg.target()).location()==invoker.location()))
		{
			if((CMLib.flags().isGood(invoker))
			&&(msg.tool() instanceof Ability)
			&&((invoker==null)||(invoker.fetchAbility(ID())==null)||proficiencyCheck(null,0,false)))
			{
				final String str1=msg.tool().ID().toUpperCase();
				if((str1.indexOf("SPOOK")>=0)
				||(str1.indexOf("NIGHTMARE")>=0)
				||(str1.indexOf("FEAR")>=0))
				{
					final MOB mob=(MOB)msg.target();
					mob.location().showSource(mob,null,CMMsg.MSG_OK_VISUAL,L("Your courage protects you from the @x1 attack.",msg.tool().name()));
					mob.location().showOthers(mob,null,CMMsg.MSG_OK_VISUAL,L("<S-NAME>'s courage protects <S-HIM-HER> from the @x1 attack.",msg.tool().name()));
					return false;
				}
			}
		}
		return true;
	}
}
