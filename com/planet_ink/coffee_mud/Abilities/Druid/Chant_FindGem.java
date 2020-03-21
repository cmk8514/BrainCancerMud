/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
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

public class Chant_FindGem extends Chant_FindPlant
{
	@Override
	public String ID()
	{
		return "Chant_FindGem";
	}

	private final static String localizedName = CMLib.lang().L("Find Gem");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String displayText()
	{
		return L("(Finding "+lookingFor+")");
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_ROCKCONTROL;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_SELF;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_TRACKING;
	}

	private final int[] myMats={RawMaterial.MATERIAL_PRECIOUS,
						  RawMaterial.MATERIAL_GLASS};

	@Override
	protected int[] okMaterials()
	{
		return myMats;
	}

	@Override
	protected int[] okResources()
	{
		return null;
	}

	public Chant_FindGem()
	{
		super();

		lookingFor = "gem";
	}
}
