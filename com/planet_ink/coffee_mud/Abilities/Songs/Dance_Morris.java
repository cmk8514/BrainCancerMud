package com.planet_ink.coffee_mud.Abilities.Songs;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

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
public class Dance_Morris extends Dance
{
	@Override
	public String ID()
	{
		return "Dance_Morris";
	}

	private final static String localizedName = CMLib.lang().L("Morris");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_MALICIOUS;
	}

	@Override
	protected String danceOf()
	{
		return name()+" Dance";
	}

	private boolean missedLastOne=false;

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected,affectableStats);
		if(affected==invoker)
			return;
		affectableStats.setArmor(affectableStats.armor()+(2*adjustedLevel(invoker(),0)));
		affectableStats.setAttackAdjustment(affectableStats.attackAdjustment()-(2*adjustedLevel(invoker(),0)));
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if((affected==null)||(!(affected instanceof MOB))||(invoker==null))
			return true;
		if(affected==invoker)
			return true;

		final MOB mob=(MOB)affected;
		// preventing distracting player from doin anything else
		if(msg.amISource(mob)
		&&(msg.targetMinor()==CMMsg.TYP_WEAPONATTACK)
		&&(!missedLastOne)
		&&(CMLib.dice().rollPercentage()>mob.charStats().getSave(CharStats.STAT_SAVE_MIND)))
		{
			missedLastOne=true;
			mob.location().show(mob,null,CMMsg.MSG_NOISYMOVEMENT,L("<S-NAME> become(s) distracted."));
			return false;
		}
		missedLastOne=false;
		return super.okMessage(myHost,msg);
	}

}
