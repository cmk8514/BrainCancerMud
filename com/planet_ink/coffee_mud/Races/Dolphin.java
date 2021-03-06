package com.planet_ink.coffee_mud.Races;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;

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
public class Dolphin extends Seal
{
	@Override
	public String ID()
	{
		return "Dolphin";
	}

	private final static String localizedStaticName = CMLib.lang().L("Dolphin");

	@Override
	public String name()
	{
		return localizedStaticName;
	}

	@Override
	public int shortestMale()
	{
		return 70;
	}

	@Override
	public int shortestFemale()
	{
		return 65;
	}

	@Override
	public int heightVariance()
	{
		return 10;
	}

	@Override
	public int lightestWeight()
	{
		return 300;
	}

	@Override
	public int weightVariance()
	{
		return 100;
	}

	@Override
	public int[] getBreathables()
	{
		return breatheAirWaterArray;
	}
	
	private final int[]	agingChart	= { 0, 2, 5, 8, 15, 25, 35, 45, 50 };

	@Override
	public int[] getAgingChart()
	{
		return agingChart;
	}

	@Override
	public void affectCharStats(MOB affectedMOB, CharStats affectableStats)
	{
		//super.affectCharStats(affectedMOB, affectableStats);
		affectableStats.setRacialStat(CharStats.STAT_INTELLIGENCE,1);
		affectableStats.setRacialStat(CharStats.STAT_STRENGTH,13);
		affectableStats.setRacialStat(CharStats.STAT_DEXTERITY,13);
		affectableStats.setRacialStat(CharStats.STAT_CHARISMA,20);
	}

	@Override
	public String arriveStr()
	{
		return "flops in";
	}

	@Override
	public String leaveStr()
	{
		return "flops";
	}

	@Override
	public Weapon myNaturalWeapon()
	{
		if(naturalWeapon==null)
		{
			naturalWeapon=CMClass.getWeapon("StdWeapon");
			naturalWeapon.setName(L("a nose butt"));
			naturalWeapon.setMaterial(RawMaterial.RESOURCE_BONE);
			naturalWeapon.setUsesRemaining(1000);
			naturalWeapon.setWeaponDamageType(Weapon.TYPE_BASHING);
		}
		return naturalWeapon;
	}
	
	@Override
	public List<RawMaterial> myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				for(int i=0;i<8;i++)
				{
					resources.addElement(makeResource
					(L("some @x1",name().toLowerCase()),RawMaterial.RESOURCE_FISH));
				}
				for(int i=0;i<5;i++)
				{
					resources.addElement(makeResource
					(L("a slippery @x1 hide",name().toLowerCase()),RawMaterial.RESOURCE_HIDE));
				}
				resources.addElement(makeResource
				(L("some @x1 blood",name().toLowerCase()),RawMaterial.RESOURCE_BLOOD));
			}
		}
		return resources;
	}
}
