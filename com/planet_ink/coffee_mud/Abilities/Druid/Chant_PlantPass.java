/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2002-2017 Bo Zimmerman

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

public class Chant_PlantPass extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_PlantPass";
	}

	private final static String	localizedName	= CMLib.lang().L("Plant Pass");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT | Ability.DOMAIN_SHAPE_SHIFTING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_TRANSPORTING;
	}

	protected String getPlantsWord()
	{
		return "plants";
	}

	protected boolean isAcceptableTargetRoom(MOB mob, Room newRoom)
	{
		return true;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(commands.size()<1)
		{
			mob.tell(L("You must specify the name of the location of one of your "+getPlantsWord()+".  Use your 'My Plants' skill if necessary."));
			return false;
		}
		final String areaName=CMParms.combine(commands,0).trim().toUpperCase();

		final Item myPlant=Druid_MyPlants.myPlant(mob.location(),mob,0);
		if(myPlant==null)
		{
			mob.tell(L("There doesn't appear to be any of your "+this.getPlantsWord()+" here to travel through."));
			return false;
		}

		Room newRoom=null;
		if(CMath.isInteger(areaName))
		{
			final List<Item> plants=Druid_MyPlants.getMyPlants(mob);
			int i=CMath.s_int(areaName);
			if((i>0)&&(i<=plants.size()))
				newRoom=CMLib.map().roomLocation(plants.get(i-1));
		}
		else
		{
			final List<Room> candidates=Druid_MyPlants.myPlantRooms(mob);
			for(int m=0;m<candidates.size();m++)
			{
				final Room room=candidates.get(m);
				if(CMLib.english().containsString(room.displayText(mob),areaName))
				{
					newRoom=room;
					break;
				}
			}
		}
		if(newRoom==null)
		{
			mob.tell(L("You can't seem to fixate on a place called '@x1', perhaps you have no "+this.getPlantsWord()+" there?",CMParms.combine(commands,0)));
			return false;
		}

		if(!this.isAcceptableTargetRoom(mob, newRoom))
			return false;
		
		final Item otherPlant = Druid_MyPlants.myPlant(newRoom, mob, 0);
		if(otherPlant==null)
		{
			mob.tell(L("You can't seem to fixate on place called '@x1', perhaps your "+this.getPlantsWord()+" there are dead?",CMParms.combine(commands,0)));
			return false;
		}
		
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,myPlant,this,CMMsg.MASK_MOVE|verbalCastCode(mob,myPlant,auto),auto?"":L("^S<S-NAME> chant(s) to <T-NAMESELF> and <S-IS-ARE> drawn into it!^?"));
			if((mob.location().okMessage(mob,msg))&&(newRoom.okMessage(mob,msg)))
			{
				mob.location().send(mob,msg);
				final List<MOB> h=properTargetList(mob,givenTarget,false);
				if(h==null)
					return false;

				final Room thisRoom=mob.location();
				for (final MOB follower : h)
				{
					final CMMsg enterMsg=CMClass.getMsg(follower,newRoom,this,CMMsg.MSG_ENTER,null,CMMsg.MSG_ENTER,null,CMMsg.MSG_ENTER,L("<S-NAME> emerge(s) from the @x1.",otherPlant.name()));
					final CMMsg leaveMsg=CMClass.getMsg(follower,thisRoom,this,CMMsg.MSG_LEAVE|CMMsg.MASK_MAGIC,L("<S-NAME> <S-IS-ARE> sucked into @x1.",myPlant.name()));
					if(thisRoom.okMessage(follower,leaveMsg)&&newRoom.okMessage(follower,enterMsg))
					{
						if(follower.isInCombat())
						{
							CMLib.commands().postFlee(follower,("NOWHERE"));
							follower.makePeace(false);
						}
						thisRoom.send(follower,leaveMsg);
						newRoom.bringMobHere(follower,false);
						newRoom.send(follower,enterMsg);
						follower.tell(L("\n\r\n\r"));
						CMLib.commands().postLook(follower,true);
					}
					else
					if(follower==mob)
						break;
				}
			}
		}
		else
			beneficialVisualFizzle(mob,myPlant,L("<S-NAME> chant(s) to <T-NAMESELF>, but nothing happens."));

		// return whether it worked
		return success;
	}
}
