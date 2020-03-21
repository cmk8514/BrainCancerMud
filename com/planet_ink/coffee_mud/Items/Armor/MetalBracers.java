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
public class MetalBracers extends StdArmor
{
	@Override
	public String ID()
	{
		return "MetalBracers";
	}

	public MetalBracers()
	{
		super();

		setName("a metal bracer");
		setDisplayText("a metal bracer lies here.");
		setDescription("Good and solid protection for your lower arms.");
		properWornBitmap=Wearable.WORN_LEFT_WRIST | Wearable.WORN_RIGHT_WRIST;
		wornLogicalAnd=false;
		basePhyStats().setArmor(2);
		basePhyStats().setWeight(5);
		basePhyStats().setAbility(0);
		baseGoldValue=10;
		recoverPhyStats();
		material=RawMaterial.RESOURCE_STEEL;
	}

}
