package com.planet_ink.coffee_mud.core.intermud.i3.packets;
import java.util.Vector;

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
public class FingerRequest extends Packet
{
	public FingerRequest()
	{
		super();
		type = Packet.FINGER_REQUEST;
	}

	public FingerRequest(Vector v) throws InvalidPacketException {
		super(v);
		try
		{
			type = Packet.FINGER_REQUEST;
			target_mud=(String)v.elementAt(4);
			target_name=(String)v.elementAt(6);
		}
		catch( final ClassCastException e )
		{
			throw new InvalidPacketException();
		}
	}

	@Override
	public void send() throws InvalidPacketException {
		if( sender_name == null || target_mud == null || sender_mud == null  || target_name == null)
		{
			throw new InvalidPacketException();
		}
		super.send();
	}

	@Override
	public String toString()
	{
		final String cmd="({\"finger-req\",5,\"" + I3Server.getMudName() +
			   "\",\"" + sender_name + "\",\"" + target_mud + "\",0,\"" + target_name + "\",})";
		return cmd;
	}
}
