package com.planet_ink.coffee_mud.Behaviors;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.interfaces.PhysicalAgent;

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
public class Druidness extends CombatAbilities
{
	@Override
	public String ID()
	{
		return "Druidness";
	}

	boolean confirmedSetup=false;

	@Override
	public String accountForYourself()
	{
		return "druidly";
	}

	@Override
	public void startBehavior(PhysicalAgent forMe)
	{
		super.startBehavior(forMe);
		if(!(forMe instanceof MOB))
			return;
		final MOB mob=(MOB)forMe;
		combatMode=COMBAT_RANDOM;
		makeClass(mob,getParmsMinusCombatMode(),"Druid");
		newCharacter(mob);
		//%%%%%att,armor,damage,hp,mana,move
		if((preCastSet==Integer.MAX_VALUE)||(preCastSet<=0))
		{
			setCombatStats(mob,0,0,0,25,10,0, true);
			setCharStats(mob);
		}
	}
}
