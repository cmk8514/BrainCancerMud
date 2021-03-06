package com.planet_ink.coffee_mud.WebMacros;

import java.net.URLEncoder;

import com.planet_ink.coffee_web.interfaces.HTTPRequest;
import com.planet_ink.coffee_web.interfaces.HTTPResponse;

/*
   Copyright 2008-2017 Bo Zimmerman

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
public class PollID extends StdWebMacro
{
	@Override
	public String name()
	{
		return "PollID";
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		final String last=httpReq.getUrlParameter("POLL");
		if(last==null)
			return " @break@";
		final java.util.Map<String,String> parms=parseParms(parm);
		try
		{
			if(parms.containsKey("ENCODED"))
				return URLEncoder.encode(last,"UTF-8");
		}
		catch(final Exception e)
		{
		}
		return clearWebMacros(last);
	}
}
