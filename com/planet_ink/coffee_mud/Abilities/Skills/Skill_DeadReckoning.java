package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Exits.interfaces.Exit;
import com.planet_ink.coffee_mud.Items.interfaces.BoardableShip;
import com.planet_ink.coffee_mud.Libraries.interfaces.TrackingLibrary;
import com.planet_ink.coffee_mud.Libraries.interfaces.TrackingLibrary.TrackingFlag;
import com.planet_ink.coffee_mud.Libraries.interfaces.TrackingLibrary.TrackingFlags;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
import com.planet_ink.coffee_mud.core.interfaces.Rideable;

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

public class Skill_DeadReckoning extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_DeadReckoning";
	}

	private final static String	localizedName	= CMLib.lang().L("Dead Reckoning");

	@Override
	public String name()
	{
		return localizedName;
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
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	private static final String[]	triggerStrings	= I(new String[] { "DEADRECKON","DEADRECKONING" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL | Ability.DOMAIN_SEATRAVEL;
	}

	@Override
	public int usageType()
	{
		return USAGE_MOVEMENT | USAGE_MANA;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final Room R=mob.location();
		if(R==null)
			return false;
		Room currentR=null;
		if(R.getArea() instanceof BoardableShip)
		{
			currentR=CMLib.map().roomLocation(((BoardableShip)R.getArea()).getShipItem());
		}
		else
		if((mob.riding() !=null) && (mob.riding().rideBasis() == Rideable.RIDEABLE_WATER))
		{
			if(CMLib.flags().isWaterySurfaceRoom(mob.location()))
				currentR=mob.location();
		}
		else
		{
			mob.tell(L("This skill only works on board a ship or boat."));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;
		
		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final String str=L("<S-NAME> use(s) <S-HIS-HER> intuition to guess the nearest way to land.");
			final CMMsg msg=CMClass.getMsg(mob,null,this,CMMsg.MSG_DELICATE_HANDS_ACT,str);
			if(R.okMessage(mob,msg))
			{
				R.send(mob,msg);
				TrackingFlags flags=CMLib.tracking().newFlags().plus(TrackingFlag.NOAIR)
																.plus(TrackingFlag.WATERSURFACEORSHOREONLY);
				TrackingLibrary.RFilter destFilter = new TrackingLibrary.RFilter()
				{
					@Override
					public boolean isFilteredOut(Room hostR, Room R, Exit E, int dir)
					{
						if(R==null)
							return false;
						switch(R.domainType())
						{
						case Room.DOMAIN_INDOORS_UNDERWATER:
						case Room.DOMAIN_INDOORS_WATERSURFACE:
						case Room.DOMAIN_OUTDOORS_UNDERWATER:
						case Room.DOMAIN_OUTDOORS_WATERSURFACE:
							return true;
						}
						return false;
					}
				};
				List<Room> trail = CMLib.tracking().findTrailToAnyRoom(currentR, destFilter, flags, 100);
				if(trail.size()==1)
				{
					mob.tell(L("You seem to already be there."));
					return false;
				}
				if(trail.size()==0)
				{
					mob.tell(L("You can't seem to see any way to land from here."));
					return false;
				}
				int dir = CMLib.map().getRoomDir(trail.get(trail.size()-1), trail.get(trail.size()-2));
				if(dir < 0)
				{
					mob.tell(L("You can't seem to spot any way to land from here."));
					return false;
				}
				mob.tell(L("You believe the closest way to land is @x1",CMLib.directions().getDirectionName(dir)));
			}
		}
		else
			return beneficialVisualFizzle(mob,null,L("<S-NAME> can't seem to figure out where <S-HE-SHE> <S-IS-ARE>."));

		return success;
	}

}
