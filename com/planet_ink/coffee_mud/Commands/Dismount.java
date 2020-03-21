package com.planet_ink.coffee_mud.Commands;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_mud.core.collections.XVector;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Rider;

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

public class Dismount extends StdCommand
{
	public Dismount(){}

	private final String[] access=I(new String[]{"DISMOUNT","DISEMBARK","LEAVE"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		final String cmdWord = commands.size()> 0 ? commands.get(0): "";
		Vector<String> origCmds=new XVector<String>(commands);
		commands.remove(0);
		if(commands.size()==0)
		{
			if(mob.riding()==null)
			{
				if(cmdWord.startsWith("L"))
				{
					CMLib.commands().doCommandFail(mob,origCmds,L("Which way? Try EXITS."));
					return false;
				}
				else
				{
					CMLib.commands().doCommandFail(mob,origCmds,L("But you aren't riding anything?!"));
					return false;
				}
			}
			final CMMsg msg=CMClass.getMsg(mob,mob.riding(),null,CMMsg.MSG_DISMOUNT,L("<S-NAME> @x1 <T-NAMESELF>.",mob.riding().dismountString(mob)));
			if(mob.location().okMessage(mob,msg))
				mob.location().send(mob,msg);
		}
		else
		{
			final Environmental E=mob.location().fetchFromRoomFavorItems(null,CMParms.combine(commands,0));
			if((E==null)||(!(E instanceof Rider)))
			{
				CMLib.commands().doCommandFail(mob,origCmds,L("You don't see anything called '@x1' here to dismount from anything.",CMParms.combine(commands,0)));
				return false;
			}
			final Rider RI=(Rider)E;
			if((RI.riding()==null)
			   ||((RI.riding() instanceof MOB)&&(!mob.location().isInhabitant((MOB)RI.riding())))
			   ||((RI.riding() instanceof Item)&&(!mob.location().isContent((Item)RI.riding())))
			   ||(!CMLib.flags().canBeSeenBy(RI.riding(),mob)))
			{
				CMLib.commands().doCommandFail(mob,origCmds,L("But @x1 is not mounted to anything?!",RI.name(mob)));
				return false;
			}
			if((RI instanceof MOB)&&(!CMLib.flags().isBoundOrHeld(RI))&&(!((MOB)RI).willFollowOrdersOf(mob)))
			{
				CMLib.commands().doCommandFail(mob,origCmds,L("@x1 may not want you to do that.",RI.name(mob)));
				return false;
			}
			final CMMsg msg=CMClass.getMsg(mob,RI.riding(),RI,CMMsg.MSG_DISMOUNT,L("<S-NAME> dismount(s) <O-NAME> from <T-NAMESELF>."));
			if(mob.location().okMessage(mob,msg))
				mob.location().send(mob,msg);
		}
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
