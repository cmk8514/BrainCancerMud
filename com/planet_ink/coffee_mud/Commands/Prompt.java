package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.PlayerStats;
import com.planet_ink.coffee_mud.Common.interfaces.Session;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMProps;

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

public class Prompt extends StdCommand
{
	public Prompt(){}

	private final String[] access=I(new String[]{"PROMPT"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		if(mob.session()==null)
			return false;
		final PlayerStats pstats=mob.playerStats();
		final Session sess=mob.session();
		if(pstats==null)
			return false;

		if(commands.size()==1)
			sess.safeRawPrintln(L("Your prompt is currently set at:\n\r@x1",pstats.getPrompt()));
		else
		{
			String str=CMParms.combine(commands,1);
			String showStr=str;
			if(("DEFAULT").startsWith(str.toUpperCase()))
			{
				str="";
				showStr=CMProps.getVar(CMProps.Str.DEFAULTPROMPT);
			}
			if(sess.confirm(L("Change your prompt to: @x1, are you sure (Y/n)?",showStr),"Y"))
			{
				pstats.setPrompt(str);
				sess.safeRawPrintln(L("Your prompt is currently now set at:\n\r@x1",pstats.getPrompt()));
			}
		}
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return false;
	}

}
