/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Common;
import java.util.List;

import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2005-2017 Bo Zimmerman

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
public class Baking extends Cooking
{
	@Override
	public String ID()
	{
		return "Baking";
	}

	private final static String localizedName = CMLib.lang().L("Baking");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final String[] triggerStrings =I(new String[] {"BAKING","BAKE"});
	@Override
	public String supportedResourceString()
	{
		return "MISC";
	}

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public String cookWordShort()
	{
		return "bake";
	}

	@Override
	public String cookWord()
	{
		return "baking";
	}

	@Override
	public boolean honorHerbs()
	{
		return false;
	}

	@Override
	public boolean requireLid()
	{
		return true;
	}

	@Override
	public String parametersFile()
	{
		return "bake.txt";
	}

	@Override
	protected List<List<String>> loadRecipes()
	{
		return super.loadRecipes(parametersFile());
	}
}
