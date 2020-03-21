package com.planet_ink.coffee_mud.Libraries.interfaces;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.CMMsg;
import com.planet_ink.coffee_mud.Common.interfaces.Clan;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.collections.Pair;
import com.planet_ink.coffee_mud.core.interfaces.Environmental;
import com.planet_ink.coffee_mud.core.interfaces.MsgMonitor;
import com.planet_ink.coffee_mud.core.interfaces.Physical;
/*
   Copyright 2005-2017 Bo Zimmerman

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
public interface CommonCommands extends CMLibrary
{
	public void tickAging(MOB mob, long millisSinceLast);
	public int tickManaConsumption(MOB mob, int manaConsumeCounter);
	public void delGlobalMonitor(MsgMonitor M);
	public void monitorGlobalMessage(Room room, CMMsg msg);
	public void addGlobalMonitor(MsgMonitor M);
	public boolean forceStandardCommand(MOB mob, String command, List<String> parms);
	public Object forceInternalCommand(MOB mob, String command, Object... parms);
	public Object unforcedInternalCommand(MOB mob, String command, Object... parms);
	public StringBuilder getScore(MOB mob);
	public StringBuilder getEquipment(MOB viewer, MOB mob);
	public StringBuilder getInventory(MOB viewer, MOB mob);
	public void postChannel(MOB mob, String channelName, String message, boolean systemMsg);
	public void postChannel(String channelName, Iterable<Pair<Clan,Integer>> clanList, String message, boolean systemMsg);
	public boolean postDrop(MOB mob, Environmental dropThis, boolean quiet, boolean optimized, boolean intermediate);
	public boolean postGive(MOB mob, MOB targetM, Item giveThis, boolean quiet);
	public boolean postOpen(MOB mob, Environmental openThis, boolean quiet);
	public boolean postGet(MOB mob, Item container, Item getThis, boolean quiet);
	public boolean postPut(MOB mob, Item container, Item getThis, boolean quiet);
	public boolean postRemove(MOB mob, Item item, boolean quiet);
	public boolean postWear(MOB mob, Item item, boolean quiet);
	public void postLook(MOB mob, boolean quiet);
	public void postRead(MOB mob, Physical target, String readOff, boolean quiet);
	public void postFlee(MOB mob, String whereTo);
	public void postSheath(MOB mob, boolean ifPossible);
	public void postDraw(MOB mob, boolean doHold, boolean ifNecessary);
	public void postStand(MOB mob, boolean ifNecessary);
	public void postSleep(MOB mob);
	public void postFollow(MOB follower, MOB leader, boolean quiet);
	public void postSay(MOB mob, MOB target, String text, boolean isPrivate, boolean tellFlag);
	public void postSay(MOB mob, MOB target,String text);
	public void postSay(MOB mob, String text);
	public void lookAtExits(Room room, MOB mob);
	public void lookAtExitsShort(Room room, MOB mob);
	public void handleBeingLookedAt(CMMsg msg);
	public void handleBeingRead(CMMsg msg);
	public void handleRecall(CMMsg msg);
	public void handleSit(CMMsg msg);
	public void handleStand(CMMsg msg);
	public void handleSleep(CMMsg msg);
	public void handleBeingSniffed(CMMsg msg);
	public void handleBeingGivenTo(CMMsg msg);
	public void handleBeingGetted(CMMsg msg);
	public void handleBeingDropped(CMMsg msg);
	public void handleBeingRemoved(CMMsg msg);
	public void handleBeingWorn(CMMsg msg);
	public void handleBeingWielded(CMMsg msg);
	public void handleBeingHeld(CMMsg msg);
	public void handleHygienicMessage(final CMMsg msg, final int minHygiene, final long adjHygiene);
	public boolean isHygienicMessage(final CMMsg msg, final int minHygiene, final long adjHygiene);
	public String getExamineItemString(MOB mob, Item item);
	public void handleObserveComesToLife(MOB observer, MOB lifer, CMMsg msg);
	public boolean handleUnknownCommand(MOB mob, List<String> command);
	public boolean doCommandFail(final MOB mob, final List<String> commands, final String msgStr);
	public boolean doCommandFail(final MOB mob, Environmental target, Environmental tools, final List<String> command, final String msgStr);
	public void handleIntroductions(MOB speaker, MOB me, String said);
	public void handleComeToLife(MOB mob, CMMsg msg);
}
