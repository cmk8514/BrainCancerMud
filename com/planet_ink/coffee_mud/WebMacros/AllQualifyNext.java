package com.planet_ink.coffee_mud.WebMacros;

import java.util.Iterator;
import java.util.Map;

import com.planet_ink.coffee_mud.Libraries.interfaces.AbilityMapper;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_web.interfaces.HTTPRequest;
import com.planet_ink.coffee_web.interfaces.HTTPResponse;

/*
   Copyright 2011-2017 Bo Zimmerman

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
public class AllQualifyNext extends StdWebMacro
{
	@Override
	public String name()
	{
		return "AllQualifyNext";
	}

	@Override
	public boolean isAdminMacro()
	{
		return true;
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		final java.util.Map<String,String> parms=parseParms(parm);
		final String last=httpReq.getUrlParameter("ALLQUALID");
		if(parms.containsKey("RESET"))
		{
			if(last!=null)
				httpReq.removeUrlParameter("ALLQUALID");
			return "";
		}
		String which=httpReq.getUrlParameter("ALLQUALWHICH");
		if(parms.containsKey("WHICH"))
			which=parms.get("WHICH");
		if((which==null)||(which.length()==0))
			which="ALL";
		final Map<String,Map<String,AbilityMapper.AbilityMapping>> allQualMap=CMLib.ableMapper().getAllQualifiesMap(httpReq.getRequestObjects());
		final Map<String,AbilityMapper.AbilityMapping> map=allQualMap.get(which.toUpperCase().trim());
		if(map==null)
			return " @break@";

		String lastID="";
		String abilityID;
		for(final Iterator<String> i=map.keySet().iterator();i.hasNext();)
		{
			abilityID=i.next();
			if((last==null)||((last.length()>0)&&(last.equals(lastID))&&(!abilityID.equalsIgnoreCase(lastID))))
			{
				httpReq.addFakeUrlParameter("ALLQUALID",abilityID);
				return "";
			}
			lastID=abilityID;
		}
		httpReq.addFakeUrlParameter("ALLQUALID","");
		if(parms.containsKey("EMPTYOK"))
			return "<!--EMPTY-->";
		return " @break@";
	}
}
