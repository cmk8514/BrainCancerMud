package com.planet_ink.coffee_mud.Locales;
import java.util.List;

import com.planet_ink.coffee_mud.Locales.interfaces.Room;

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
public class WoodsGrid extends StdGrid
{
	@Override
	public String ID()
	{
		return "WoodsGrid";
	}

	public WoodsGrid()
	{
		super();
		basePhyStats.setWeight(3);
		recoverPhyStats();
	}

	@Override
	public int domainType()
	{
		return Room.DOMAIN_OUTDOORS_WOODS;
	}

	@Override
	public String getGridChildLocaleID()
	{
		return "Woods";
	}

	@Override
	public List<Integer> resourceChoices()
	{
		return Woods.roomResources;
	}
}
