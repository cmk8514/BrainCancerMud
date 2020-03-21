package com.planet_ink.coffee_mud.Items.Basic;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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
public class GenRuinedItem extends GenItem
{
	@Override
	public String ID()
	{
		return "GenRuinedItem";
	}

	public GenRuinedItem()
	{
		super();
		setName("a ruined thing");
		setDisplayText("a ruined thing sits here");
		setDescription("");
		recoverPhyStats();
	}
	
	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(msg.target()==this)
		{
			switch(msg.targetMinor())
			{
			case CMMsg.TYP_WEAR:
				msg.source().tell(msg.source(),this,null,L("<T-NAME> is ruined and can not be worn."));
				return false;
			case CMMsg.TYP_WIELD:
				msg.source().tell(msg.source(),this,null,L("<T-NAME> is ruined and can not be wielded."));
				return false;
			default:
				break;
			}
		}
		return super.okMessage(myHost, msg);
	}
	
}
