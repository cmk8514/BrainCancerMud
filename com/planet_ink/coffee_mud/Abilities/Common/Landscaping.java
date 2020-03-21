/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Common;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
/*
   Copyright 2016-2017 Bo Zimmerman

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

public class Landscaping extends BuildingSkill
{
	@Override
	public String ID()
	{
		return "Landscaping";
	}

	private final static String	localizedName	= CMLib.lang().L("Landscaping");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final String[]	triggerStrings	= I(new String[] { "LANDSCAPE", "LANDSCAPING" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public String supportedResourceString()
	{
		return "ROCK|STONE";
	}

	@Override
	public String parametersFile()
	{
		return "landscaping.txt";
	}

	@Override
	protected String getMainResourceName()
	{
		return "Material";
	}

	@Override
	protected String getSoundName()
	{
		return "stone.wav";
	}

	public Landscaping()
	{
		super();
	}

	@Override
	protected boolean canDescTitleHere(final Room R)
	{
		return (R!=null)
				&&((R.domainType()&Room.INDOORS)==0)
				&&(R.domainType()!=Room.DOMAIN_OUTDOORS_UNDERWATER);
	}

	@Override
	protected int[][] getBasicMaterials(final MOB mob, int woodRequired, String miscType)
	{
		if(miscType.length()==0)
			miscType="rock";
		final int[][] idata=fetchFoundResourceData(mob,
													woodRequired,miscType,null,
													0,null,null,
													false,
													0,null);
		return idata;
	}
}
