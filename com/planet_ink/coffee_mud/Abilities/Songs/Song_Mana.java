package com.planet_ink.coffee_mud.Abilities.Songs;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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
public class Song_Mana extends Song
{
	@Override
	public String ID()
	{
		return "Song_Mana";
	}

	private final static String localizedName = CMLib.lang().L("Mana");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking,tickID))
			return false;

		final MOB mob=(MOB)affected;
		if(mob==null)
			return true;
		if(invoker==null)
			return true;
		//int level=invoker.phyStats().level();
		//int mana=(int)Math.round(Integer.valueOf(level).doubleValue()/2.0);
		mob.curState().adjMana((adjustedLevel(invoker(),0)*3),mob.maxState());
		return true;
	}
}
