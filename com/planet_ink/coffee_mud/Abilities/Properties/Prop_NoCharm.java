package com.planet_ink.coffee_mud.Abilities.Properties;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Areas.interfaces.Area;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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
public class Prop_NoCharm extends Property
{
	@Override
	public String ID()
	{
		return "Prop_NoCharm";
	}

	@Override
	public String name()
	{
		return "Charm Spell Neutralizing";
	}

	@Override
	protected int canAffectCode()
	{
		return Ability.CAN_ROOMS|Ability.CAN_AREAS|Ability.CAN_MOBS;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_IMMUNER;
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;

		if((msg.tool() instanceof Ability)
		&&(msg.source().location()!=null)
		&&((msg.source().location()==affected)
		   ||((affected instanceof Area)&&(((Area)affected).inMyMetroArea(msg.source().location().getArea())))
		   ||(msg.target()==affected))
		&&(CMath.bset(((Ability)msg.tool()).flags(),Ability.FLAG_CHARMING)))
		{
			final Ability A=(Ability)msg.tool();
			if(((A.classificationCode()&Ability.ALL_ACODES)==Ability.ACODE_CHANT)
			||((A.classificationCode()&Ability.ALL_ACODES)==Ability.ACODE_SPELL)
			||((A.classificationCode()&Ability.ALL_ACODES)==Ability.ACODE_PRAYER)
			||((A.classificationCode()&Ability.ALL_ACODES)==Ability.ACODE_SONG))
				msg.source().location().showHappens(CMMsg.MSG_OK_VISUAL,L("Magic energy fizzles and is absorbed into the air."));
			return false;
		}
		return true;
	}
}
