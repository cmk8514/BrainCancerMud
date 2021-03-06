package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;

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
public class Trident extends StdWeapon
{
	@Override
	public String ID()
	{
		return "Trident";
	}

	public Trident()
	{
		super();

		setName("a trident");
		setDisplayText("a trident is on the ground.");
		setDescription("It`s a polearm with a three sharp prongs.");

		basePhyStats().setAbility(0);
		basePhyStats().setLevel(1);
		basePhyStats.setWeight(10);
		basePhyStats().setAttackAdjustment(0);
		basePhyStats().setDamage(10);
		baseGoldValue=10;
		recoverPhyStats();
		wornLogicalAnd=true;
		properWornBitmap=Wearable.WORN_HELD|Wearable.WORN_WIELD;
		weaponDamageType=TYPE_PIERCING;
		material=RawMaterial.RESOURCE_STEEL;
		weaponClassification=Weapon.CLASS_POLEARM;
	}
}
