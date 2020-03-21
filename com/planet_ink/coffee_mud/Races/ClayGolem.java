package com.planet_ink.coffee_mud.Races;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.core.CMLib;

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
public class ClayGolem extends StoneGolem
{
	@Override
	public String ID()
	{
		return "ClayGolem";
	}

	private final static String localizedStaticName = CMLib.lang().L("Clay Golem");

	@Override
	public String name()
	{
		return localizedStaticName;
	}

	protected static Vector<RawMaterial> resources=new Vector<RawMaterial>();

	@Override
	public List<RawMaterial> myResources()
	{
		synchronized(resources)
		{
			if(resources.size()==0)
			{
				resources.addElement(makeResource
					(L("a pound of clay"),RawMaterial.RESOURCE_CLAY));
				resources.addElement(makeResource
					(L("essence of golem"),RawMaterial.RESOURCE_BLOOD));
			}
		}
		return resources;
	}

}
