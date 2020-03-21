package com.planet_ink.coffee_mud.Commands;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.collections.XVector;

/*
   Copyright 2006-2017 Bo Zimmerman

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

public class Mood extends StdCommand
{
	public Mood()
	{
	}

	private final String[]	access	= I(new String[] { "MOOD" });

	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		final Ability A=CMClass.getAbility("Mood");
		if(A!=null)
		{
			final Vector<String> V=new XVector<String>(commands);
			V.remove(0);
			A.invoke(mob,V,mob,true,0);
		}
		else
			mob.tell(L("This command is not implemented."));
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}
}
