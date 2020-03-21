/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Common;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMath;

/*
   Copyright 2012-2017 Bo Zimmerman

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
public class SpellCraftingSkill extends CraftingSkill
{
	@Override
	public String ID()
	{
		return "SpellCraftingSkill";
	}

	private final static String	localizedName	= CMLib.lang().L("Spell Crafting Skill");

	@Override
	public String name()
	{
		return localizedName;
	}

	public SpellCraftingSkill()
	{
		super();
	}

	protected String getCraftableSpellName(List<String> commands)
	{
		String spellName=null;
		if((commands.size()>0))
			spellName=CMParms.combine(commands,0);
		else
		{
			final List<List<String>> recipes=loadRecipes();
			final List<String> V=recipes.get(CMLib.dice().roll(1,recipes.size(),-1));
			spellName=V.get(RCP_FINALNAME);
		}
		return spellName;
	}

	protected List<String> getCraftableSpellRow(String spellName)
	{
		List<String> spellFound=null;
		final List<List<String>> recipes=loadRecipes();
		for(final List<String> V : recipes)
		{
			if (V.get(RCP_FINALNAME).equalsIgnoreCase(spellName))
			{
				spellFound = V;
				break;
			}
		}
		if (spellFound == null)
		{
			for (final List<String> V : recipes)
			{
				if (CMLib.english().containsString(V.get(RCP_FINALNAME), spellName))
				{
					spellFound = V;
					break;
				}
			}
		}
		if (spellFound == null)
		{
			for (final List<String> V : recipes)
			{
				if (V.get(RCP_FINALNAME).toLowerCase().indexOf(spellName.toLowerCase()) >= 0)
				{
					spellFound = V;
					break;
				}
			}
		}
		return spellFound;
	}

	protected Ability getCraftableSpellRecipeSpell(List<String> commands)
	{
		Ability theSpell=null;
		final String spellName=getCraftableSpellName(commands);
		if(spellName!=null)
		{
			theSpell=CMClass.getAbility(commands.get(0));
			if(theSpell==null)
			{
				final List<String> spellFound=getCraftableSpellRow(spellName);
				if(spellFound!=null)
					theSpell=CMClass.getAbility(spellFound.get(RCP_FINALNAME));
			}
		}
		return theSpell;
	}

	protected int getCraftableSpellLevel(List<String> commands)
	{
		Ability theSpell=null;
		final String spellName=getCraftableSpellName(commands);
		if(spellName!=null)
		{
			final List<String> spellFound=getCraftableSpellRow(spellName);
			if(spellFound!=null)
				return CMath.s_int(spellFound.get(RCP_LEVEL));
			theSpell=CMClass.getAbility(commands.get(0));
			if(theSpell!=null)
				return CMLib.ableMapper().lowestQualifyingLevel(theSpell.ID());
		}
		return -1;
	}
}
