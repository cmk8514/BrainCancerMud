package com.planet_ink.coffee_mud.Commands;

import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.PlayerStats;
import com.planet_ink.coffee_mud.Common.interfaces.Session.InputCallback;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMStrings;
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

public class Title extends StdCommand
{
	private final String[]	access	= I(new String[] { "TITLE" });

	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(final MOB mob, List<String> commands, int metaFlags) throws java.io.IOException
	{
		if ((mob.playerStats() == null) || (mob.playerStats().getTitles().size() == 0))
		{
			mob.tell(L("You don't have any titles to select from."));
			return false;
		}
		final String currTitle = mob.playerStats().getTitles().get(0);
		if (currTitle.startsWith("{") && currTitle.endsWith("}"))
		{
			mob.tell(L("You can not change your current title."));
			return false;
		}
		final PlayerStats ps = mob.playerStats();
		final StringBuffer menu = new StringBuffer("^xTitles:^.^?\n\r");
		CMLib.titles().evaluateAutoTitles(mob);
		if (!ps.getTitles().contains("*"))
			ps.getTitles().add("*");
		for (int i = 0; i < ps.getTitles().size(); i++)
		{
			String title = ps.getTitles().get(i);
			if (title.startsWith("{") && title.endsWith("}"))
				title = title.substring(1, title.length() - 1);
			if (title.equalsIgnoreCase("*"))
				menu.append(CMStrings.padRight("" + (i + 1), 2) + ": Do not use a title.\n\r");
			else
				menu.append(CMStrings.padRight("" + (i + 1), 2) + ": " + CMStrings.replaceAll(title, "*", mob.Name()) + "\n\r");
		}
		final InputCallback[] IC = new InputCallback[1];
		IC[0] = new InputCallback(InputCallback.Type.PROMPT, "")
		{
			@Override
			public void showPrompt()
			{
				mob.tell(menu.toString());
				if (mob.session() != null)
					mob.session().promptPrint(L("Enter a selection: "));
			}

			@Override
			public void timedOut()
			{
			}

			@Override
			public void callBack()
			{
				final int num = CMath.s_int(this.input);
				if ((num > 0) && (num <= ps.getTitles().size()))
				{
					final String which = ps.getTitles().get(num - 1);
					ps.getTitles().remove(num - 1);
					ps.getTitles().add(0, which);
					mob.tell(L("Title change accepted."));
				}
				else
					mob.tell(L("No change"));
			}
		};
		if (mob.session() != null)
			mob.session().prompt(IC[0]);
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}
}
