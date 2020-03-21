package com.planet_ink.coffee_mud.WebMacros;

import java.util.Enumeration;
import java.util.HashSet;

import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.Deity;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_web.interfaces.HTTPRequest;
import com.planet_ink.coffee_web.interfaces.HTTPResponse;

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

public class DeityNext extends StdWebMacro
{
	@Override
	public String name()
	{
		return "DeityNext";
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		final java.util.Map<String,String> parms=parseParms(parm);
		final String last=httpReq.getUrlParameter("DEITY");
		if(parms.containsKey("RESET"))
		{
			if(last!=null)
				httpReq.removeUrlParameter("DEITY");
			return "";
		}
		String lastID="";
		final HashSet<Room> heavensfound=new HashSet<Room>();
		for(final Enumeration<Deity> d=CMLib.map().deities();d.hasMoreElements();)
		{
			final Deity D=d.nextElement();
			if((D.location()!=null)&&(!heavensfound.contains(D.location())))
			{
				if(parms.containsKey("HEAVENS"))
					heavensfound.add(D.location());
				if((last==null)||((last.length()>0)&&(last.equals(lastID))&&(!D.Name().equals(lastID))))
				{
					httpReq.addFakeUrlParameter("DEITY",D.Name());
					return "";
				}
				lastID=D.Name();
			}
		}
		httpReq.addFakeUrlParameter("DEITY","");
		if(parms.containsKey("EMPTYOK"))
			return "<!--EMPTY-->";
		return " @break@";
	}

}
