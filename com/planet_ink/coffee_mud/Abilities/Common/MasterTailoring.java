/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Common;
import java.util.List;

import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2004-2017 Tim Kassebaum

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
public class MasterTailoring extends Tailoring
{
	@Override
	public String ID()
	{
		return "MasterTailoring";
	}

	private final static String	localizedName	= CMLib.lang().L("Master Tailoring");

	@Override
	public String name()
	{
		return localizedName;
	}

	private static final String[]	triggerStrings	= I(new String[] { "MASTERKNIT", "MKNIT", "MTAILOR", "MTAILORING", "MASTERTAILOR", "MASTERTAILORING" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public String parametersFile()
	{
		return "mastertailor.txt";
	}

	@Override
	protected boolean masterCraftCheck(final Item I)
	{
		if(I.name().toUpperCase().startsWith("DESIGNER")||(I.name().toUpperCase().indexOf(" DESIGNER ")>0))
			return true;
		if(I.basePhyStats().level()<31)
			return false;
		return true;
	}

	@Override
	protected boolean autoGenInvoke(final MOB mob, List<String> commands, Physical givenTarget, final boolean auto, 
			 					 final int asLevel, int autoGenerate, boolean forceLevels, List<Item> crafted)
	{
		if(super.checkStop(mob, commands))
			return true;

		if(super.checkInfo(mob, commands))
			return true;
		
		randomRecipeFix(mob,addRecipes(mob,loadRecipes()),commands,autoGenerate);
		if(commands.size()==0)
		{
			commonTell(mob,L("Knit what? Enter \"mknit list\" for a list, \"mknit info <item>\", \"mknit refit <item>\" to resize,"
						+ " \"mknit learn <item>\", \"mknit scan\", \"mknit mend <item>\", or \"mknit stop\" to cancel."));
			return false;
		}
		return super.autoGenInvoke(mob,commands,givenTarget,auto,asLevel,autoGenerate,forceLevels,crafted);
	}
}

