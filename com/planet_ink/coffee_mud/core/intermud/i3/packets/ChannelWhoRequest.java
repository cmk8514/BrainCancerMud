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
public class ChannelWhoRequest extends Packet {
	public String channel = null;

	public ChannelWhoRequest()
	{
		super();
		type = Packet.CHAN_WHO_REQ;
	}

	public ChannelWhoRequest(Vector v) throws InvalidPacketException {
		super(v);
		try
		{
			type = Packet.CHAN_WHO_REQ;
			channel = (String)v.elementAt(6);
			channel = Intermud.getLocalChannel(channel);
		}
		catch( final ClassCastException e )
		{
			throw new InvalidPacketException();
		}
	}

	@Override
	public void send() throws InvalidPacketException {
		if( sender_name == null || target_mud == null || sender_mud == null  || channel == null)
		{
			throw new InvalidPacketException();
		}
		channel = Intermud.getRemoteChannel(channel);
		super.send();
	}

	@Override
	public String toString()
	{
		final String cmd="({\"chan-who-req\",5,\"" + I3Server.getMudName() +
			   "\",\"" + sender_name + "\",\"" + target_mud + "\",0,\"" + channel + "\",})";
		return cmd;
	}
}
