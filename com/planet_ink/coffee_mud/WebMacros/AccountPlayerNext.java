package com.planet_ink.coffee_mud.WebMacros;
import java.util.Enumeration;

import com.planet_ink.coffee_mud.Common.interfaces.PlayerAccount;
import com.planet_ink.coffee_mud.Libraries.interfaces.PlayerLibrary;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMProps;
import com.planet_ink.coffee_web.interfaces.HTTPRequest;
import com.planet_ink.coffee_web.interfaces.HTTPResponse;

/*
   Copyright 2010-2017 Bo Zimmerman

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
public class AccountPlayerNext extends StdWebMacro
{
	@Override
	public String name()
	{
		return "AccountPlayerNext";
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		if(!CMProps.getBoolVar(CMProps.Bool.MUDSTARTED))
			return CMProps.getVar(CMProps.Str.MUDSTATUS);

		final java.util.Map<String,String> parms=parseParms(parm);
		final String last=httpReq.getUrlParameter("PLAYER");
		if(parms.containsKey("RESET"))
		{
			if(last!=null)
				httpReq.removeUrlParameter("PLAYER");
			return "";
		}
		final String accountName=httpReq.getUrlParameter("ACCOUNT");
		if(accountName==null)
			return " @break@";
		final PlayerAccount account=CMLib.players().getLoadAccount(accountName);
		if(account==null)
			return "";

		String lastID="";
		String sort=httpReq.getUrlParameter("SORTBY");
		if(sort==null)
			sort="";
		final Enumeration pe=account.getThinPlayers();
		for(;pe.hasMoreElements();)
		{
			final PlayerLibrary.ThinPlayer user=(PlayerLibrary.ThinPlayer)pe.nextElement();
			if((last==null)||((last.length()>0)&&(last.equals(lastID))&&(!user.name().equals(lastID))))
			{
				httpReq.addFakeUrlParameter("PLAYER",user.name());
				return "";
			}
			lastID=user.name();
		}
		httpReq.addFakeUrlParameter("PLAYER","");
		if(parms.containsKey("EMPTYOK"))
			return "<!--EMPTY-->";
		return " @break@";
	}

}
