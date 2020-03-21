package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Areas.interfaces.Area;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.BoardableShip;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.SailingShip;
import com.planet_ink.coffee_mud.Libraries.interfaces.TrackingLibrary;
import com.planet_ink.coffee_mud.Libraries.interfaces.TrackingLibrary.TrackingFlag;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.Directions;
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
public class Skill_MorseCode extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_MorseCode";
	}

	private final static String	localizedName	= CMLib.lang().L("Morse Code");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String displayText()
	{
		return "";
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_MOBS;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL | Ability.DOMAIN_SEATRAVEL;
	}

	@Override
	public boolean isAutoInvoked()
	{
		return true;
	}

	@Override
	public boolean canBeUninvoked()
	{
		return false;
	}

	@Override
	public int usageType()
	{
		return USAGE_MOVEMENT;
	}

	private static final String[]	triggerStrings	= I(new String[] { "MORSECODE", "MORSE" });

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		if((affected instanceof MOB)
		&&(!msg.amISource((MOB)affected))
		&&(msg.tool() instanceof Ability)
		&&(msg.tool().ID().equals("Skill_MorseCode"))
		&&(msg.sourceMinor()==CMMsg.NO_EFFECT)
		&&(msg.targetMinor()==CMMsg.NO_EFFECT)
		&&(msg.targetMessage()!=null)
		&&(msg.othersMessage()!=null))
			msg.addTrailerMsg(CMClass.getMsg((MOB)affected,null,null,CMMsg.MSG_OK_VISUAL,L("The morse code signals seem to say '@x1'.",msg.targetMessage()),CMMsg.NO_EFFECT,null,CMMsg.NO_EFFECT,null));
		super.executeMsg(myHost,msg);
	}

	protected int getRoomDir(Set<Room> higherRooms, Room thisRoom)
	{
		if(thisRoom != null)
		{
			for(int d = 0; d<=Directions.NUM_DIRECTIONS(); d++)
			{
				final Room R=thisRoom.getRoomInDir(d);
				if((R!=null)&&(higherRooms.contains(R)))
					return d;
			}
		}
		return -1;
	}
	
	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if((!auto)&&(mob.isInCombat()))
		{
			mob.tell(L("Not while you are fighting!"));
			return false;
		}

		final Room R=mob.location();
		if(R==null)
			return false;

		if(commands.size()==0)
		{
			if(mob.isMonster())
				commands.add(L("@x1 is over here!",mob.Name()));
			else
			{
				mob.tell(L("You need to specify the message to send using morse code."));
				return false;
			}
		}
		
		final Item outDoorsI;
		if(((R.domainType()&Room.INDOORS)==0))
		{
			outDoorsI=mob.fetchHeldItem();
			if((outDoorsI==null)||(!CMLib.flags().isLightSource(outDoorsI)))
			{
				mob.tell(L("You need a light source to send morse code outdoors."));
				return false;
			}
		}
		else
			outDoorsI=null;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final String autoStr=L("<T-NAME> begin(s) morse coding!");
			final String sendStr;
			if(outDoorsI!=null)
				sendStr=L("<S-NAME> use(s) @x1 to send signals in morse code!",outDoorsI.name());
			else
				sendStr=L("<S-NAME> start(s) banging out morse code on the walls!");
			final CMMsg msg=CMClass.getMsg(mob,null,this,CMMsg.MSG_NOISYMOVEMENT,auto?autoStr:sendStr);
			if(R.okMessage(mob,msg))
			{
				R.send(mob,msg);
				final String str=CMParms.combine(commands,0);
				final int msgCode;
				if(outDoorsI!=null)
					msgCode=CMMsg.MSG_OK_VISUAL;
				else
					msgCode=CMMsg.MSG_OK_ACTION;
				final CMMsg msg2=CMClass.getMsg(mob,null,this,CMMsg.NO_EFFECT,null,CMMsg.NO_EFFECT,str,msgCode,null);
				final TrackingLibrary.TrackingFlags flags=CMLib.tracking().newFlags();
				if(outDoorsI!=null)
					flags.add(TrackingFlag.OUTDOORONLY);
				else
					flags.add(TrackingFlag.INDOORONLY);
				int range=3 + (super.getXLEVELLevel(mob)/2)+(super.getXMAXRANGELevel(mob));
				final List<Room> checkSet=CMLib.tracking().getRadiantRooms(R,flags,range);
				Area deckArea=null;
				Room deckShipRoom=null;
				Item deckShip=null;
				if(outDoorsI==null)
					msg2.setOthersMessage(L("You hear someone banging morse code on the walls in the distance."));
				else
				{
					if(R.getArea() instanceof BoardableShip)
					{
						deckShip=((BoardableShip)R.getArea()).getShipItem();
						if(deckShip instanceof SailingShip)
						{
							deckArea=R.getArea();
							deckShipRoom=CMLib.map().roomLocation(deckShip);
							if(deckShipRoom != null)
								checkSet.addAll(CMLib.tracking().getRadiantRooms(deckShipRoom,flags,range));
						}
					}
				}
				final Set<Room> higherRooms=new HashSet<Room>();
				for(final Iterator<Room> r=checkSet.iterator();r.hasNext();)
				{
					final Room R2=r.next();
					if(R2!=null)
					{
						if(outDoorsI!=null)
						{
							if(deckArea!=null)
							{
								if(deckArea == R2.getArea())
								{
									if(R2==R)
										msg2.setOthersMessage(L("<S-NAME> flash(es) morse code in lights on the deck."));
									else
										msg2.setOthersMessage(L("You see someone flashing morse code in lights on the deck."));
								}
								else
								{
									if(R2 == deckShipRoom)
										msg2.setOthersMessage(L("You see someone flashing morse code in lights on the deck of @x1.",(deckShip==null)?"":deckShip.name()));
									else
									{
										int dir = getRoomDir(higherRooms,R2);
										if(dir >= 0)
											msg2.setOthersMessage(L("You see someone flashing morse code in lights somewhere on a ship @x1.",CMLib.directions().getInDirectionName(dir)));
										else
											msg2.setOthersMessage(L("You see someone flashing morse code on a ship in the distance."));
									}
								}
							}
							else
							{
								int dir = getRoomDir(higherRooms,R2);
								if(dir >= 0)
									msg2.setOthersMessage(L("You see someone flashing morse code in lights somewhere @x1.",CMLib.directions().getInDirectionName(dir)));
								else
									msg2.setOthersMessage(L("You see someone flashing morse code in the distance."));
							}
						}
						if(R2.okMessage(mob,msg2))
							R2.sendOthers(msg2.source(),msg2);
						higherRooms.add(R2);
					}
				}
			}
		}
		else
			beneficialVisualFizzle(mob,null,L("<S-NAME> attempt(s) to send morse code, but mess(es) it up."));
		return success;
	}
}
