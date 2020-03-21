package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;

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
public class TwoHandedSword extends Sword
{
	@Override
	public String ID()
	{
		return "TwoHandedSword";
	}

	public TwoHandedSword()
	{
		super();

		setName("a two-handed sword");
		setDisplayText("a heavy two-handed sword hangs on the wall.");
		setDescription("It has a metallic pommel, and a very large blade.");
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(0);
		basePhyStats.setWeight(15);
		basePhyStats().setAttackAdjustment(0);
		basePhyStats().setDamage(10);
		baseGoldValue=50;
		wornLogicalAnd=true;
		properWornBitmap=Wearable.WORN_HELD|Wearable.WORN_WIELD;
		recoverPhyStats();
		material=RawMaterial.RESOURCE_STEEL;
		weaponDamageType=TYPE_SLASHING;
	}

}
