package com.planet_ink.coffee_mud.Abilities.Skills;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.Shield;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.Races.interfaces.Race;
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

public class Skill_Bash extends StdSkill
{
	@Override
	public String ID()
	{
		return "Skill_Bash";
	}

	private final static String localizedName = CMLib.lang().L("Shield Bash");

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
		return CAN_MOBS;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_MALICIOUS;
	}

	private static final String[] triggerStrings =I(new String[] {"BASH"});
	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SKILL|Ability.DOMAIN_SHIELDUSE;
	}

	@Override
	public int usageType()
	{
		return USAGE_MOVEMENT;
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if((mob!=null)&&(target!=null))
		{
			final Item thisShield=getShield(mob);
			if(thisShield==null)
				return Ability.QUALITY_INDIFFERENT;
			if((CMLib.flags().isSitting(target)||CMLib.flags().isSleeping(target)))
				return Ability.QUALITY_INDIFFERENT;
		}
		return super.castingQuality(mob,target);
	}

	public Item getShield(MOB mob)
	{
		Item thisShield=null;
		for(int i=0;i<mob.numItems();i++)
		{
			final Item I=mob.getItem(i);
			if((I!=null)&&(I instanceof Shield)&&(!I.amWearingAt(Wearable.IN_INVENTORY)))
			{
				thisShield=I;
				break;
			}
		}
		return thisShield;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		final MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null)
			return false;
		final Item thisShield=getShield(mob);
		if(thisShield==null)
		{
			mob.tell(L("You must have a shield to perform a bash."));
			return false;
		}

		if((CMLib.flags().isSitting(target)||CMLib.flags().isSleeping(target)))
		{
			mob.tell(L("@x1 must stand up first!",target.name(mob)));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		String str=null;
		if(success)
		{
			str=auto?L("<T-NAME> is bashed!"):L("^F^<FIGHT^><S-NAME> bash(es) <T-NAMESELF> with @x1!^</FIGHT^>^?",thisShield.name());
			final CMMsg msg=CMClass.getMsg(mob,target,this,CMMsg.MSK_MALICIOUS_MOVE|CMMsg.TYP_JUSTICE|(auto?CMMsg.MASK_ALWAYS:0),str);
			CMLib.color().fixSourceFightColor(msg);
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Weapon w=CMClass.getWeapon("ShieldWeapon");
				if(w!=null)
				{
					w.setName(thisShield.name());
					w.setDisplayText(thisShield.displayText());
					w.setDescription(thisShield.description());
					w.basePhyStats().setDamage(thisShield.phyStats().level()+(2*getXLEVELLevel(mob)));
					if((CMLib.combat().postAttack(mob,target,w))
					&&(target.charStats().getBodyPart(Race.BODY_LEG)>0)
					&&(target.phyStats().weight()<(mob.phyStats().weight()*2)))
					{
						target.basePhyStats().setDisposition(target.basePhyStats().disposition()|PhyStats.IS_SITTING);
						target.recoverPhyStats();
					}
				}
			}
		}
		else
			return maliciousFizzle(mob,target,L("<S-NAME> attempt(s) to shield bash <T-NAMESELF>, but end(s) up looking silly."));

		return success;
	}

}
