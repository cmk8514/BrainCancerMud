package com.planet_ink.coffee_mud.MOBS;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.Faction;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
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
public class Parakeet extends StdMOB
{
	@Override
	public String ID()
	{
		return "Parakeet";
	}

	public Parakeet()
	{
		super();
		username="a parakeet";
		setDescription("a small colorful bird.");
		setDisplayText("A parakeet flies nearby.");
		CMLib.factions().setAlignment(this,Faction.Align.NEUTRAL);
		setMoney(0);
		setWimpHitPoint(0);

		baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,1);

		basePhyStats().setDamage(1);
		basePhyStats().setSpeed(1.0);
		basePhyStats().setAbility(0);
		basePhyStats().setLevel(1);
		basePhyStats().setArmor(90);
		basePhyStats().setDisposition(basePhyStats().disposition()|PhyStats.IS_FLYING);
		baseCharStats().setMyRace(CMClass.getRace("SongBird"));
		baseCharStats().getMyRace().startRacing(this,false);

		baseState.setHitPoints(CMLib.dice().roll(basePhyStats().level(),20,basePhyStats().level()));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();
	}

}
