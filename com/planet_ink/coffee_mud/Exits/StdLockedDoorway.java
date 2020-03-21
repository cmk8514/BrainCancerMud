package com.planet_ink.coffee_mud.Exits;

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
public class StdLockedDoorway extends StdClosedDoorway
{
	@Override
	public String ID()
	{
		return "StdLockedDoorway";
	}

	@Override
	public boolean hasALock()
	{
		return true;
	}

	@Override
	public boolean defaultsLocked()
	{
		return true;
	}

	@Override
	public String closedText()
	{
		return "a closed, locked door";
	}
}
