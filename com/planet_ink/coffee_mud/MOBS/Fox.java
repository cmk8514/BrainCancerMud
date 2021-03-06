package com.planet_ink.coffee_mud.MOBS;
import java.util.Random;

import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.Faction;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2000-2017 Lee H. Fox

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
public class Fox extends StdMOB
{
	@Override
	public String ID()
	{
		return "Fox";
	}

	public Fox()
	{
		super();
		final Random randomizer = new Random(System.currentTimeMillis());

		username="a fox";
		setDescription("It`s got a red coat and a tail.  Never has there been a more majestic animal.");
		setDisplayText("A fox growls.");
		CMLib.factions().setAlignment(this,Faction.Align.NEUTRAL);
		setMoney(0);
		basePhyStats.setWeight(20 + Math.abs(randomizer.nextInt() % 55));
		setWimpHitPoint(2);

		baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,1);
		baseCharStats().setMyRace(CMClass.getRace("Fox"));
		baseCharStats().getMyRace().startRacing(this,false);

		basePhyStats().setDamage(6);
		basePhyStats().setSpeed(1.0);
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(5);
		basePhyStats().setArmor(70);

		baseState.setHitPoints(CMLib.dice().roll(basePhyStats().level(),20,basePhyStats().level()));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();
	}

}
