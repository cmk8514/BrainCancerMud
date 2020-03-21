package com.planet_ink.coffee_mud.core.intermud.i3.packets;
import java.util.Vector;

import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.intermud.i3.server.I3Server;

/**
 * Copyright (c) 2010-2017 Bo Zimmerman
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
@SuppressWarnings("rawtypes")
public class MudAuthReply extends Packet
{
	public long key=0;

	public MudAuthReply()
	{
		super();
		type = Packet.MAUTH_REPLY;
		target_mud=I3Server.getMudName();
	}

	public MudAuthReply(Vector v)
	{
		super(v);
		type = Packet.MAUTH_REPLY;
		target_mud=(String)v.elementAt(4);
		key=CMath.s_int(v.elementAt(6).toString());
	}

	public MudAuthReply(String mud, long key)
	{
		super();
		type = Packet.MAUTH_REPLY;
		target_mud=mud;
		this.key=key;
	}

	@Override
	public void send() throws InvalidPacketException
	{
		super.send();
	}

	@Override
	public String toString()
	{
		return "({\"auth-mud-reply\",5,\""+I3Server.getMudName()+"\",0,\""+target_mud+"\",0,"+key+",})";
	}
}
