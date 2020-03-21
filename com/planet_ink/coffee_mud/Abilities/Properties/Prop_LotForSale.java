package com.planet_ink.coffee_mud.Abilities.Properties;
import com.planet_ink.coffee_mud.Libraries.interfaces.LegalLibrary;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.core.interfaces.LandTitle;

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

public class Prop_LotForSale extends Prop_LotsForSale
{
	@Override
	public String ID()
	{
		return "Prop_LotForSale";
	}

	@Override
	public String name()
	{
		return "Buy a room once, get all adjacent rooms free";
	}
	
	@Override
	public String getTitleID()
	{
		return super.getUniqueLotID();
	}
	
	@Override
	public LandTitle generateNextRoomTitle()
	{
		final LandTitle newTitle=(LandTitle)this.copyOf();
		newTitle.setBackTaxes(0);
		return newTitle;
	}

	@Override
	public boolean canGenerateAdjacentRooms(Room R)
	{
		return ((R.displayText().indexOf(L(LegalLibrary.INDOORSTR).trim())<0)
			  &&(R.displayText().indexOf(L(LegalLibrary.OUTDOORSTR).trim())<0)
			  &&(R.description().indexOf(L(LegalLibrary.SALESTR).trim())<0)
			  &&(R.description().indexOf(L(LegalLibrary.RENTSTR).trim())<0));
	}
}
