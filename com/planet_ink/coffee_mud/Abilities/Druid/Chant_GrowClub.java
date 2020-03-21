/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Druid;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.ItemPossessor;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2003-2017 Bo Zimmerman

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

public class Chant_GrowClub extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_GrowClub";
	}

	private final static String localizedName = CMLib.lang().L("Grow Club");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_PLANTGROWTH;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_OK_SELF;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int castingQuality(MOB mob, Physical target)
	{
		if(mob!=null)
		{
			if((mob.isInCombat())&&(mob.fetchWieldedItem()==null))
			{
				final Room R=mob.location();
				if((R!=null)
				&&(R.findItem(null,"club")==null)
				&&((R.domainType()==Room.DOMAIN_OUTDOORS_WOODS)
				||((R.myResource()&RawMaterial.MATERIAL_MASK)==RawMaterial.MATERIAL_WOODEN)
				||(R.domainType()==Room.DOMAIN_OUTDOORS_JUNGLE)))
					return super.castingQuality(mob, target,Ability.QUALITY_BENEFICIAL_SELF);
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if((mob.location().domainType()!=Room.DOMAIN_OUTDOORS_WOODS)
		&&((mob.location().myResource()&RawMaterial.MATERIAL_MASK)!=RawMaterial.MATERIAL_WOODEN)
		&&(mob.location().domainType()!=Room.DOMAIN_OUTDOORS_JUNGLE))
		{
			mob.tell(L("This magic will not work here."));
			return false;
		}
		int material=RawMaterial.RESOURCE_OAK;
		if((mob.location().myResource()&RawMaterial.MATERIAL_MASK)==RawMaterial.MATERIAL_WOODEN)
			material=mob.location().myResource();
		else
		{
			final List<Integer> V=mob.location().resourceChoices();
			final Vector<Integer> V2=new Vector<Integer>();
			if(V!=null)
			for(int v=0;v<V.size();v++)
			{
				if((V.get(v).intValue()&RawMaterial.MATERIAL_MASK)==RawMaterial.MATERIAL_WOODEN)
					V2.addElement(V.get(v));
			}
			if(V2.size()>0)
				material=V2.elementAt(CMLib.dice().roll(1,V2.size(),-1)).intValue();
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		// now see if it worked
		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,null,this,verbalCastCode(mob,null,auto),auto?"":L("^S<S-NAME> chant(s) to the trees.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Weapon newItem=CMClass.getWeapon("GenWeapon");
				newItem.setName(L("@x1 club",RawMaterial.CODES.NAME(material).toLowerCase()));
				newItem.setName(CMLib.english().startWithAorAn(newItem.Name()));
				newItem.setDisplayText(L("@x1 sits here",newItem.name()));
				newItem.setDescription(L("It looks like the limb of a tree."));
				newItem.setMaterial(material);
				newItem.basePhyStats().setWeight(10);
				final int level=mob.phyStats().level();
				newItem.basePhyStats().setLevel(level);
				newItem.basePhyStats().setAttackAdjustment(0);
				int damage=6;
				try
				{
					 damage=(((level+(2*getXLEVELLevel(mob)))-1)/2)+2;
				}
				catch(final Exception t)
				{
				}
				if(damage<6)
					damage=6;
				newItem.basePhyStats().setDamage(damage+super.getX1Level(mob));
				newItem.recoverPhyStats();
				newItem.setBaseValue(0);
				newItem.setWeaponClassification(Weapon.CLASS_BLUNT);
				newItem.setWeaponDamageType(Weapon.TYPE_BASHING);
				newItem.setMiscText(newItem.text());
				mob.location().addItem(newItem,ItemPossessor.Expire.Resource);
				mob.location().showHappens(CMMsg.MSG_OK_ACTION,L("A good looking club grows out of a tree and drops."));
				mob.location().recoverPhyStats();
			}
		}
		else
			return beneficialWordsFizzle(mob,null,L("<S-NAME> chant(s) to the trees, but nothing happens."));

		// return whether it worked
		return success;
	}
}
