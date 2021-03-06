package com.planet_ink.coffee_mud.WebMacros;

import java.util.Enumeration;
import java.util.TreeSet;
import java.util.Vector;

import com.planet_ink.coffee_mud.Races.interfaces.Race;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_web.interfaces.HTTPRequest;
import com.planet_ink.coffee_web.interfaces.HTTPResponse;

/*
   Copyright 2007-2017 Bo Zimmerman

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

public class RaceCatNext extends StdWebMacro
{
	@Override
	public String name()
	{
		return "RaceCatNext";
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		final java.util.Map<String,String> parms=parseParms(parm);
		final String last=httpReq.getUrlParameter("RACECAT");
		if(parms.containsKey("RESET"))
		{
			if(last!=null)
				httpReq.removeUrlParameter("RACECAT");
			return "";
		}
		Vector<String> raceCats=new Vector<String>();
		for(final Enumeration<Race> r=CMClass.races();r.hasMoreElements();)
		{
			final Race R=r.nextElement();
			if((!raceCats.contains(R.racialCategory()))
			&&((CMLib.login().isAvailableRace(R))
				||(parms.containsKey("ALL"))))
					raceCats.addElement(R.racialCategory());
		}
		raceCats=new Vector<String>(new TreeSet<String>(raceCats));
		String lastID="";
		for(final Enumeration<String> r=raceCats.elements();r.hasMoreElements();)
		{
			final String RC=r.nextElement();
			if((last==null)||((last.length()>0)&&(last.equals(lastID))&&(!RC.equals(lastID))))
			{
				httpReq.addFakeUrlParameter("RACECAT",RC);
				return "";
			}
			lastID=RC;
		}
		httpReq.addFakeUrlParameter("RACECAT","");
		if(parms.containsKey("EMPTYOK"))
			return "<!--EMPTY-->";
		return " @break@";
	}

}
