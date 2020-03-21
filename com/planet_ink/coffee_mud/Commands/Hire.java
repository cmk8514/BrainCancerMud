package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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

public class Hire extends StdCommand
{
	public Hire(){}

	private final String[] access=I(new String[]{"HIRE"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		final String rest=CMParms.combine(commands,1);
		Environmental target=mob.location().fetchFromRoomFavorMOBs(null,rest);
		if((target!=null)&&(!target.name().equalsIgnoreCase(rest))&&(rest.length()<4))
			target=null;
		if((target!=null)&&(!CMLib.flags().canBeSeenBy(target,mob)))
			target=null;
		CMMsg msg=null;
		if(target==null)
			msg=CMClass.getMsg(mob,null,null,CMMsg.MSG_SPEAK,L("^T<S-NAME> say(s) 'I'm looking to hire some help.'^?"));
		else
			msg=CMClass.getMsg(mob,target,null,CMMsg.MSG_SPEAK,L("^T<S-NAME> say(s) to <T-NAMESELF> 'Are you for hire?'^?"));
		if(mob.location().okMessage(mob,msg))
			mob.location().send(mob,msg);
		return false;
	}

	@Override
	public double combatActionsCost(final MOB mob, final List<String> cmds)
	{
		return CMProps.getCommandCombatActionCost(ID());
	}

	@Override
	public double actionsCost(final MOB mob, final List<String> cmds)
	{
		return CMProps.getCommandActionCost(ID());
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}

}
