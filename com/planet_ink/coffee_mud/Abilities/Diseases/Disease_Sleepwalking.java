/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Diseases;
import java.util.ArrayList;
import java.util.List;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.Directions;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

/*
   Copyright 2015-2017 Bo Zimmerman

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

public class Disease_Sleepwalking extends Disease
{
	@Override
	public String ID()
	{
		return "Disease_Sleepwalking";
	}

	private final static String	localizedName	= CMLib.lang().L("Sleepwalking");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String	localizedStaticDisplay	= "";

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_MOBS;
	}

	@Override
	protected int canTargetCode()
	{
		return CAN_MOBS;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_MALICIOUS;
	}

	@Override
	public boolean putInCommandlist()
	{
		return false;
	}

	@Override
	protected int DISEASE_TICKS()
	{
		return 10;
	}

	@Override
	protected int DISEASE_DELAY()
	{
		return 2;
	}

	@Override
	protected String DISEASE_DONE()
	{
		return L("You feel more in control.");
	}

	@Override
	protected String DISEASE_START()
	{
		return "";
	}

	@Override
	protected String DISEASE_AFFECT()
	{
		return L("");
	}

	@Override
	public int spreadBitmap()
	{
		return 0;
	}

	@Override
	public int difficultyLevel()
	{
		return 2;
	}

	@Override
	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if(!super.okMessage(myHost, msg))
			return false;
		if((msg.source()==affected)
		&&(CMLib.flags().isSleeping(affected))
		&&((msg.sourceMinor()==CMMsg.TYP_ENTER)
			||(msg.sourceMinor()==CMMsg.TYP_LEAVE)))
		{
			if(msg.sourceMessage()!=null)
				msg.setSourceMessage(null);
		}
		return true;
	}
	
	@Override
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking,tickID))
			return false;
		if(affected==null)
			return false;
		if(!(affected instanceof MOB))
			return true;

		final MOB mob=(MOB)affected;
		if((!mob.amDead())&&((--diseaseTick)<=0)&&(CMLib.flags().isSleeping(mob)))
		{
			final Room R=mob.location();
			if((R!=null)&&(CMLib.flags().isInTheGame(mob, true))&&(!CMLib.flags().isBoundOrHeld(mob)))
			{
				List<Integer> dirs=new ArrayList<Integer>(1);
				for(int d : Directions.CODES())
				{
					if((R.getRoomInDir(d)!=null)&&(R.getExitInDir(d)!=null)&&(R.getExitInDir(d).isOpen()))
						dirs.add(Integer.valueOf(d));
				}
				if(dirs.size()>0)
				{
					int dir=dirs.get(CMLib.dice().roll(1, dirs.size(), -1)).intValue();
					CMLib.tracking().walk(mob, dir, false, true, true, true);
				}
			}
			if(mob.location()==R)
				diseaseTick=DISEASE_DELAY();
			else
				diseaseTick=CMLib.dice().roll(1, 3, -1);
		}
		return true;
	}
}
