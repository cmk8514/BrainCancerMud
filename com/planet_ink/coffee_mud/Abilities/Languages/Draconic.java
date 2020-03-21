package com.planet_ink.coffee_mud.Abilities.Languages;
import java.util.List;
import java.util.Vector;

import com.planet_ink.coffee_mud.core.CMLib;

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

public class Draconic extends StdLanguage
{
	@Override
	public String ID()
	{
		return "Draconic";
	}

	private final static String localizedName = CMLib.lang().L("Draconic");

	@Override
	public String name()
	{
		return localizedName;
	}

	public static List<String[]> wordLists=null;
	public Draconic()
	{
		super();
	}

	@Override
	public List<String[]> translationVector(String language)
	{
		if(wordLists==null)
		{
			final String[] one={"y"};
			final String[] two={"ve","ov","iv","si","es","se"};
			final String[] three={"see","sev","ave","ces","ven","sod"};
			final String[] four={"nirg","avet","sav`e","choc","sess","sens","vent","vens","sven","yans","vays"};
			final String[] five={"splut","svets","fruite","dwagg","vrers","verrs","srens","swath","senys","varen"};
			final String[] six={"choccie","svenren","yorens","vyrues","whyrie","vrysenso","forin","sinnes","sessis","uroven","xorers","nosees"};
			wordLists=new Vector<String[]>();
			wordLists.add(one);
			wordLists.add(two);
			wordLists.add(three);
			wordLists.add(four);
			wordLists.add(five);
			wordLists.add(six);
		}
		return wordLists;
	}
}
