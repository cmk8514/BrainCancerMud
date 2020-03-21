package com.planet_ink.coffee_mud.Items.Basic;
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
public class OilFlask extends StdDrink
{
	@Override
	public String ID()
	{
		return "OilFlask";
	}

	public OilFlask()
	{
		super();
		setName("an oil flask");
		basePhyStats.setWeight(3);
		capacity=0;
		setMaterial(RawMaterial.RESOURCE_GLASS);
		setDisplayText("an oil flask sits here.");
		setDescription("A small glass flask containing lamp oil, with a lid.");
		baseGoldValue=5;
		amountOfLiquidHeld=5;
		disappearsAfterDrinking=true;
		amountOfLiquidRemaining=5;
		liquidType=RawMaterial.RESOURCE_LAMPOIL;
		recoverPhyStats();
	}

}
