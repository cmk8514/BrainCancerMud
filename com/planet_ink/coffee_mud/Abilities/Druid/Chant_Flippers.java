/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Exits.interfaces.Exit;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.Races.interfaces.Race;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2016-2017 Bo Zimmerman

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

public class Chant_Flippers extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_Flippers";
	}

	private final static String	localizedName	= CMLib.lang().L("Flippers");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String	localizedStaticDisplay	= CMLib.lang().L("(Flippers)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT | Ability.DOMAIN_SHAPE_SHIFTING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	@Override
	public void unInvoke()
	{
		if(!(affected instanceof MOB))
			return;
		final MOB mob=(MOB)affected;

		super.unInvoke();
		if(canBeUninvoked())
			mob.tell(L("Your flippers disappear."));
	}

	@Override
	public void affectCharStats(MOB affected, CharStats affectableStats)
	{
		if(affectableStats.getBodyPart(Race.BODY_TAIL)==0)
			affectableStats.alterBodypart(Race.BODY_TAIL,2);
		super.affectCharStats(affected,affectableStats);
	}

	private final AtomicBoolean noRecurse = new AtomicBoolean(false);
	
	@Override
	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if(!super.okMessage(myHost, msg))
			return false;
		if((affected instanceof MOB)
		&&(!noRecurse.get())
		&&(msg.amISource((MOB)affected))
		&&(msg.targetMinor()==CMMsg.TYP_ENTER)
		&&(msg.target() instanceof Room)
		&&(msg.tool() instanceof Exit)
		&&(CMLib.flags().isSwimming(affected))
		&&(CMLib.flags().isWateryRoom((Room)msg.target()))
		&&(((Exit)msg.tool()).isOpen()))
		{
			try
			{
				noRecurse.set(true);
				final Room targetRoom = (Room)msg.target();
				final int dir = CMLib.map().getRoomDir(msg.source().location(), (Room)msg.target());
				final int level = this.adjustedLevel(invoker(), 0);
				final int numRooms = level / 10;
				Room finalRoom = targetRoom;
				for(int i=0;i<numRooms;i++)
				{
					Room R=finalRoom.getRoomInDir(dir);
					Exit E=finalRoom.getExitInDir(dir);
					if((R!=null)
					&&(E!=null)
					&&(CMLib.flags().isWateryRoom(R))
					&&(E.isOpen())
					&&(R.roomID().length()==0))
					{
						if(finalRoom!=targetRoom)
							finalRoom.send(msg.source(), msg);
						msg.setTarget(R);
						if(R.okMessage(myHost, msg))
						{
							if(finalRoom != targetRoom)
								CMLib.commands().postLook(msg.source(), true);
							finalRoom=R;
						}
						else
						{
							msg.setTarget(finalRoom);
							break;
						}
					}
					else
					{
						msg.setTarget(finalRoom);
						break;
					}
				}
			}
			finally
			{
				noRecurse.set(false);
			}
			
		}
		return true;
	}

	@Override
	public void executeMsg(Environmental myHost, CMMsg msg)
	{
		super.executeMsg(myHost, msg);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=super.getTarget(mob, commands, givenTarget, false, false);
		if(target == null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> chant(s) to <T-NAMESELF>.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("<S-NAME> grow(s) a pair of flippers!"));
				beneficialAffect(mob,target,asLevel,0);
			}
		}
		else
			beneficialWordsFizzle(mob,target,L("<S-NAME> chant(s) to <T-NAMESELF>, but nothing happens."));

		return success;
	}
}
