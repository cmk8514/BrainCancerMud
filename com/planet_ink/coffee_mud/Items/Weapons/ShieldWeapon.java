package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Shield;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;

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
public class ShieldWeapon extends StdWeapon implements Shield
{
	@Override
	public String ID()
	{
		return "ShieldWeapon";
	}

	public ShieldWeapon()
	{
		super();

		setName("a bashing shield");
		setDisplayText("A bashing shield has been left here.");
		setDescription("Looks like natural fighting ability.");
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(0);
		basePhyStats().setWeight(0);
		basePhyStats().setAttackAdjustment(0);
		basePhyStats().setDamage(1);
		weaponDamageType=Weapon.TYPE_BASHING;
		material=RawMaterial.RESOURCE_STEEL;
		weaponClassification=Weapon.CLASS_BLUNT;
		recoverPhyStats();
	}

	public void setShield(Item shield)
	{
		name=shield.name();
		displayText=shield.displayText();
		miscText="";
		setDescription(shield.description());
		basePhyStats().setDamage(shield.phyStats().level());
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(0);
		basePhyStats().setWeight(0);
		basePhyStats().setAttackAdjustment(0);
		weaponDamageType=Weapon.TYPE_BASHING;
		recoverPhyStats();
	}

	public ShieldWeapon(Item shield)
	{
		super();

		setShield(shield);
	}

}
