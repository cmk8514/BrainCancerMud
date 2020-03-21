/*
 * LAST UPDATED BY: Spike
 * LAST UPDATED ON: 3/21/2020
 * ACTION TAKEN: Removed imported packages that were not used to remove warnings 
 */
package com.planet_ink.coffee_mud.Abilities.Diseases;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Abilities.interfaces.DiseaseAffect;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.CharState;
import com.planet_ink.coffee_mud.Common.interfaces.Social;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Tickable;

/*
   Copyright 2003-2017 Bo Zimmerman

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

public class Disease_Chlamydia extends Disease
{
	@Override
	public String ID()
	{
		return "Disease_Chlamydia";
	}

	private final static String localizedName = CMLib.lang().L("Chlamydia");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String localizedStaticDisplay = CMLib.lang().L("(Chlamydia)");

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
	public int difficultyLevel()
	{
		return 5;
	}

	@Override
	protected int DISEASE_TICKS()
	{
		return CMProps.getIntVar(CMProps.Int.TICKSPERMUDDAY)*10;
	}

	@Override
	protected int DISEASE_DELAY()
	{
		return CMProps.getIntVar(CMProps.Int.TICKSPERMUDDAY);
	}

	@Override
	protected String DISEASE_DONE()
	{
		return L("Your chlamydia clears up.");
	}

	@Override
	protected String DISEASE_START()
	{
		return L("^G<S-NAME> scratch(es) <S-HIS-HER> privates.^?");
	}

	@Override
	protected String DISEASE_AFFECT()
	{
		return L("<S-NAME> scratch(es) <S-HIS-HER> privates.");
	}

	@Override
	public int spreadBitmap()
	{
		return DiseaseAffect.SPREAD_STD;
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
		if((!mob.amDead())&&((--diseaseTick)<=0))
		{
			diseaseTick=DISEASE_DELAY();
			mob.location().show(mob,null,CMMsg.MSG_NOISE,DISEASE_AFFECT());
			return true;
		}
		return true;
	}

	@Override
	public void affectCharState(MOB affected, CharState affectableState)
	{
		super.affectCharState(affected,affectableState);
		affectableState.setMovement(affectableState.getMovement()/2);
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(affected==null)
			return super.okMessage(myHost,msg);
		if(affected instanceof MOB)
		{
			final MOB mob=(MOB)affected;
			if(((msg.amITarget(mob))||(msg.amISource(mob)))
			&&(msg.tool() instanceof Social)
			&&(msg.tool().Name().equals("MATE <T-NAME>")
			||msg.tool().Name().equals("SEX <T-NAME>")))
			{
				msg.source().tell(mob,null,null,L("<S-NAME> really do(es)n't feel like it."));
				return false;
			}
		}
		return super.okMessage(myHost,msg);
	}
}
