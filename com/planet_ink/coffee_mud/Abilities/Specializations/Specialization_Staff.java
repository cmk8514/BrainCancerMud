package com.planet_ink.coffee_mud.Abilities.Specializations;
import com.planet_ink.coffee_mud.Items.interfaces.Weapon;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2006-2017 Bo Zimmerman

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
public class Specialization_Staff extends Specialization_Weapon
{
	@Override
	public String ID()
	{
		return "Specialization_Staff";
	}

	private final static String	localizedName	= CMLib.lang().L("Staff Specialization");

	@Override
	public String name()
	{
		return localizedName;
	}

	public Specialization_Staff()
	{
		super();
		weaponClass=Weapon.CLASS_STAFF;
	}
}
