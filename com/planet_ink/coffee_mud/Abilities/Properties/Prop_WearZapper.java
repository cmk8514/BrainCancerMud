package com.planet_ink.coffee_mud.Abilities.Properties;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Abilities.interfaces.TriggeredAffect;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
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
public class Prop_WearZapper extends Prop_HaveZapper
{
	@Override
	public String ID()
	{
		return "Prop_WearZapper";
	}

	@Override
	public String name()
	{
		return "Restrictions to wielding/wearing/holding";
	}

	@Override
	protected int canAffectCode()
	{
		return Ability.CAN_ITEMS;
	}

	@Override
	public String accountForYourself()
	{
		return "Wearing restricted as follows: "+CMLib.masking().maskDesc(miscText);
	}

	@Override
	public int triggerMask()
	{
		return TriggeredAffect.TRIGGER_WEAR_WIELD;
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(affected==null)
			return super.okMessage(myHost, msg);
		if(!(affected instanceof Item))
			return super.okMessage(myHost, msg);
		final Item myItem=(Item)affected;

		final MOB mob=msg.source();
		if(mob.location()==null)
			return true;

		if(msg.amITarget(myItem))
		switch(msg.targetMinor())
		{
		case CMMsg.TYP_HOLD:
			if((!CMLib.masking().maskCheck(text(),mob,actual))&&(CMLib.dice().rollPercentage()<=percent))
			{
				mob.location().show(mob,null,myItem,CMMsg.MSG_OK_VISUAL,msgStr);
				return false;
			}
			break;
		case CMMsg.TYP_WEAR:
			if((!CMLib.masking().maskCheck(text(),mob,actual))&&(CMLib.dice().rollPercentage()<=percent))
			{
				mob.location().show(mob,null,myItem,CMMsg.MSG_OK_VISUAL,msgStr);
				return false;
			}
			break;
		case CMMsg.TYP_WIELD:
			if((!CMLib.masking().maskCheck(text(),mob,actual))&&(CMLib.dice().rollPercentage()<=percent))
			{
				mob.location().show(mob,null,myItem,CMMsg.MSG_OK_VISUAL,msgStr);
				return false;
			}
			break;
		case CMMsg.TYP_GET:
			break;
		default:
			break;
		}
		return true;
	}
}
