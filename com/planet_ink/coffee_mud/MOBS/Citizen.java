package com.planet_ink.coffee_mud.MOBS;
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
public class Citizen extends StdMOB
{
	@Override
	public String ID()
	{
		return "Citizen";
	}

	public Citizen()
	{
		super();
		username="a citizen";
		setDescription("");
		baseCharStats().setStat(CharStats.STAT_GENDER,(CMLib.dice().rollPercentage()>50)?'M':'F');
		setDisplayText("A citizen goes about "+baseCharStats().hisher()+" business.");
		CMLib.factions().setAlignment(this,Faction.Align.GOOD);
		setMoney(10);
		basePhyStats.setWeight(150);
		setWimpHitPoint(0);

		baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,7);
		baseCharStats().setMyRace(CMClass.getRace("Human"));
		baseCharStats().getMyRace().startRacing(this,false);

		basePhyStats().setAbility(0);
		basePhyStats().setLevel(1);
		basePhyStats().setArmor(90);

		baseState.setHitPoints(CMLib.dice().roll(basePhyStats().level(),20,basePhyStats().level()));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();
	}

}
