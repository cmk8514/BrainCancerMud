package com.planet_ink.coffee_mud.Abilities.Languages;
import java.util.List;
import java.util.Random;

import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.collections.XVector;

/*
   Copyright 2016-2017 Bo Zimmerman

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

public class Aquan extends AnimalSpeak
{
	@Override
	public String ID()
	{
		return "Aquan";
	}

	private final static String	localizedName	= CMLib.lang().L("Aquan");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String[] soundBase = new String[] 
	{
		"gurgle","blub","eeeeeeeee","oioioi","glub",
		"honk","glugglge","mrrr","wurrr","llllgl","gl","lb",
		"llrrrwwwrrr","blip","flup","glglglll","wwwrrr","lllrr",
		"glug","blubbablup","gurglflub","blubllll","splurt",
		"oi","eeee","rrwwwll","onkglgl","bluggg","lrrr","lg" 
	};

	private static String[] animalSounds = null;
	
	@Override
	protected String[] getSounds() 
	{
		if(animalSounds == null)
		{
			List<String> sounds=new XVector<String>(soundBase);
			Random r=new Random(System.currentTimeMillis());
			for(int i=0;i<soundBase.length * 2;i++)
			{
				String s=soundBase[r.nextInt(soundBase.length)]+soundBase[r.nextInt(soundBase.length)];
				if(!sounds.contains(s))
					sounds.add(s);
				else
					i--;
			}
			animalSounds = sounds.toArray(new String[sounds.size()]);
		}
		return animalSounds;
	}
}
