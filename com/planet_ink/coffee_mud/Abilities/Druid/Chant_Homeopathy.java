/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Abilities.interfaces.DiseaseAffect;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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

public class Chant_Homeopathy extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_Homeopathy";
	}

	private final static String localizedName = CMLib.lang().L("Homeopathy");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String localizedStaticDisplay = CMLib.lang().L("(Homeopathy)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	protected int canTargetCode()
	{
		return CAN_MOBS;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_PRESERVING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_OTHERS;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?L("Something is happening to <T-NAME>!"):L("^S<S-NAME> chant(s) homeopathically to <T-NAME>^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				Ability D=null;
				for(int t=0;t<target.numEffects();t++) // personal effects
				{
					final Ability A=target.fetchEffect(t);
					if((A!=null)&&(A instanceof DiseaseAffect))
						D=A;
				}
				final int roll=CMLib.dice().rollPercentage();
				if((roll>66)||(D==null))
					mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("<S-YOUPOSS> condition is unchanged."));
				else
				if(roll>33)
				{
					mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("<S-NAME> glow(s) a bit."));
					D.unInvoke();
				}
				else
				{
					mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("Something is definitely happening to <S-NAME>!"));
					for(int i=0;i<1000;i++)
						if(!D.tick(target,Tickable.TICKID_MOB))
							break;
				}
			}
		}
		else
			beneficialVisualFizzle(mob,null,L("<S-NAME> chant(s) to <T-NAMESELF>, but nothing happens."));

		return success;
	}
}
