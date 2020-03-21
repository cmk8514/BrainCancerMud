package com.planet_ink.coffee_mud.Commands;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.BoardableShip;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.collections.XVector;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.ItemPossessor;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

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

public class Push extends Go
{
	public Push()
	{
	}

	private final String[]	access	= I(new String[] { "PUSH" });

	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		Vector<String> origCmds=new XVector<String>(commands);
		Physical pushThis=null;
		String dir="";
		int dirCode=-1;
		Environmental E=null;
		if(commands.size()>1)
		{
			dirCode=CMLib.directions().getGoodDirectionCode(commands.get(commands.size()-1));
			if(dirCode>=0)
			{
				if((mob.location().getRoomInDir(dirCode)==null)
				||(mob.location().getExitInDir(dirCode)==null)
				||(!mob.location().getExitInDir(dirCode).isOpen()))
				{
					if(CMLib.flags().isFloatingFreely(mob))
					{
						E=mob.location();
					}
					else
					{
						CMLib.commands().doCommandFail(mob,origCmds,L("You can't push anything that way."));
						return false;
					}
				}
				else
					E=mob.location().getRoomInDir(dirCode);
				dir=" "+(((mob.location() instanceof BoardableShip)||(mob.location().getArea() instanceof BoardableShip))?
						CMLib.directions().getShipDirectionName(dirCode):CMLib.directions().getDirectionName(dirCode));
				commands.remove(commands.size()-1);
			}
		}
		if(dir.length()==0)
		{
			dirCode=CMLib.directions().getGoodDirectionCode(commands.get(commands.size()-1));
			if(dirCode>=0)
				pushThis=mob.location().getExitInDir(dirCode);
		}
		final String itemName=CMParms.combine(commands,1);
		if(pushThis==null)
			pushThis=mob.location().fetchFromRoomFavorItems(null,itemName);
		if(pushThis==null)
			pushThis=mob.location().fetchFromMOBRoomFavorsItems(mob,null,itemName,Wearable.FILTER_ANY);

		if((pushThis==null)||(!CMLib.flags().canBeSeenBy(pushThis,mob)))
		{
			CMLib.commands().doCommandFail(mob,origCmds,L("You don't see '@x1' here.",itemName));
			return false;
		}
		final int malmask=(pushThis instanceof MOB)?CMMsg.MASK_MALICIOUS:0;
		final String msgStr = "<S-NAME> push(es) <T-NAME>"+dir+".";
		final CMMsg msg=CMClass.getMsg(mob,pushThis,E,CMMsg.MSG_PUSH|malmask,msgStr);
		if(mob.location().okMessage(mob,msg))
		{
			mob.location().send(mob,msg);
			if((dir.length()>0)
			&&(msg.tool() instanceof Room)
			&&(msg.tool()!=mob.location()))
			{
				final Room R=(Room)msg.tool();
				if(R.okMessage(mob,msg))
				{
					dirCode=CMLib.tracking().findRoomDir(mob,R);
					if(dirCode>=0)
					{
						if(msg.othersMessage().equals(msgStr))
							msg.setOthersMessage("<S-NAME> push(es) <T-NAME> into here.");
						R.sendOthers(mob,msg);
						int expense = Math.round(CMath.sqrt(pushThis.phyStats().weight()));
						if(expense < CMProps.getIntVar(CMProps.Int.RUNCOST))
							expense = CMProps.getIntVar(CMProps.Int.RUNCOST);
						for(int i=0;i<expense;i++)
							CMLib.combat().expendEnergy(mob,true);
						if(pushThis instanceof Item)
							R.moveItemTo((Item)pushThis,ItemPossessor.Expire.Player_Drop,ItemPossessor.Move.Followers);
						else
						if(pushThis instanceof MOB)
							CMLib.tracking().walk((MOB)pushThis,dirCode,((MOB)pushThis).isInCombat(),false,true,true);
						final int movesRequired = pushThis.phyStats().movesReqToPush() - (mob.maxCarry() / 3);
						if(movesRequired > 0)
							mob.curState().adjMovement(-movesRequired, mob.maxState());
					}
				}
			}

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
