package com.planet_ink.coffee_mud.Abilities.Prayers;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Abilities.interfaces.MendingSkill;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
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

public class Prayer_CureCritical extends Prayer implements MendingSkill
{
	@Override
	public String ID()
	{
		return "Prayer_CureCritical";
	}

	private final static String localizedName = CMLib.lang().L("Cure Critical Wounds");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_BENEFICIAL_OTHERS;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_PRAYER|Ability.DOMAIN_HEALING;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_HOLY|Ability.FLAG_HEALINGMAGIC;
	}

	@Override
	public boolean supportsMending(Physical item)
	{
		return (item instanceof MOB)
				&&((((MOB)item).curState()).getHitPoints()<(((MOB)item).maxState()).getHitPoints());
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if(mob!=null)
		{
			if(target instanceof MOB)
			{
				if(!supportsMending(target))
					return Ability.QUALITY_INDIFFERENT;
				if(CMLib.flags().isUndead((MOB)target))
					return Ability.QUALITY_MALICIOUS;
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;
		final boolean undead=CMLib.flags().isUndead(target);

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,(!undead?0:CMMsg.MASK_MALICIOUS)|verbalCastCode(mob,target,auto),auto?L("A bright white glow surrounds <T-NAME>."):L("^S<S-NAME> @x1, delivering a critical healing touch to <T-NAMESELF>.^?",prayWord(mob)));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final int healing=CMLib.dice().roll(4,adjustedLevel(mob,asLevel),6);
				final int oldHP=target.curState().getHitPoints();
				CMLib.combat().postHealing(mob,target,this,healing,CMMsg.MASK_ALWAYS|CMMsg.TYP_CAST_SPELL,null);
				if(target.curState().getHitPoints()>oldHP)
					target.tell(L("You feel much better!"));
			}
		}
		else
			beneficialWordsFizzle(mob,target,auto?"":L("<S-NAME> @x1 for <T-NAMESELF>, but nothing happens.",prayWord(mob)));

		// return whether it worked
		return success;
	}
}
