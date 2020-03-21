package com.planet_ink.coffee_mud.Items.Basic;
import java.util.Random;

import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.core.interfaces.SpaceObject;

/*
   Copyright 2014-2017 Bo Zimmerman

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
public class GasGiant extends GenSpaceBody
{
	@Override
	public String ID()
	{
		return "GasGiant";
	}
	
	public GasGiant()
	{
		super();
		setName("unknown gas giant");
		setDisplayText("an unknown gas giant is floating here");
		setDescription("it`s pretty");
		coordinates=new long[]{Math.round(Long.MAX_VALUE*Math.random()),Math.round(Long.MAX_VALUE*Math.random()),Math.round(Long.MAX_VALUE*Math.random())};
		Random random=new Random(System.currentTimeMillis());
		radius=SpaceObject.Distance.SaturnRadius.dm + (random.nextLong() % (SpaceObject.Distance.SaturnRadius.dm / 2));
		this.setMaterial(RawMaterial.RESOURCE_HYDROGEN);
	}
}
