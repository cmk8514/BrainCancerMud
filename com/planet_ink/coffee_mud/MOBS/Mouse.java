package com.planet_ink.coffee_mud.MOBS;
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
public class Mouse extends StdMOB
{
	@Override
	public String ID()
	{
		return "Mouse";
	}

	public Mouse()
	{
		super();
		username="a mouse";
		setDescription("The little mouse is a small white rodent.");
		setDisplayText("A little mouse tries to scurry out of your way.");
		CMLib.factions().setAlignment(this,Faction.Align.NEUTRAL);
		setMoney(0);
		setWimpHitPoint(2);

		basePhyStats().setDamage(1);

		baseCharStats().setMyRace(CMClass.getRace("Mouse"));
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
