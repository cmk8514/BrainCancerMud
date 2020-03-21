/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Common;
import com.planet_ink.coffee_mud.core.*;
import java.util.*;

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
public class FoodPrep extends Cooking
{
	@Override
	public String ID()
	{
		return "FoodPrep";
	}

	private final static String	localizedName	= CMLib.lang().L("Food Prep");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final String[]	triggerStrings	= I(new String[] { "FOODPREPPING", "FPREP" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public String cookWordShort()
	{
		return "make";
	}

	@Override
	public String cookWord()
	{
		return "making";
	}

	@Override
	public boolean honorHerbs()
	{
		return false;
	}

	@Override
	public boolean requireFire()
	{
		return false;
	}

	@Override
	public String parametersFile()
	{
		return "foodprep.txt";
	}

	@Override
	protected List<List<String>> loadRecipes()
	{
		return super.loadRecipes(parametersFile());
	}

	public FoodPrep()
	{
		super();

		defaultFoodSound = "chopchop.wav";
	}

}
