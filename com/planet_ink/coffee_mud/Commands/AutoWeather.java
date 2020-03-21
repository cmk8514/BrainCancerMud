package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMParms;

/*
   Copyright 2004-2017 Bo Zimmerman

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

public class AutoWeather extends StdCommand
{
	public AutoWeather(){}

	private final String[] access=I(new String[]{"AUTOWEATHER"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException

	{
		String parm = (commands.size() > 1) ? CMParms.combine(commands,1) : ""; 
		if((!mob.isAttributeSet(MOB.Attrib.AUTOWEATHER) && (parm.length()==0))||(parm.equalsIgnoreCase("ON")))
		{
			mob.setAttribute(MOB.Attrib.AUTOWEATHER,true);
			mob.tell(L("Weather descriptions are now on."));
		}
		else
		if((mob.isAttributeSet(MOB.Attrib.AUTOWEATHER) && (parm.length()==0))||(parm.equalsIgnoreCase("OFF")))
		{
			mob.setAttribute(MOB.Attrib.AUTOWEATHER,false);
			mob.tell(L("Weather descriptions are now off."));
		}
		else
		if(parm.length() > 0)
		{
			mob.tell(L("Illegal @x1 argument: '@x2'.  Try ON or OFF, or nothing to toggle.",getAccessWords()[0],parm));
		}
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}
}

