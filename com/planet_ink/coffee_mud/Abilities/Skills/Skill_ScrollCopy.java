package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.Scroll;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMath;
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

public class Skill_ScrollCopy extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_ScrollCopy";
	}

	private final static String	localizedName	= CMLib.lang().L("Memorize");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	protected int canTargetCode()
	{
		return Ability.CAN_ITEMS;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	private static final String[]	triggerStrings	= I(new String[] { "MEMORIZE" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL | Ability.DOMAIN_CALLIGRAPHY;
	}

	@Override
	protected int overrideMana()
	{
		return 0;
	} // -1=normal, Ability.COST_ALL=all, Ability.COST_PCT

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{

		if(commands.size()<2)
		{
			mob.tell(L("Memorize what from what?"));
			return false;
		}
		final Item target=mob.fetchItem(null,Wearable.FILTER_UNWORNONLY,CMParms.combine(commands,1));
		if((target==null)||(!CMLib.flags().canBeSeenBy(target,mob)))
		{
			mob.tell(L("You don't see '@x1' here.",CMParms.combine(commands,1)));
			return false;
		}

		if(!(target instanceof Scroll))
		{
			mob.tell(L("You can't memorize from that."));
			return false;
		}

		if(((Scroll)target).usesRemaining()<1)
		{
			mob.tell(L("The scroll appears to be faded."));
			return false;
		}

		final List<Ability> theSpells=((Scroll)target).getSpells();
		Ability thisSpell=null;
		for(int a=0;a<theSpells.size();a++)
		{
			final Ability A=theSpells.get(a);
			if(CMLib.english().containsString(A.name(),(commands.get(0))))
			{
				thisSpell=A;
				break;
			}
		}

		if(thisSpell==null)
		{
			mob.tell(L("That is not written on @x1.",target.name(mob)));
			return false;
		}

		thisSpell=(Ability)thisSpell.copyOf();
		final MOB T=CMClass.getMOB("Teacher");
		T.setName(target.name());
		T.charStats().setStat(CharStats.STAT_GENDER,'N');
		T.delAllAbilities();
		thisSpell.setProficiency(50);
		T.addAbility(thisSpell);
		if(!thisSpell.canBeLearnedBy(T,mob))
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			if(mob.location().show(mob,target,this,CMMsg.MSG_HANDS,L("<S-NAME> memorize(s) '@x1' from <T-NAME>.",thisSpell.name())))
			{
				thisSpell.teach(T,mob);
				if((mob.fetchAbility(thisSpell.ID())!=null)
				&&(CMLib.ableMapper().qualifyingClassLevel(mob,this)>=0)
				&&(CMLib.ableMapper().qualifyingClassLevel(mob,thisSpell)>=0))
				{
					final int xp=(int)Math.round(100.0*CMath.div(CMLib.ableMapper().lowestQualifyingLevel(thisSpell.ID()),CMLib.ableMapper().qualifyingClassLevel(mob,this)));
					if(xp>=0)
						CMLib.leveler().postExperience(mob,null,null,xp,false);
				}
			}
		}
		else
			mob.location().show(mob,null,CMMsg.MSG_HANDS,L("<S-NAME> attempt(s) to memorize '@x1' from @x2, but fail(s).",thisSpell.name(),target.name()));
		return success;
	}

}
