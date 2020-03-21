package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMSecurity;
import com.planet_ink.coffee_mud.core.Log;

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

public class Ban extends StdCommand
{
	public Ban(){}

	private final String[] access=I(new String[]{"BAN"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		commands.remove(0);
		String banMe=CMParms.combine(commands,0);
		if(banMe.length()==0)
		{
			mob.tell(L("Ban what?  Enter an IP address or name mask."));
			return false;
		}
		banMe=banMe.toUpperCase().trim();
		final int b=CMSecurity.ban(banMe);
		if(b<0)
		{
			Log.sysOut("Ban",mob.Name()+" Banned "+banMe+".");
			mob.tell(L("Logins and IPs matching @x1 are now banned.",banMe));
		}
		else
		{
			mob.tell(L("That is already banned.  Do LIST BANNED and check out #@x1.",""+(b+1)));
			return false;
		}
		return true;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}

	@Override
	public boolean securityCheck(MOB mob)
	{
		return CMSecurity.isAllowed(mob,mob.location(),CMSecurity.SecFlag.BAN);
	}

}
