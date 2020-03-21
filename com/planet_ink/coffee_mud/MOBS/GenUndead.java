package com.planet_ink.coffee_mud.MOBS;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharState;
import com.planet_ink.coffee_mud.Common.interfaces.CharStats;
import com.planet_ink.coffee_mud.Common.interfaces.Faction;
import com.planet_ink.coffee_mud.Items.interfaces.DeadBody;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.Races.interfaces.Race;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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
public class GenUndead extends GenMob
{
	@Override
	public String ID()
	{
		return "GenUndead";
	}

	protected final Race undeadRace;

	public GenUndead()
	{
		super();
		username="a generic undead being";
		setDescription("He looks dead to me.");
		setDisplayText("A generic undead mob stands here.");
		CMLib.factions().setAlignment(this,Faction.Align.EVIL);
		setMoney(10);
		basePhyStats.setWeight(150);
		setWimpHitPoint(0);

		undeadRace=CMClass.getRace("Undead");
		if(undeadRace!=null)
		{
			baseCharStats().setMyRace(undeadRace);
			baseCharStats().getMyRace().startRacing(this,false);
			baseCharStats().setStat(CharStats.STAT_CHARISMA,2);
			recoverCharStats();
		}

		basePhyStats().setAbility(10);
		basePhyStats().setLevel(1);
		basePhyStats().setArmor(90);

		baseState.setHitPoints(CMLib.dice().roll(basePhyStats().level(),20,basePhyStats().level()));

		recoverMaxState();
		resetToMaxState();
		recoverPhyStats();
		recoverCharStats();
	}

	public void recoverMaxState(MOB affectedMOB, CharState affectableState)
	{
		super.recoverMaxState();
		if((charStats().getMyRace()!=undeadRace)&&(undeadRace!=null))
			undeadRace.affectCharState(this, maxState);
	}

	@Override
	public void recoverPhyStats()
	{
		super.recoverPhyStats();
		if((charStats().getMyRace()!=undeadRace)&&(undeadRace!=null))
			undeadRace.affectPhyStats(this, phyStats);
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		super.executeMsg(myHost,msg);
		if((charStats().getMyRace()!=undeadRace)&&(undeadRace!=null))
			undeadRace.executeMsg(this, msg);
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!super.okMessage(myHost, msg))
			return false;
		if((charStats().getMyRace()!=undeadRace)&&(undeadRace!=null))
			return undeadRace.okMessage(this, msg);
		return true;
	}

	@Override
	public void recoverCharStats()
	{
		super.recoverCharStats();
		if((charStats().getMyRace()!=undeadRace)&&(undeadRace!=null))
			undeadRace.affectCharStats(this, charStats);
	}

	@Override
	public DeadBody killMeDead(boolean createBody)
	{
		final DeadBody body=super.killMeDead(createBody);
		if((createBody)&&(charStats().getMyRace()!=undeadRace)&&(body!=null)&&(undeadRace!=null))
		{
			if((name().toUpperCase().indexOf("DRACULA")>=0)
			||(name().toUpperCase().indexOf("VAMPIRE")>=0))
				body.addNonUninvokableEffect(CMClass.getAbility("Disease_Vampirism"));
			else
			if((name().toUpperCase().indexOf("GHOUL")>=0)
			||(name().toUpperCase().indexOf("GHAST")>=0))
				body.addNonUninvokableEffect(CMClass.getAbility("Disease_Cannibalism"));
			final Ability A=CMClass.getAbility("Prop_Smell");
			if(A!=null)
			{
				body.addNonUninvokableEffect(A);
				A.setMiscText(body.name()+" SMELLS HORRIBLE!");
			}
		}
		return body;
	}

	@Override
	public boolean isGeneric()
	{
		return true;
	}
}
