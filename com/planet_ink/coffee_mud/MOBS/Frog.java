package com.planet_ink.coffee_mud.MOBS;
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
public class Frog extends StdMOB
{
	@Override
	public String ID()
	{
		return "Frog";
	}

	public Frog()
	{
		super();
		username="a frog";
		setDescription("A small little amphibian with long legs and big eyes.");
		setDisplayText("A frog is hopping around.");
		CMLib.factions().setAlignment(this,Faction.Align.NEUTRAL);
		setMoney(0);

		basePhyStats().setDamage(6);

		baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,1);

		basePhyStats().setAbility(0);
		basePhyStats().setLevel(1);
		basePhyStats().setArmor(90);

		baseCharStats().setMyRace(CMClass.getRace("Frog"));
		baseCharStats().getMyRace().startRacing(this,false);
		baseState.setHitPoints(CMLib.dice().rollHP(basePhyStats.level(), 20));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();
	}

}
