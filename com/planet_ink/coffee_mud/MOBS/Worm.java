package com.planet_ink.coffee_mud.MOBS;
import java.util.Random;

import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.Faction;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;

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
public class Worm extends StdMOB
{
	@Override
	public String ID()
	{
		return "Worm";
	}

	public Worm()
	{
		super();
		final Random randomizer = new Random(System.currentTimeMillis());

		username="a worm";
		setDescription("A long squirmy slithering thing.");
		setDisplayText("A worm is squirming around.");
		CMLib.factions().setAlignment(this,Faction.Align.NEUTRAL);
		setMoney(0);
		setWimpHitPoint(2);

		basePhyStats().setWeight(Math.abs(randomizer.nextInt() % 2));

		baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,1);
		baseCharStats().setStat(CharStats.STAT_STRENGTH,1);
		baseCharStats().setStat(CharStats.STAT_DEXTERITY,1);
		baseCharStats().setMyRace(CMClass.getRace("Worm"));
		baseCharStats().getMyRace().startRacing(this,false);

		basePhyStats().setDamage(10);
		basePhyStats().setSpeed(1.0);
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(1);
		basePhyStats().setArmor(90);

		baseState.setHitPoints(CMLib.dice().roll(basePhyStats().level(),20,basePhyStats().level()));

		addBehavior(CMClass.getBehavior("Mobile"));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();

		if(numAllAbilities()>0)
			addBehavior(CMClass.getBehavior("CombatAbilities"));
	}
}
