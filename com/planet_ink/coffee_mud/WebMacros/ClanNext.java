package com.planet_ink.coffee_mud.WebMacros;

import java.util.Enumeration;

import com.planet_ink.coffee_mud.Common.interfaces.Clan;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_web.interfaces.HTTPRequest;
import com.planet_ink.coffee_web.interfaces.HTTPResponse;

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
@SuppressWarnings("rawtypes")
public class ClanNext extends StdWebMacro
{
	@Override
	public String name()
	{
		return "ClanNext";
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		final java.util.Map<String,String> parms=parseParms(parm);
		final String last=httpReq.getUrlParameter("CLAN");
		if(parms.containsKey("RESET"))
		{
			if(last!=null)
				httpReq.removeUrlParameter("CLAN");
			return "";
		}
		String lastID="";
		for(final Enumeration c=CMLib.clans().clans();c.hasMoreElements();)
		{
			final Clan C=(Clan)c.nextElement();
			if((last==null)||((last.length()>0)&&(last.equals(lastID))&&(!C.clanID().equals(lastID))))
			{
				httpReq.addFakeUrlParameter("CLAN",C.clanID());
				return "";
			}
			lastID=C.clanID();
		}
		httpReq.addFakeUrlParameter("CLAN","");
		if(parms.containsKey("EMPTYOK"))
			return "<!--EMPTY-->";
		return " @break@";
	}
}
