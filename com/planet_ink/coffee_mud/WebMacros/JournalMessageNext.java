package com.planet_ink.coffee_mud.WebMacros;

import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.Clan;
import com.planet_ink.coffee_mud.Common.interfaces.JournalEntry;
import com.planet_ink.coffee_mud.Libraries.interfaces.JournalsLibrary;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.CMSecurity;
import com.planet_ink.coffee_mud.core.CMath;
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
public class JournalMessageNext extends StdWebMacro
{
	@Override
	public String name()
	{
		return "JournalMessageNext";
	}

	@Override
	public String runMacro(HTTPRequest httpReq, String parm, HTTPResponse httpResp)
	{
		final java.util.Map<String,String> parms=parseParms(parm);
		final String journalName=httpReq.getUrlParameter("JOURNAL");
		if(journalName==null)
			return " @break@";

		if(CMLib.journals().isArchonJournalName(journalName))
		{
			final MOB M = Authenticate.getAuthenticatedMob(httpReq);
			if((M==null)||(!CMSecurity.isASysOp(M)))
				return " @break@";
		}

		String srch=httpReq.getUrlParameter("JOURNALMESSAGESEARCH");
		if(srch!=null)
			srch=srch.toLowerCase();
		String last=httpReq.getUrlParameter("JOURNALMESSAGE");
		int cardinal=CMath.s_int(httpReq.getUrlParameter("JOURNALCARDINAL"));
		if(parms.containsKey("RESET"))
		{
			if(last!=null)
			{
				httpReq.removeUrlParameter("JOURNALMESSAGE");
				httpReq.removeUrlParameter("JOURNALCARDINAL");
			}
			return "";
		}
		final MOB M = Authenticate.getAuthenticatedMob(httpReq);
		cardinal++;
		JournalEntry entry = null;
		final String page=httpReq.getUrlParameter("JOURNALPAGE");
		final String mpage=httpReq.getUrlParameter("MESSAGEPAGE");
		final String parent=httpReq.getUrlParameter("JOURNALPARENT");
		final String dbsearch=httpReq.getUrlParameter("DBSEARCH");
		final Clan setClan=CMLib.clans().getClan(httpReq.getUrlParameter("CLAN"));
		final JournalsLibrary.ForumJournal journal= CMLib.journals().getForumJournal(journalName,setClan);
		final List<JournalEntry> msgs=JournalInfo.getMessages(journalName,journal,page,mpage,parent,dbsearch,httpReq.getRequestObjects());
		while((entry==null)||(!CMLib.journals().canReadMessage(entry,srch,M,parms.containsKey("NOPRIV"))))
		{
			entry = JournalInfo.getNextEntry(msgs,last);
			if(entry==null)
			{
				httpReq.addFakeUrlParameter("JOURNALMESSAGE","");
				if(parms.containsKey("EMPTYOK"))
					return "<!--EMPTY-->";
				return " @break@";
			}
			last=entry.key();
		}
		entry.cardinal(cardinal);
		httpReq.addFakeUrlParameter("JOURNALCARDINAL",""+cardinal);
		httpReq.addFakeUrlParameter("JOURNALMESSAGE",last);
		return "";
	}
}
