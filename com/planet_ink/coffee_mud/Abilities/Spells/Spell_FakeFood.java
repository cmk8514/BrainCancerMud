package com.planet_ink.coffee_mud.Abilities.Spells;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Food;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.ItemPossessor;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2002-2017 Bo Zimmerman

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

public class Spell_FakeFood extends Spell
{

	@Override
	public String ID()
	{
		return "Spell_FakeFood";
	}

	private final static String localizedName = CMLib.lang().L("Fake Food");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL|Ability.DOMAIN_ILLUSION;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,null,this,verbalCastCode(mob,null,auto),auto?"":L("^S<S-NAME> invoke(s) a spell dramatically.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Food F=(Food)CMClass.getItem("GenFood");
				switch(CMLib.dice().roll(1,5,0))
				{
				case 1: F.setName(L("a shiny apple"));
						F.setDisplayText(L("A shiny red apple sits here."));
						F.setDescription(L("It looks tasty and crisp!"));
						break;
				case 2: F.setName(L("a nice peach"));
						F.setDisplayText(L("A nice peach sits here."));
						F.setDescription(L("It looks tasty!"));
						break;
				case 3: F.setName(L("a big pot pie"));
						F.setDisplayText(L("A big pot pie has been left here."));
						F.setDescription(L("It sure looks good!"));
						break;
				case 4: F.setName(L("a juicy steak"));
						F.setDisplayText(L("A juicy steak has been left here."));
						F.setDescription(L("It sure looks good!"));
						break;
				case 5: F.setName(L("a bit of food"));
						F.setDisplayText(L("A bit of food has been left here."));
						F.setDescription(L("It sure looks good!"));
						break;
				}
				F.setNourishment(0);
				F.setBaseValue(0);
				for(int f=0;f<5;f++)
				{
					final Food F2=(Food)F.copyOf();
					F2.recoverPhyStats();
					mob.location().addItem(F2,ItemPossessor.Expire.Resource);
					mob.location().show(mob,null,F2,CMMsg.MSG_OK_VISUAL,L("<O-NAME> appears!"));
				}
			}
		}
		else
			beneficialVisualFizzle(mob,null,L("<S-NAME> dramatically attempt(s) to invoke a spell, but fizzle(s) the spell."));

		// return whether it worked
		return success;
	}
}
