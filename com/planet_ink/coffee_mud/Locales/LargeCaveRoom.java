package com.planet_ink.coffee_mud.Locales;

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
public class LargeCaveRoom extends CaveRoom
{
	@Override
	public String ID()
	{
		return "LargeCaveRoom";
	}

	public LargeCaveRoom()
	{
		super();
		basePhyStats.setWeight(4);
		recoverPhyStats();
	}

	@Override
	public int maxRange()
	{
		return 5;
	}
}
