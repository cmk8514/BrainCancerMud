package com.planet_ink.coffee_mud.Races;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
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
public class Swordfish extends GiantFish
{
	@Override
	public String ID()
	{
		return "Swordfish";
	}

	private final static String localizedStaticName = CMLib.lang().L("Swordfish");

	@Override
	public String name()
	{
		return localizedStaticName;
	}

	@Override
	public int shortestMale()
	{
		return 10;
	}

	@Override
	public int shortestFemale()
	{
		return 15;
	}

	@Override
	public int heightVariance()
	{
		return 20;
	}

	@Override
	public int lightestWeight()
	{
		return 355;
	}

	@Override
	public int weightVariance()
	{
		return 105;
	}

	@Override
	public Weapon myNaturalWeapon()
	{
		if(naturalWeapon==null)
		{
			naturalWeapon=CMClass.getWeapon("StdWeapon");
			naturalWeapon.setName(L("a pointy sword-nose"));
			naturalWeapon.setRanges(0,1);
			naturalWeapon.setMaterial(RawMaterial.RESOURCE_BONE);
			naturalWeapon.setUsesRemaining(1000);
			naturalWeapon.setWeaponDamageType(Weapon.TYPE_SLASHING);
		}
		return naturalWeapon;
	}

	protected static Vector<RawMaterial>	resources	= new Vector<RawMaterial>();

	@Override
	public void affectCharStats(MOB affectedMOB, CharStats affectableStats)
	{
		super.affectCharStats(affectedMOB, affectableStats);
		affectableStats.setRacialStat(CharStats.STAT_INTELLIGENCE,1);
		affectableStats.setRacialStat(CharStats.STAT_STRENGTH,13);
		affectableStats.setRacialStat(CharStats.STAT_DEXTERITY,16);
	}

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected, affectableStats);
		affectableStats.setAttackAdjustment(affectableStats.attackAdjustment() + (affectableStats.level() / 10));
	}

	@Override
	public List<RawMaterial> myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				for(int i=0;i<15;i++)
				{
					resources.addElement(makeResource
					(L("some @x1",name().toLowerCase()),RawMaterial.RESOURCE_FISH));
				}
				for(int i=0;i<10;i++)
				{
					resources.addElement(makeResource
					(L("a @x1 hide",name().toLowerCase()),RawMaterial.RESOURCE_HIDE));
				}
				resources.addElement(makeResource
				(L("a @x1 sword-nose",name().toLowerCase()),RawMaterial.RESOURCE_BONE));
				resources.addElement(makeResource
				(L("some @x1 blood",name().toLowerCase()),RawMaterial.RESOURCE_BLOOD));
			}
		}
		return resources;
	}
}
