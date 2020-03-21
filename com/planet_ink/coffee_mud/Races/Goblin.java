package com.planet_ink.coffee_mud.Races;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Areas.interfaces.Area;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.Armor;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2001-2017 Bo Zimmerman

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
public class Goblin extends StdRace
{
	@Override
	public String ID()
	{
		return "Goblin";
	}

	private final static String localizedStaticName = CMLib.lang().L("Goblin");

	@Override
	public String name()
	{
		return localizedStaticName;
	}

	@Override
	public int shortestMale()
	{
		return 45;
	}

	@Override
	public int shortestFemale()
	{
		return 40;
	}

	@Override
	public int heightVariance()
	{
		return 6;
	}

	@Override
	public int lightestWeight()
	{
		return 70;
	}

	@Override
	public int weightVariance()
	{
		return 50;
	}

	@Override
	public long forbiddenWornBits()
	{
		return 0;
	}

	private final static String localizedStaticRacialCat = CMLib.lang().L("Goblinoid");

	@Override
	public String racialCategory()
	{
		return localizedStaticRacialCat;
	}

	private final String[]	culturalAbilityNames		= { "Goblinese", "Orcish", "Mining", "Excavation" };
	private final int[]		culturalAbilityProficiencies= { 100, 50, 75, 25 };
	private final int[]		culturalAbilityLevels		= { 0,    0,  0,  5 };
	private final boolean[]	culturalAbilityGains		= { true, true, true, true };

	@Override
	public String[] culturalAbilityNames()
	{
		return culturalAbilityNames;
	}

	@Override
	public int[] culturalAbilityProficiencies()
	{
		return culturalAbilityProficiencies;
	}

	@Override
	protected int[] culturalAbilityLevels()
	{
		return culturalAbilityLevels;
	}

	@Override
	protected boolean[] culturalAbilityAutoGains()
	{
		return culturalAbilityGains;
	}

	//  							  an ey ea he ne ar ha to le fo no gi mo wa ta wi
	private static final int[] parts={0 ,2 ,2 ,1 ,1 ,2 ,2 ,1 ,2 ,2 ,1 ,0 ,1 ,1 ,0 ,0 };

	@Override
	public int[] bodyMask()
	{
		return parts;
	}

	private final int[]	agingChart	= { 0, 1, 2, 12, 21, 34, 52, 57, 63 };

	@Override
	public int[] getAgingChart()
	{
		return agingChart;
	}

	protected static Vector<RawMaterial>	resources	= new Vector<RawMaterial>();

	@Override
	public int availabilityCode()
	{
		return Area.THEME_FANTASY | Area.THEME_SKILLONLYMASK;
	}

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected,affectableStats);
		affectableStats.setSensesMask(affectableStats.sensesMask()|PhyStats.CAN_SEE_INFRARED);
	}

	@Override
	public void affectCharStats(MOB affectedMOB, CharStats affectableStats)
	{
		super.affectCharStats(affectedMOB, affectableStats);
		if(affectedMOB.isMonster())
		{
			affectableStats.setRacialStat(CharStats.STAT_DEXTERITY,12);
			affectableStats.setRacialStat(CharStats.STAT_STRENGTH,6);
			affectableStats.setRacialStat(CharStats.STAT_WISDOM,8);
			affectableStats.setRacialStat(CharStats.STAT_CHARISMA,9);
		}
		else
		{
			affectableStats.setStat(CharStats.STAT_DEXTERITY,affectableStats.getStat(CharStats.STAT_DEXTERITY)+2);
			affectableStats.setStat(CharStats.STAT_MAX_DEXTERITY_ADJ,affectableStats.getStat(CharStats.STAT_MAX_DEXTERITY_ADJ)+2);
			affectableStats.setStat(CharStats.STAT_STRENGTH,affectableStats.getStat(CharStats.STAT_STRENGTH)-1);
			affectableStats.setStat(CharStats.STAT_MAX_STRENGTH_ADJ,affectableStats.getStat(CharStats.STAT_MAX_STRENGTH_ADJ)-1);
			affectableStats.setStat(CharStats.STAT_CHARISMA,affectableStats.getStat(CharStats.STAT_CHARISMA)-1);
			affectableStats.setStat(CharStats.STAT_MAX_CHARISMA_ADJ,affectableStats.getStat(CharStats.STAT_MAX_CHARISMA_ADJ)-1);
			affectableStats.setStat(CharStats.STAT_WISDOM,affectableStats.getStat(CharStats.STAT_WISDOM)-1);
			affectableStats.setStat(CharStats.STAT_MAX_WISDOM_ADJ,affectableStats.getStat(CharStats.STAT_MAX_WISDOM_ADJ)-1);
			affectableStats.setStat(CharStats.STAT_INTELLIGENCE,affectableStats.getStat(CharStats.STAT_INTELLIGENCE)+1);
			affectableStats.setStat(CharStats.STAT_MAX_INTELLIGENCE_ADJ,affectableStats.getStat(CharStats.STAT_MAX_INTELLIGENCE_ADJ)+1);
		}
	}

	@Override
	public Weapon myNaturalWeapon()
	{
		if(naturalWeapon==null)
		{
			naturalWeapon=CMClass.getWeapon("StdWeapon");
			naturalWeapon.setName(L("nasty little claws"));
			naturalWeapon.setMaterial(RawMaterial.RESOURCE_BONE);
			naturalWeapon.setUsesRemaining(1000);
			naturalWeapon.setWeaponDamageType(Weapon.TYPE_SLASHING);
		}
		return naturalWeapon;
	}

	@Override
	public String healthText(MOB viewer, MOB mob)
	{
		final double pct=(CMath.div(mob.curState().getHitPoints(),mob.maxState().getHitPoints()));

		if(pct<.10)
			return L("^r@x1^r is near a pitiful death!^N",mob.name(viewer));
		else
		if(pct<.20)
			return L("^r@x1^r is covered in blood.^N",mob.name(viewer));
		else
		if(pct<.30)
			return L("^r@x1^r is bleeding badly from lots of wounds.^N",mob.name(viewer));
		else
		if(pct<.40)
			return L("^y@x1^y has numerous bloody wounds and gashes.^N",mob.name(viewer));
		else
		if(pct<.50)
			return L("^y@x1^y has some bloody wounds and gashes.^N",mob.name(viewer));
		else
		if(pct<.60)
			return L("^p@x1^p has a few bloody wounds.^N",mob.name(viewer));
		else
		if(pct<.70)
			return L("^p@x1^p is cut and bruised heavily.^N",mob.name(viewer));
		else
		if(pct<.80)
			return L("^g@x1^g has some minor cuts and bruises.^N",mob.name(viewer));
		else
		if(pct<.90)
			return L("^g@x1^g has a few bruises and scratches.^N",mob.name(viewer));
		else
		if(pct<.99)
			return L("^g@x1^g has a few small bruises.^N",mob.name(viewer));
		else
			return L("^c@x1^c is in perfect health.^N",mob.name(viewer));
	}

	@Override
	public List<Item> outfit(MOB myChar)
	{
		if(outfitChoices==null)
		{
			// Have to, since it requires use of special constructor
			final Armor s1=CMClass.getArmor("GenShirt");
			if(s1 == null)
				return new Vector<Item>();
			outfitChoices=new Vector<Item>();
			s1.setName(L("a small ratty tunic"));
			s1.setDisplayText(L("a small ratty tunic is folded neatly here."));
			s1.setDescription(L("It is a small ratty abused little shirt."));
			s1.text();
			outfitChoices.add(s1);
			final Armor p1=CMClass.getArmor("GenPants");
			p1.setName(L("a cotton loincloth"));
			p1.setDisplayText(L("a cotton loincloth lies here"));
			p1.setDescription(L("Looks like little more than some rags pinned together at the corners."));
			p1.text();
			outfitChoices.add(p1);
			final Armor s3=CMClass.getArmor("GenBelt");
			outfitChoices.add(s3);
		}
		return outfitChoices;
	}

	@Override
	public List<RawMaterial> myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				resources.addElement(makeResource
				(L("a pair of @x1 ears",name().toLowerCase()),RawMaterial.RESOURCE_MEAT));
				resources.addElement(makeResource
				(L("some @x1 blood",name().toLowerCase()),RawMaterial.RESOURCE_BLOOD));
				resources.addElement(makeResource
				(L("a pile of @x1 bones",name().toLowerCase()),RawMaterial.RESOURCE_BONE));
			}
		}
		return resources;
	}
}
