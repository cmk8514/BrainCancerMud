package com.planet_ink.coffee_mud.Items.Armor;
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
public class SteelGauntlets extends StdArmor
{
	@Override
	public String ID()
	{
		return "SteelGauntlets";
	}

	public SteelGauntlets()
	{
		super();

		setName("some steel gauntlets");
		setDisplayText("a pair of steel gauntlets sit here.");
		setDescription("They look like they're made of steel.");
		properWornBitmap=Wearable.WORN_HANDS | Wearable.WORN_LEFT_WRIST | Wearable.WORN_RIGHT_WRIST;
		wornLogicalAnd=true;
		basePhyStats().setArmor(3); // = $$$$ =
		basePhyStats().setAbility(0);
		basePhyStats().setWeight(5);
		baseGoldValue=20;
		recoverPhyStats();
		material=RawMaterial.RESOURCE_STEEL;
	}

}
