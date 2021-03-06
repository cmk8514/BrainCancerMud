package com.planet_ink.coffee_mud.Behaviors;
import java.util.Enumeration;

import com.planet_ink.coffee_mud.Behaviors.interfaces.Behavior;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

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
public class ROMGangMember extends StdBehavior
{
	@Override
	public String ID()
	{
		return "ROMGangMember";
	}

	int tickTock=5;

	@Override
	public String accountForYourself()
	{
		return "gang membership";
	}

	public void pickAFight(MOB observer)
	{
		if(!canFreelyBehaveNormal(observer))
			return;
		if(observer.location().numPCInhabitants()==0)
			return;

		MOB victim=null;
		String vicParms="";
		for(int i=0;i<observer.location().numInhabitants();i++)
		{
			final MOB inhab=observer.location().fetchInhabitant(i);
			if((inhab!=null)
			&&((inhab.isMonster())||(CMLib.clans().findCommonRivalrousClans(inhab,observer).size()==0)))
			{
				for(final Enumeration<Behavior> e=inhab.behaviors();e.hasMoreElements();)
				{
					final Behavior B=e.nextElement();
					if(B.ID().equals(ID())&&(!B.getParms().equals(getParms())))
					{
						victim=inhab;
						vicParms=B.getParms();
					}
					else
					if((B.ID().indexOf("GoodGuardian")>=0)||(B.ID().indexOf("Patrolman")>=0))
						return;
				}
			}
		}

		if(victim==null)
			return;
		Item weapon=observer.fetchWieldedItem();
		if(weapon==null)
			weapon=observer.getNaturalWeapon();

		/* say something, then raise hell */
		switch (CMLib.dice().roll(1,7,-1))
		{
		case 0:
			observer.location().show(observer,null,CMMsg.MSG_SPEAK,L("^T<S-NAME> yell(s) 'I've been looking for you, punk!'^?"));
			break;
		case 1:
			observer.location().show(observer,victim,CMMsg.MSG_NOISYMOVEMENT,L("With a scream of rage, <S-NAME> attack(s) <T-NAME>."));
			break;
		case 2:
			observer.location().show(observer,victim,CMMsg.MSG_SPEAK,L("^T<S-NAME> say(s) 'What's slimy @x1 trash like you doing around here?'^?",vicParms));
			break;
		case 3:
			observer.location().show(observer,victim,CMMsg.MSG_SPEAK,L("^T<S-NAME> crack(s) <S-HIS-HER> knuckles and say(s) 'Do ya feel lucky?'^?"));
			break;
		case 4:
			observer.location().show(observer,victim,CMMsg.MSG_SPEAK,L("^T<S-NAME> say(s) 'There's no cops to save you this time!'^?"));
			break;
		case 5:
			observer.location().show(observer,victim,CMMsg.MSG_SPEAK,L("^T<S-NAME> say(s) 'Time to join your brother, spud.'^?"));
			break;
		case 6:
			observer.location().show(observer,victim,CMMsg.MSG_SPEAK,L("^T<S-NAME> say(s) 'Let's rock.'^?"));
			break;
		}

		CMLib.combat().postAttack(observer,victim,weapon);
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		super.tick(ticking,tickID);

		if(tickID!=Tickable.TICKID_MOB)
			return true;
		final MOB mob=(MOB)ticking;
		tickTock--;
		if(tickTock<=0)
		{
			tickTock=CMLib.dice().roll(1,10,0);
			pickAFight(mob);
		}
		return true;
	}
}
