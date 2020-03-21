package com.planet_ink.coffee_mud.MOBS;
import java.util.Random;

import com.planet_ink.coffee_mud.Common.interfaces.Faction;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;

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
public class HeavenlyServent extends StdMOB
{
	@Override
	public String ID()
	{
		return "HeavenlyServent";
	}

	public HeavenlyServent()
	{
		super();

		final Random randomizer = new Random(System.currentTimeMillis());

		username="an archon servant";
		setDescription("An angelic form in gowns of white, with golden hair, and an ever present smile.");
		setDisplayText("A servant of the Archons is running errands.");
		CMLib.factions().setAlignment(this,Faction.Align.NEUTRAL);
		setMoney(0);
		basePhyStats.setWeight(20 + Math.abs(randomizer.nextInt() % 55));
		setWimpHitPoint(2);

		addBehavior(CMClass.getBehavior("Mobile"));
		addBehavior(CMClass.getBehavior("MudChat"));

		basePhyStats().setDamage(25);

		basePhyStats().setAbility(0);
		basePhyStats().setLevel(10);
		basePhyStats().setArmor(0);
		baseCharStats().setMyRace(CMClass.getRace("Human"));
		baseCharStats().getMyRace().startRacing(this,false);

		baseState.setHitPoints(CMLib.dice().roll(basePhyStats().level(),20,basePhyStats().level()));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();
	}

}
