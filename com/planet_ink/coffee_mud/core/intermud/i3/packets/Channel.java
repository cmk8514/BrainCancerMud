package com.planet_ink.coffee_mud.core.intermud.i3.packets;
import java.io.Serializable;

import com.planet_ink.coffee_mud.core.intermud.i3.persist.Persistent;

/**
 * This class represents an I3 channel.  The ChannelList
 * class keeps a list of this channels for tracking.
 * @author George Reese (borg@imaginary.com)
 * @version 1.0
 */

/**
 * Copyright (c) 1996 George Reese
 * This source code may not be modified, copied,
 * redistributed, or used in any fashion without the
 * express written consent of George Reese.
 */
public class Channel implements Serializable
{
	public static final long serialVersionUID=0;
	/**
	 * The name of the channel
	 */
	public String channel;
	/**
	 * The modification status of this channel
	 * @see com.planet_ink.coffee_mud.core.intermud.i3.persist.Persistent
	 */
	public int modified;
	/**
	 * The mud which controls the channel
	 */
	public String owner;
	/**
	 * The type of the mud channel
	 */
	public int    type;

	/**
	 * Constructs a new mud channel object
	 */
	public Channel()
	{
		super();
		modified = Persistent.UNMODIFIED;
	}

	/**
	 * Constructs a copy of an existing channel
	 * @param other the other channel
	 */
	public Channel(Channel other)
	{
		super();
		channel = other.channel;
		modified = other.modified;
		owner = other.owner;
		type = other.type;
	}
}
