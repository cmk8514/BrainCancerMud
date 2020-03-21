package com.planet_ink.coffee_mud.Commands;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_mud.core.collections.XVector;

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

public class Remove extends StdCommand
{
	public Remove()
	{
	}

	private final String[]	access	= I(new String[] { "REMOVE", "REM" });

	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@SuppressWarnings("rawtypes")
	private final static Class[][] internalParameters=new Class[][]{{Item.class},{Item.class,Boolean.class}};

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		Vector<String> origCmds=new XVector<String>(commands);
		if(commands.size()<2)
		{
			CMLib.commands().doCommandFail(mob,origCmds,L("Remove what?"));
			return false;
		}
		commands.remove(0);
		final List<Item> items=CMLib.english().fetchItemList(mob,mob,null,commands,Wearable.FILTER_WORNONLY,false);
		if(items.size()==0)
			CMLib.commands().doCommandFail(mob,origCmds,L("You don't seem to be wearing that."));
		else
		for(int i=0;i<items.size();i++)
		{
			final Item item=items.get(i);
			final CMMsg newMsg=CMClass.getMsg(mob,item,null,CMMsg.MSG_REMOVE,L("<S-NAME> remove(s) <T-NAME>."));
			if(mob.location().okMessage(mob,newMsg))
				mob.location().send(mob,newMsg);
		}
		return false;
	}
	
	@Override
	public Object executeInternal(MOB mob, int metaFlags, Object... args) throws java.io.IOException
	{
		if(!super.checkArguments(internalParameters, args))
			return Boolean.FALSE;
		if(args[0] instanceof Item)
		{
			final Item item=(Item)args[0];
			final boolean quiet=((args.length>1) && (args[1] instanceof Boolean)) ? ((Boolean)args[1]).booleanValue() : false;
			final CMMsg newMsg=CMClass.getMsg(mob,item,null,CMMsg.MSG_REMOVE,quiet?null:L("<S-NAME> remove(s) <T-NAME>."));
			if(mob.location().okMessage(mob,newMsg))
			{
				mob.location().send(mob,newMsg);
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
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
