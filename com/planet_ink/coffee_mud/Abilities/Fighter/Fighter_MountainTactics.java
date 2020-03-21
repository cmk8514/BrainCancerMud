/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Fighter;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.core.CMLib;

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

public class Fighter_MountainTactics extends Fighter_FieldTactics
{
	@Override
	public String ID()
	{
		return "Fighter_MountainTactics";
	}

	private final static String localizedName = CMLib.lang().L("Mountain Tactics");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final Integer[] landClasses = {Integer.valueOf(Room.DOMAIN_OUTDOORS_MOUNTAINS)};
	@Override
	public Integer[] landClasses()
	{
		return landClasses;
	}
}
