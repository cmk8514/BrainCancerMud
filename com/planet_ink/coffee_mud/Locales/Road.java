package com.planet_ink.coffee_mud.Locales;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;

/*
   Copyright 2003-2017 Bo Zimmerman

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
public class Road extends StdRoom
{
	@Override
	public String ID()
	{
		return "Road";
	}

	public Road()
	{
		super();
		name="a road";
		basePhyStats.setWeight(1);
		recoverPhyStats();
	}

	@Override
	public int domainType()
	{
		return Room.DOMAIN_OUTDOORS_PLAINS;
	}

	public static final Integer[] resourceList={
		Integer.valueOf(RawMaterial.RESOURCE_STONE),
		Integer.valueOf(RawMaterial.RESOURCE_FEATHERS),
		Integer.valueOf(RawMaterial.RESOURCE_SCALES),
		Integer.valueOf(RawMaterial.RESOURCE_SAND),
		Integer.valueOf(RawMaterial.RESOURCE_CLAY),
	};
	public static final List<Integer> roomResources=new Vector<Integer>(Arrays.asList(resourceList));

	@Override
	public List<Integer> resourceChoices()
	{
		return Road.roomResources;
	}
}
