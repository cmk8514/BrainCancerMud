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
public class Ration extends StdFood
{
	@Override
	public String ID()
	{
		return "Ration";
	}

	public Ration()
	{
		super();
		setName("a ration pack");
		basePhyStats.setWeight(10);
		amountOfNourishment=500;
		setDisplayText("a standard ration pack sits here.");
		setDescription("Bits of salt dried meat, dried fruit, and hard bread.");
		baseGoldValue=15;
		setMaterial(RawMaterial.RESOURCE_MEAT);
		recoverPhyStats();
	}
}
