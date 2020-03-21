package com.planet_ink.coffee_mud.Locales;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.Directions;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.Rideable;

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
public class ClimbableLedge extends ClimbableSurface
{
	@Override
	public String ID()
	{
		return "ClimbableLedge";
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(CMLib.flags().isSleeping(this))
			return super.okMessage(myHost,msg);

		if((msg.targetMinor()==CMMsg.TYP_ENTER)
		&&(msg.amITarget(this)))
		{
			final Rideable ladder=CMLib.tracking().findALadder(msg.source(),this);
			if(ladder!=null)
			{
				msg.source().setRiding(ladder);
				msg.source().recoverPhyStats();
			}
			if((getRoomInDir(Directions.DOWN)!=msg.source().location()))
				return true;
		}
		return super.okMessage(myHost,msg);
	}

}
