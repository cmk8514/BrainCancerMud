package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;

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

public class NoTeach extends StdCommand
{
	public NoTeach(){}

	private final String[] access=I(new String[]{"NOTEACH"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		if(mob.isAttributeSet(MOB.Attrib.NOTEACH))
		{
			mob.setAttribute(MOB.Attrib.NOTEACH,false);
			mob.tell(L("You may now teach, train, or learn."));
		}
		else
		{
			mob.setAttribute(MOB.Attrib.NOTEACH,true);
			mob.tell(L("You are no longer teaching, training, or learning."));
		}
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}

}
