package com.planet_ink.coffee_mud.Abilities.Spells;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/**
 * Title: False Realities Flavored CoffeeMUD
 * Description: The False Realities Version of CoffeeMUD
 * Copyright: Copyright (c) 2003 Jeremy Vyska
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Company: http://www.falserealities.com
 * @author FR - Jeremy Vyska; CM - Bo Zimmerman
 * @version 1.0.0.0
 */

public class Spell_CEqPoison extends Spell_BaseClanEq {

@Override
public String ID()
{
	return "Spell_CEqPoison";
}

private final static String localizedName = CMLib.lang().L("ClanEnchant Poison");

	@Override
	public String name()
	{
		return localizedName;
	}

@Override
public int abstractQuality()
{
	return Ability.QUALITY_INDIFFERENT;
}

  @Override
public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
  {
	type="Poison";
	// All the work is done by the base model
	if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
		return false;
	  return true;
  }
}
