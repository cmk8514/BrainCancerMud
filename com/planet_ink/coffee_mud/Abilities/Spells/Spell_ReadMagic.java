package com.planet_ink.coffee_mud.Abilities.Spells;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

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

public class Spell_ReadMagic extends Spell
{

	@Override
	public String ID()
	{
		return "Spell_ReadMagic";
	}

	private final static String	localizedName	= CMLib.lang().L("Read Magic");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String	localizedStaticDisplay	= CMLib.lang().L("(Ability to read magic)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	protected int canTargetCode()
	{
		return CAN_ITEMS;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL | Ability.DOMAIN_DIVINATION;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		// first, using the commands vector, determine
		// the target of the spell.  If no target is specified,
		// the system will assume your combat target.
		final Physical target=getTarget(mob,null,givenTarget,commands,Wearable.FILTER_ANY);
		if(target==null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if((success)&&(mob.fetchEffect(this.ID())==null))
		{
			final Ability thisNewOne=(Ability)this.copyOf();
			mob.addEffect(thisNewOne);
			CMLib.commands().postRead(mob, target, "", false);
			mob.delEffect(thisNewOne);
		}
		else
			return beneficialWordsFizzle(mob,target,L("<S-NAME> incant(s) and gaze(s) over <T-NAMESELF>, but nothing more happens."));

		// return whether it worked
		return success;
	}
}
