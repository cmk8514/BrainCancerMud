package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMStrings;

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

public class Description extends StdCommand
{
	public Description(){}

	private final String[] access=I(new String[]{"DESCRIPTION"});
	
	private final int CHAR_LIMIT = 128 * 1024;
	
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
			mob.tell(L("^xYour current description:^?\n\r@x1",mob.description()));
			mob.tell(L("\n\rEnter DESCRIPTION [NEW TEXT] to change."));
			return false;
		}
		
		final String s=CMParms.combine(commands,1);
		if(s.length()>CHAR_LIMIT)
			mob.tell(L("Your description exceeds @x1 characters in length.  Please re-enter a shorter one.",""+CHAR_LIMIT));
		else
		{
			mob.setDescription(CMStrings.convertHtmlToText(new StringBuilder(s)));
			mob.tell(L("Your description has been changed."));
		}
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return false;
	}

}
