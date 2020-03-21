/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Common;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2013-2017 Bo Zimmerman

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

public class MasterShearing extends Shearing
{
	@Override
	public String ID()
	{
		return "MasterShearing";
	}

	private final static String	localizedName	= CMLib.lang().L("Master Shearing");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final String[]	triggerStrings	= I(new String[] { "MSHEAR", "MSHEARING", "MASTERSHEAR", "MASTERSHEARING" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	protected int getDuration(MOB mob, int weight)
	{
		int duration=((weight/(10+getXLEVELLevel(mob))))*2;
		duration = super.getDuration(duration, mob, 1, 25);
		if(duration>100)
			duration=100;
		return duration;
	}

	@Override
	protected int baseYield()
	{
		return 3;
	}
}
