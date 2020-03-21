package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.PlayerStats;
import com.planet_ink.coffee_mud.Common.interfaces.Session;
import com.planet_ink.coffee_mud.Common.interfaces.Session.InputCallback;
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

public class Password extends StdCommand
{
	public Password()
	{
	}

	private final String[]	access	= I(new String[] { "PASSWORD" });

	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(final MOB mob, List<String> commands, int metaFlags) throws java.io.IOException
	{
		final PlayerStats pstats = mob.playerStats();
		if (pstats == null)
			return false;
		if (mob.isMonster())
			return false;
		final Session sess = mob.session();
		if (sess != null)
		{
			sess.prompt(new InputCallback(InputCallback.Type.PROMPT)
			{
				@Override
				public void showPrompt()
				{
					sess.promptPrint(L("Enter your old password : "));
				}

				@Override
				public void timedOut()
				{
				}

				@Override
				public void callBack()
				{
					final String old = this.input;
					sess.prompt(new InputCallback(InputCallback.Type.PROMPT)
					{
						@Override
						public void showPrompt()
						{
							sess.promptPrint(L("Enter a new password    : "));
						}

						@Override
						public void timedOut()
						{
						}

						@Override
						public void callBack()
						{
							final String nep = this.input;
							sess.prompt(new InputCallback(InputCallback.Type.PROMPT)
							{
								@Override
								public void showPrompt()
								{
									sess.promptPrint(L("Enter new password again: "));
								}

								@Override
								public void timedOut()
								{
								}

								@Override
								public void callBack()
								{
									final String ne2 = this.input;
									if (!pstats.matchesPassword(old))
										mob.tell(L("Your old password was not entered correctly."));
									else if (!nep.equals(ne2))
										mob.tell(L("Your new password was not entered the same way twice!"));
									else
									{
										pstats.setPassword(nep);
										mob.tell(L("Your password has been changed."));
										if (pstats.getAccount() != null)
											CMLib.database().DBUpdateAccount(pstats.getAccount());
										CMLib.database().DBUpdatePassword(mob.Name(), pstats.getPasswordStr());
									}
								}
							});
						}
					});
				}
			});
		}
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return false;
	}

}
