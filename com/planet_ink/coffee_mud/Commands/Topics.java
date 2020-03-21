package com.planet_ink.coffee_mud.Commands;
import java.util.List;
import java.util.Properties;

import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;

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

public class Topics extends ATopics
{
	public Topics(){}

	private final String[] access=I(new String[]{"TOPICS"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		final Properties helpFile=CMLib.help().getHelpFile();
		if(helpFile.size()==0)
		{
			if(mob!=null)
				mob.tell(L("No help is available."));
			return false;
		}

		doTopics(mob,helpFile,"HELP", "PLAYER TOPICS");
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}

	@Override
	public boolean securityCheck(MOB mob)
	{
		return true;
	}
}
