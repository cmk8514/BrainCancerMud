package com.planet_ink.coffee_mud.Abilities.Specializations;
import com.planet_ink.coffee_mud.Items.interfaces.AmmunitionWeapon;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2014-2017 Bo Zimmerman

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
public class Specialization_Bow extends Specialization_Weapon
{
	@Override
	public String ID()
	{
		return "Specialization_Bow";
	}

	private final static String	localizedName	= CMLib.lang().L("Bow Specialization");

	@Override
	public String name()
	{
		return localizedName;
	}

	public Specialization_Bow()
	{
		super();
		weaponClass=Weapon.CLASS_RANGED;
		secondWeaponClass=-1;
	}

	@Override
	protected boolean isWeaponMatch(Weapon W)
	{
		if(!super.isWeaponMatch(W))
			return false;
		if(!(W instanceof AmmunitionWeapon))
			return false;
		final String ammo=((AmmunitionWeapon)W).ammunitionType();
		return (ammo!=null) && (ammo.equalsIgnoreCase("arrow")||ammo.equalsIgnoreCase("arrows"));
	}
}
