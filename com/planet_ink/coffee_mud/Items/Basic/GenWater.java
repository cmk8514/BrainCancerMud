package com.planet_ink.coffee_mud.Items.Basic;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;

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
public class GenWater extends GenDrink
{
	@Override
	public String ID()
	{
		return "GenWater";
	}

	public GenWater()
	{
		super();
		readableText = "";

		setName("a generic puddle of water");
		basePhyStats.setWeight(2);
		setDisplayText("a generic puddle of water sits here.");
		setDescription("");
		baseGoldValue=0;
		capacity=0;
		amountOfThirstQuenched=250;
		amountOfLiquidHeld=10000;
		amountOfLiquidRemaining=10000;
		setMaterial(RawMaterial.RESOURCE_FRESHWATER);
		basePhyStats().setSensesMask(PhyStats.SENSE_ITEMNOTGET);
		recoverPhyStats();
	}

}
