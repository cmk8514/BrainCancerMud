package com.planet_ink.coffee_mud.Abilities.Songs;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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
public class Skill_SlowFall extends BardSkill
{
	@Override
	public String ID()
	{
		return "Skill_SlowFall";
	}

	private final static String localizedName = CMLib.lang().L("Slow Fall");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String displayText()
	{
		return activated?"(Slow Fall)":"";
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
		return Ability.ACODE_SKILL|Ability.DOMAIN_FITNESS;
	}

	public boolean activated=false;

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected,affectableStats);
		if(activated)
			affectableStats.setWeight(0);
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(affected!=null)
		{
			if((CMath.bset(affected.phyStats().disposition(),PhyStats.IS_FALLING))
			   &&((!(affected instanceof MOB))
				  ||(((MOB)affected).fetchAbility(ID())==null)
				  ||proficiencyCheck((MOB)affected,0,false)))
			{
				activated=true;
				affected.recoverPhyStats();
				if(affected instanceof MOB)
					helpProficiency((MOB)affected, 0);
			}
			else
			if(activated)
			{
				activated=false;
				affected.recoverPhyStats();
			}
		}
		return super.tick(ticking,tickID);
	}
}
