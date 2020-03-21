/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Areas.interfaces.Area;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.Climate;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMath;
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

public class Chant_SummonLightning extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_SummonLightning";
	}

	@Override
	public String name()
	{
		return renderedMundane?"lightning":"Summon Lightning";
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_MALICIOUS;
	}

	@Override
	public int maxRange()
	{
		return adjustedMaxInvokerRange(10);
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
		return Ability.ACODE_CHANT|Ability.DOMAIN_WEATHER_MASTERY;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_WEATHERAFFECTING|Ability.FLAG_AIRBASED;
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		 if(mob!=null)
		 {
			 final Room R=mob.location();
			 if(R!=null)
			 {
				 if((R.domainType()&Room.INDOORS)>0)
					 return Ability.QUALITY_INDIFFERENT;
				 final Area A=R.getArea();
				 if(A.getClimateObj().weatherType(mob.location())!=Climate.WEATHER_THUNDERSTORM)
					 return Ability.QUALITY_INDIFFERENT;
			 }
		 }
		 return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(((mob.location().domainType()&Room.INDOORS)>0)&&(!auto))
		{
			mob.tell(L("You must be outdoors for this chant to work."));
			return false;
		}
		if(mob.location().getArea().getClimateObj().weatherType(mob.location())!=Climate.WEATHER_THUNDERSTORM)
		{
			mob.tell(L("This chant requires a thunderstorm!"));
			return false;
		}
		final MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),L(auto?"^JA lightning bolt streaks out of the sky!^?":"^S<S-NAME> chant(s) to <T-NAMESELF>.  Suddenly a lightning bolt streaks from the sky!^?")+CMLib.protocol().msp("lightning.wav",40));
			final CMMsg msg2=CMClass.getMsg(mob,target,this,verbalCastMask(mob,target,auto)|CMMsg.TYP_ELECTRIC,null);
			if((mob.location().okMessage(mob,msg))&&((mob.location().okMessage(mob,msg2))))
			{
				mob.location().send(mob,msg);
				mob.location().send(mob,msg2);
				final int maxDie =  adjustedLevel( mob, asLevel ) + ( 2 * super.getX1Level( mob ) );
				int damage = CMLib.dice().roll(maxDie,8,maxDie);
				if((msg.value()>0)||(msg2.value()>0))
					damage = (int)Math.round(CMath.div(damage,2.0));
				if(target.location()==mob.location())
					CMLib.combat().postDamage(mob,target,this,damage,CMMsg.MASK_ALWAYS|CMMsg.TYP_ELECTRIC,Weapon.TYPE_STRIKING,L("The bolt <DAMAGE> <T-NAME>!"));
			}
		}
		else
			return maliciousFizzle(mob,target,L("<S-NAME> chant(s) at <T-NAMESELF>, but the magic fades."));

		// return whether it worked
		return success;
	}
}
