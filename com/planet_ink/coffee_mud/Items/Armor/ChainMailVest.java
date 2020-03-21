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
public class ChainMailVest extends StdArmor
{
	@Override
	public String ID()
	{
		return "ChainMailVest";
	}

	public ChainMailVest()
	{
		super();

		setName("a chain mail vest");
		setDisplayText("a chain mail vest sits here.");
		setDescription("This is fairly solid looking vest made of chain mail.");
		properWornBitmap=Wearable.WORN_TORSO;
		wornLogicalAnd=false;
		basePhyStats().setArmor(25);
		basePhyStats().setWeight(30);
		basePhyStats().setAbility(0);
		baseGoldValue=75;
		recoverPhyStats();
		material=RawMaterial.RESOURCE_STEEL;
	}

}
