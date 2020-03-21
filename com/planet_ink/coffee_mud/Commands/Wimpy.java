package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMath;

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

public class Wimpy extends StdCommand
{
	public Wimpy(){}

	private final String[] access=I(new String[]{"WIMPY"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		if(commands.size()<2)
		{
			mob.tell(L("Change your wimp level to what?"));
			return false;
		}
		final String amt=CMParms.combine(commands,1);
		int newWimp = mob.baseState().getHitPoints();
		if(CMath.isPct(amt))
			newWimp = (int)Math.round(CMath.s_pct(amt) * newWimp);
		else
		if(CMath.isInteger(amt))
			newWimp=CMath.s_int(amt);
		else
		{
			mob.tell(L("You can't change your wimp level to '@x1'",amt));
			return false;
		}
		mob.setWimpHitPoint(newWimp);
		mob.tell(L("Your wimp level has been changed to @x1 hit points.",""+mob.getWimpHitPoint()));
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}

}
