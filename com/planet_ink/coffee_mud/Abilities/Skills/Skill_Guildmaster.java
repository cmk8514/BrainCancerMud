package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.Enumeration;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.CharClasses.interfaces.CharClass;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

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
public class Skill_Guildmaster extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_Guildmaster";
	}

	private final static String localizedName = CMLib.lang().L("Guildmaster");

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
		return Ability.ACODE_SKILL|Ability.DOMAIN_INFLUENTIAL;
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

	protected static CharClass notAClass = null;
	
	protected boolean disabled=false;

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected, affectableStats);
		//affectableStats.setDisposition(affectableStats.disposition()|PhyStats.IS_UNATTACKABLE);
	}

	public boolean isInAweOfMe(final MOB attackerM, final MOB meM)
	{
		if(notAClass == null)
			notAClass = CMClass.getCharClass("StdCharClass");
		if(attackerM.charStats().getCurrentClass() == notAClass)
			return false;
		if(meM.playerStats()==null)
			return false;
		final CharClass C=attackerM.charStats().getCurrentClass();
		if(meM.charStats().getCurrentClass().baseClass().equals(C.baseClass()))
			return true;
		final String title = meM.playerStats().getActiveTitle();
		if((title==null)||(title.length()==0))
			return false;
		for(Enumeration<CharClass> chkc = CMClass.charClasses(); chkc.hasMoreElements();)
		{
			final CharClass chkC = chkc.nextElement();
			if(((chkC.baseClass().equals(C.baseClass())))
			&&(title.indexOf(chkC.name())>0))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(affected instanceof MOB)
		{
			if(((msg.targetMajor()&CMMsg.MASK_MALICIOUS)>0)
			&&(!CMath.bset(msg.sourceMajor(),CMMsg.MASK_ALWAYS)))
			{
				final MOB mob=(MOB)affected;
				if(msg.amISource((MOB)affected))
					disabled=true;
				else
				if(((msg.amITarget(mob)))
				&&(!disabled)
				&&(!msg.amISource((MOB)affected))
				&&(!mob.isInCombat())
				&&(isInAweOfMe(msg.source(),mob))
				&&(proficiencyCheck(mob,0,false)))
				{
					helpProficiency(mob, 0);
					if(!CMath.bset(msg.sourceMajor(),CMMsg.MASK_ALWAYS))
						msg.source().tell(msg.source(),affected,null,L("There is no way you would attack @x1.",mob.name()));
					mob.makePeace(true);
					return false;
				}
			}
			else
			if(!((MOB)affected).isInCombat())
				disabled=false;
		}
		return super.okMessage(myHost,msg);
	}
}
