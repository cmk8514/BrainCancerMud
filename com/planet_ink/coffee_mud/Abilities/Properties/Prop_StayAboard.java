package com.planet_ink.coffee_mud.Abilities.Properties;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.ItemPossessor;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
import com.planet_ink.coffee_mud.core.interfaces.Rideable;
import com.planet_ink.coffee_mud.core.interfaces.Rider;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

/*
   Copyright 2009-2017 Bo Zimmerman

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
public class Prop_StayAboard extends Property
{
	@Override
	public String ID()
	{
		return "Prop_StayAboard";
	}

	@Override
	public String name()
	{
		return "Stays on mounted thing";
	}

	@Override
	protected int canAffectCode()
	{
		return Ability.CAN_ITEMS|Ability.CAN_MOBS;
	}

	protected Rideable rideable=null;

	@Override
	public String accountForYourself()
	{
		return "Stays on anything mounted to.";
	}

	protected boolean noRepeat=false;

	@Override
	public void setAffectedOne(Physical P)
	{
		super.setAffectedOne(P);
		if(P instanceof Rider)
		{
			rideable = ((Rider)P).riding();
		}
	}

	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking, tickID))
			return false;
		synchronized(this)
		{
			if(noRepeat)
				return true;
			try
			{
				noRepeat=true;
				if((tickID==Tickable.TICKID_MOB)
				&&(ticking instanceof Rider)
				&&(ticking instanceof MOB)
				&&(rideable!=null))
					stayAboard((Rider)ticking);
			}
			finally
			{
				noRepeat=false;
			}
		}
		return true;
	}

	public void stayAboard(Rider R)
	{
		final Room rideR=CMLib.map().roomLocation(rideable);
		if((rideR!=null)
		&&((CMLib.map().roomLocation(R)!=rideR)
			||(R.riding()!=rideable)))
		{
			if(R.riding()!=null)
				R.setRiding(null);
			if(CMLib.map().roomLocation(R)!=rideR)
				if(R instanceof Item)
					rideR.moveItemTo((Item)R,ItemPossessor.Expire.Never,ItemPossessor.Move.Followers);
				else
				if(R instanceof MOB)
					rideR.bringMobHere((MOB)R,true);
			R.setRiding(rideable);
		}
	}

	@Override
	public void affectPhyStats(Physical E, PhyStats affectableStats)
	{
		super.affectPhyStats(E, affectableStats);
		synchronized(this)
		{
			if(noRepeat)
				return;
			try
			{
				noRepeat=true;
				if(E instanceof Rider)
					if(rideable==null)
						rideable=((Rider)E).riding();
					else
					if(!CMLib.flags().isInTheGame(rideable,true))
						rideable=null;
					else
					if(E instanceof Item)
						stayAboard((Rider)E);
			}
			finally
			{
				noRepeat=false;
			}
		}
	}
}
