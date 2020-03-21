package com.planet_ink.coffee_mud.core.intermud.i3.packets;
import java.util.Vector;

import com.planet_ink.coffee_mud.core.intermud.i3.server.I3Server;

/**
 * Copyright (c) 1996 George Reese
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
public class ChannelAdd extends Packet  {
	public String channel = null;

	public ChannelAdd()
	{
		super();
		type = Packet.CHAN_ADD;
	}

	public ChannelAdd(Vector v) throws InvalidPacketException {
		super(v);
		try
		{
			type = Packet.CHAN_ADD;
			channel = (String)v.elementAt(6);
		}
		catch( final ClassCastException e )
		{
			throw new InvalidPacketException();
		}
	}

	public ChannelAdd(int t, String chan, String who)
	{
		super();
		type = t;
		channel = chan;
		sender_name = who;
	}

	@Override
	public void send() throws InvalidPacketException {
		if( channel == null  )
		{
			throw new InvalidPacketException();
		}
		super.send();
	}

	@Override
	public String toString()
	{
		final NameServer n = Intermud.getNameServer();
		final String cmd=
			 "({\"channel-add\",5,\"" + I3Server.getMudName() + "\",\"" +
			   sender_name + "\",\""+n.name+"\",0,\"" + channel + "\",0,})";
		return cmd;
	}
}
