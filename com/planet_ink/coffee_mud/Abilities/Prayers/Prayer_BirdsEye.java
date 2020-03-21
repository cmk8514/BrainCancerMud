package com.planet_ink.coffee_mud.Abilities.Prayers;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.PhyStats;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Libraries.interfaces.TrackingLibrary;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.interfaces.Physical;

/*
   Copyright 2004-2017 Bo Zimmerman

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

public class Prayer_BirdsEye extends Prayer
{
	@Override
	public String ID()
	{
		return "Prayer_BirdsEye";
	}

	private final static String localizedName = CMLib.lang().L("Birds Eye");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_PRAYER|Ability.DOMAIN_COMMUNING;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_NEUTRAL;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);
		if(success)
		{
			CMMsg msg=CMClass.getMsg(mob,null,this,verbalCastCode(mob,null,auto),auto?"":L("^S<S-NAME> @x1 for a birds eye view.^?",prayWord(mob)));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				final Item I=CMClass.getItem("BardMap");
				if(I!=null)
				{
					final Vector<Room> set=new Vector<Room>();
					TrackingLibrary.TrackingFlags flags;
					flags = CMLib.tracking().newFlags()
							.plus(TrackingLibrary.TrackingFlag.NOEMPTYGRIDS)
							.plus(TrackingLibrary.TrackingFlag.NOAIR);
					CMLib.tracking().getRadiantRooms(mob.location(),set,flags,null,2,null);
					final StringBuffer str=new StringBuffer("");
					for(int i=0;i<set.size();i++)
						str.append(CMLib.map().getExtendedRoomID(set.elementAt(i))+";");
					I.setReadableText(str.toString());
					I.setName("");
					I.basePhyStats().setDisposition(PhyStats.IS_GLOWING);
					msg=CMClass.getMsg(mob,I,CMMsg.MSG_READ,"");
					mob.addItem(I);
					mob.location().send(mob,msg);
					I.destroy();
				}
			}
		}
		else
			beneficialWordsFizzle(mob,null,L("<S-NAME> @x1 for a birds eye view, but fail(s).",prayWord(mob)));

		return success;
	}
}
