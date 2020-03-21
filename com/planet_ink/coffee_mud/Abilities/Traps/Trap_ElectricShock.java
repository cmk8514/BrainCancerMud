package com.planet_ink.coffee_mud.Abilities.Traps;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Abilities.interfaces.Trap;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
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
public class Trap_ElectricShock extends StdTrap
{
	@Override
	public String ID()
	{
		return "Trap_ElectricShock";
	}

	private final static String	localizedName	= CMLib.lang().L("electric shock");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canAffectCode()
	{
		return Ability.CAN_ITEMS | Ability.CAN_EXITS;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	protected int trapLevel()
	{
		return 19;
	}

	@Override
	public String requiresToSet()
	{
		return "10 pounds of metal";
	}

	@Override
	public Trap setTrap(MOB mob, Physical P, int trapBonus, int qualifyingClassLevel, boolean perm)
	{
		if(P==null)
			return null;
		if(mob!=null)
		{
			final Item I=findMostOfMaterial(mob.location(),RawMaterial.MATERIAL_METAL);
			if(I!=null)
				super.destroyResources(mob.location(),I.material(),10);
		}
		return super.setTrap(mob,P,trapBonus,qualifyingClassLevel,perm);
	}

	@Override
	public List<Item> getTrapComponents()
	{
		final List<Item> V=new Vector<Item>();
		for(int i=0;i<10;i++)
			V.add(CMLib.materials().makeItemResource(RawMaterial.RESOURCE_IRON));
		return V;
	}

	@Override
	public boolean canSetTrapOn(MOB mob, Physical P)
	{
		if(!super.canSetTrapOn(mob,P))
			return false;
		if(mob!=null)
		{
			final Item I=findMostOfMaterial(mob.location(),RawMaterial.MATERIAL_METAL);
			if((I==null)
			||(super.findNumberOfResource(mob.location(),I.material())<10))
			{
				mob.tell(L("You'll need to set down at least 10 pounds of metal first."));
				return false;
			}
		}
		return true;
	}

	@Override
	public void spring(MOB target)
	{
		if((target!=invoker())&&(target.location()!=null))
		{
			if((!invoker().mayIFight(target))
			||(isLocalExempt(target))
			||(invoker().getGroupMembers(new HashSet<MOB>()).contains(target))
			||(target==invoker())
			||(doesSaveVsTraps(target)))
				target.location().show(target,null,null,CMMsg.MASK_ALWAYS|CMMsg.MSG_NOISE,L("<S-NAME> avoid(s) setting off a shocking trap!"));
			else
			if(target.location().show(target,target,this,CMMsg.MASK_ALWAYS|CMMsg.MSG_NOISE,L("<S-NAME> set(s) off an shocking trap!")))
			{
				super.spring(target);
				CMLib.combat().postDamage(invoker(),target,null,CMLib.dice().roll(trapLevel()+abilityCode(),8,1),CMMsg.MASK_ALWAYS|CMMsg.TYP_ELECTRIC,Weapon.TYPE_STRIKING,L("The shock <DAMAGES> <T-NAME>!@x1",CMLib.protocol().msp("shock.wav",30)));
				if((canBeUninvoked())&&(affected instanceof Item))
					disable();
			}
		}
	}
}
