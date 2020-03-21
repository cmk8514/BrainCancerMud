package com.planet_ink.coffee_mud.Commands;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Items.interfaces.Container;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_mud.core.CMSecurity;
import com.planet_ink.coffee_mud.core.CMath;
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

public class Restring extends StdCommand
{
	public Restring(){}

	private final String[] access=I(new String[]{"RESTRING"});
	@Override
	public String[] getAccessWords()
	{
		return access;
	}

	public boolean errorOut(MOB mob)
	{
		mob.tell(L("You are not allowed to do that here."));
		return false;
	}

	@Override
	public boolean execute(MOB mob, List<String> commands, int metaFlags)
		throws java.io.IOException
	{
		String allWord=CMParms.combine(commands,1);
		final int x=allWord.indexOf('@');
		MOB srchMob=mob;
		Item srchContainer=null;
		Room srchRoom=mob.location();
		if(x>0)
		{
			final String rest=allWord.substring(x+1).trim();
			allWord=allWord.substring(0,x).trim();
			if(rest.equalsIgnoreCase("room"))
				srchMob=null;
			else
			if(rest.length()>0)
			{
				final MOB M=srchRoom.fetchInhabitant(rest);
				if(M==null)
				{
					final Item I = srchRoom.findItem(null, rest);
					if(I instanceof Container)
						srchContainer=I;
					else
					{
						mob.tell(L("MOB or Container '@x1' not found.",rest));
						mob.location().showOthers(mob,null,CMMsg.MSG_OK_ACTION,L("<S-NAME> flub(s) a spell.."));
						return false;
					}
				}
				else
				{
					srchMob=M;
					srchRoom=null;
				}
			}
		}
		Physical thang=null;
		if((srchMob!=null)&&(srchRoom!=null))
			thang=srchRoom.fetchFromMOBRoomFavorsItems(srchMob,srchContainer,allWord,Wearable.FILTER_ANY);
		else
		if(srchMob!=null)
			thang=srchMob.findItem(allWord);
		else
		if(srchRoom!=null)
			thang=srchRoom.fetchFromRoomFavorItems(srchContainer,allWord);
		if((thang!=null)&&(thang instanceof Item))
		{
			if(!thang.isGeneric())
				mob.tell(L("@x1 can not be restrung.",thang.name()));
			else
			{
				int showFlag=-1;
				if(CMProps.getIntVar(CMProps.Int.EDITORTYPE)>0)
					showFlag=-999;
				boolean ok=false;
				while(!ok)
				{
					int showNumber=0;
					CMLib.genEd().genName(mob,thang,++showNumber,showFlag);
					CMLib.genEd().genDisplayText(mob,thang,++showNumber,showFlag);
					CMLib.genEd().genDescription(mob,thang,++showNumber,showFlag);
					if(showFlag<-900)
					{
						ok=true;
						break;
					}
					if(showFlag>0)
					{
						showFlag=-1;
						continue;
					}
					showFlag=CMath.s_int(mob.session().prompt(L("Edit which? "),""));
					if(showFlag<=0)
					{
						showFlag=-1;
						ok=true;
					}
				}
			}
			thang.recoverPhyStats();
			mob.location().show(mob,null,CMMsg.MSG_OK_VISUAL,L("@x1 shake(s) under the transforming power.",thang.name()));
		}
		else
			mob.tell(L("'@x1' can not be restrung.",allWord));
		return false;
	}

	@Override
	public boolean canBeOrdered()
	{
		return true;
	}

	@Override
	public boolean securityCheck(MOB mob)
	{
		return CMSecurity.isAllowedContainsAny(mob,mob.location(),CMSecurity.SECURITY_CMD_GROUP)
			 ||CMSecurity.isAllowed(mob,mob.location(),CMSecurity.SecFlag.RESTRING);
	}

}
