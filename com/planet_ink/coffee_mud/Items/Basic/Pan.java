package com.planet_ink.coffee_mud.Items.Basic;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;

/*
   Copyright 2004-2017 Bo Zimmerman

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
public class Pan extends StdDrink
{
	@Override
	public String ID()
	{
		return "Pan";
	}

	public Pan()
	{
		super();
		setName("a pan");
		setDisplayText("an iron pan sits here.");
		setDescription("A sturdy iron pan for cooking in.");
		capacity=25;
		baseGoldValue=5;
		setMaterial(RawMaterial.RESOURCE_IRON);
		basePhyStats().setWeight(5);
		recoverPhyStats();
	}

}
