package com.planet_ink.coffee_mud.Abilities.Paladin;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

/*
   Copyright 2007-2017 Bo Zimmerman

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

public class Paladin_Purity extends PaladinSkill
{
	@Override
	public String ID()
	{
		return "Paladin_Purity";
	}

	private final static String localizedName = CMLib.lang().L("Paladin`s Purity");

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

	public Paladin_Purity()
	{
		super();
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;
		if((affected==null)||(!(CMLib.flags().isGood(affected))))
			return true;
		if(!(affected instanceof MOB))
			return true;

		if((msg.sourceMinor()==CMMsg.TYP_FACTIONCHANGE)
		&&(msg.source()==affected)
		&&(msg.tool()!=null)
		&&(!msg.source().isMine(msg.tool()))
		&&(msg.value()<0)
		&&(msg.othersMessage()!=null)
		&&(msg.othersMessage().equalsIgnoreCase(CMLib.factions().AlignID())))
		{
			msg.source().location().show(msg.source(),null,CMMsg.MSG_OK_VISUAL,L("<S-YOUPOSS> purity protects <S-HIM-HER> from the evil influence."));
			return false;
		}
		return true;
	}
}
