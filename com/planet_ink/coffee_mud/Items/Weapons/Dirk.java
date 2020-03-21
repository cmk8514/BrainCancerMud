package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;

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
public class Dirk extends Dagger
{
	@Override
	public String ID()
	{
		return "Dirk";
	}

	public Dirk()
	{
		super();

		setName("a dirk");
		setDisplayText("a pointy dirk is on the ground.");
		setDescription("The dirk is a single-edged, grooved weapon with a back edge near the point. ");
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(0);
		basePhyStats.setWeight(1);
		baseGoldValue=2;
		basePhyStats().setAttackAdjustment(0);
		basePhyStats().setDamage(4);
		weaponDamageType=TYPE_PIERCING;
		material=RawMaterial.RESOURCE_STEEL;
		recoverPhyStats();
	}

}
