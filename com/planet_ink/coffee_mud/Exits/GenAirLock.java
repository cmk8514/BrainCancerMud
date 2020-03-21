package com.planet_ink.coffee_mud.Exits;

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
public class GenAirLock extends GenExit
{
	@Override
	public String ID()
	{
		return "GenAirLock";
	}

	@Override
	public String Name()
	{
		return "an air lock";
	}

	@Override
	public String displayText()
	{
		return "";
	}

	@Override
	public boolean hasADoor()
	{
		return true;
	}

	@Override
	public boolean hasALock()
	{
		return false;
	}

	@Override
	public boolean defaultsLocked()
	{
		return false;
	}

	@Override
	public boolean defaultsClosed()
	{
		return true;
	}

	@Override
	public String closedText()
	{
		return "a closed air lock door";
	}

	public GenAirLock()
	{
		super();
		name="an air lock door";
		displayText="";
		description="This door leads to the outside of the ship through a small air lock.";
		hasADoor=true;
		hasALock=false;
		doorDefaultsClosed=true;
		doorDefaultsLocked=false;
		closedText="a closed air lock door";
		doorName="door";
		closeName="close";
		openName="open";
	}
}
