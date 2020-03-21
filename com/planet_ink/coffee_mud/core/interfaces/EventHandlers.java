package com.planet_ink.coffee_mud.core.interfaces;
import com.planet_ink.coffee_mud.Abilities.interfaces.Ability;
import com.planet_ink.coffee_mud.Common.interfaces.Social;
import com.planet_ink.coffee_mud.Exits.interfaces.Exit;
import com.planet_ink.coffee_mud.Items.interfaces.Container;
import com.planet_ink.coffee_mud.Items.interfaces.Item;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;

/**
 * Currently unused.  Move along.
 * @author Bo Zimmerman
 *
 */
public interface EventHandlers extends MsgListener, StatsAffecting
{
	public boolean approveGetItem(MOB mob, Item item);
	public void executeGetItem(MOB mob, Item item);

	public boolean approveGetItemFrom(MOB mob, Item item, Container container);
	public void executeGetItemFrom(MOB mob, Item item, Container container);

	public boolean approveDropItem(MOB mob, Item item);
	public void executeDropItem(MOB mob, Item item);

	public boolean approveRemoveItem(MOB mob, Item item);
	public void executeRemoveItem(MOB mob, Item item);

	public boolean approveWearItem(MOB mob, Item item);
	public void executeWearItem(MOB mob, Item item);

	public boolean approveEatItem(MOB mob, Environmental item);
	public void executeEatItem(MOB mob, Environmental item);

	public boolean approveDrinkItem(MOB mob, Drink item);
	public void executeDrinkItem(MOB mob, Drink item);

	public boolean approvePutItem(MOB mob, Item item, Container container);
	public void executePutItem(MOB mob, Item item, Container container);

	public boolean approveGiveItem(MOB mob, MOB to, Item item);
	public void executeGiveItem(MOB mob, MOB to, Item item);

	public boolean approveBuyItem(MOB mob, ShopKeeper from, Item item);
	public void executeBuyItem(MOB mob, ShopKeeper from, Item item);

	public boolean approveSellItem(MOB mob, ShopKeeper to, Item item);
	public void executeSellItem(MOB mob, ShopKeeper to, Item item);

	public boolean approveSay(MOB mob, String say);
	public void executeSay(MOB mob, String say);

	public boolean approveChannel(MOB mob, String channel, String say);
	public void executeChannel(MOB mob, String channel, String say);

	public boolean approveEnterRoom(MOB mob, Room room, Exit exit);
	public void executeEnterRoom(MOB mob, Room room, Exit exit);

	public boolean approveLeaveRoom(MOB mob, Room room, Exit exit);
	public void executeLeaveRoom(MOB mob, Room room, Exit exit);

	public boolean approveSocial(MOB mob, Environmental target, Social S);
	public void executeSocial(MOB mob, Environmental target, Social S);

	public boolean approveEmote(MOB mob, String emote);
	public void executeEmote(MOB mob, String emote);

	public boolean approveAttack(MOB mob, MOB victim);
	public void executeAttack(MOB mob, MOB victim);

	public boolean approveDamage(MOB attacker, MOB victim, int[] damage);
	public void executeDamage(MOB attacker, MOB victim, int[] damage);

	public boolean approveGainLevel(MOB mob);
	public void executeGainLevel(MOB mob);

	public boolean approveLogin(MOB mob);
	public void executeLogin(MOB mob);

	public boolean approveLogout(MOB mob);
	public void executeLogout(MOB mob);

	public boolean approveGainExp(MOB mob, int[] amount);
	public void executeGainExp(MOB mob, int[] amount);

	public boolean approveDeath(MOB mob);
	public void executeDeath(MOB mob);

	public boolean approveSkillUse(MOB mob, Ability skill, MOB victim);
	public void executeSkillUse(MOB mob, Ability skill, MOB victim);
}
