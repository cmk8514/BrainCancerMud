package com.planet_ink.coffee_mud.Abilities.Songs;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
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
public class Dance_Tarantella extends Dance
{
	@Override
	public String ID()
	{
		return "Dance_Tarantella";
	}

	private final static String localizedName = CMLib.lang().L("Tarantella");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_BENEFICIAL_SELF;
	}

	protected int ticks=1;

	@Override
	protected String danceOf()
	{
		return name()+" Dance";
	}

	@Override
	public void affectCharStats(MOB affectedMOB, CharStats affectedStats)
	{
		super.affectCharStats(affectedMOB,affectedStats);
		affectedStats.setStat(CharStats.STAT_SAVE_POISON,affectedStats.getStat(CharStats.STAT_SAVE_POISON)+(adjustedLevel(invoker(),0)*2));
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking,tickID))
			return false;

		final MOB mob=(MOB)affected;
		if(mob==null)
			return false;

		if((++ticks)>=(15-getXLEVELLevel(invoker())))
		{
			final List<Ability> offenders=CMLib.flags().flaggedAffects(mob,Ability.ACODE_POISON);
			if(offenders!=null)
				for(int a=0;a<offenders.size();a++)
					offenders.get(a).unInvoke();
		}

		return true;
	}

}
