package com.planet_ink.coffee_mud.Locales;
import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;

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
public class IndoorInTheAir extends StdRoom
{
	@Override
	public String ID()
	{
		return "IndoorInTheAir";
	}

	public IndoorInTheAir()
	{
		super();
		name="the space";
		basePhyStats.setWeight(1);
		recoverPhyStats();
	}

	@Override
	public int domainType()
	{
		return Room.DOMAIN_INDOORS_AIR;
	}

	@Override
	public int maxRange()
	{
		return 5;
	}

	@Override
	public boolean okMessage(final Environmental myHost, final CMMsg msg)
	{
		if(!super.okMessage(myHost,msg))
			return false;
		return InTheAir.isOkAirAffect(this,msg);
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		super.executeMsg(myHost,msg);
		InTheAir.airAffects(this,msg);
	}

}
