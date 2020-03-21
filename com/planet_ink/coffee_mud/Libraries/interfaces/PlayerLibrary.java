package com.planet_ink.coffee_mud.Libraries.interfaces;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.planet_ink.coffee_mud.Common.interfaces.AccountStats;
import com.planet_ink.coffee_mud.Common.interfaces.PlayerAccount;
import com.planet_ink.coffee_mud.Common.interfaces.TimeClock;
import com.planet_ink.coffee_mud.Locales.interfaces.Room;
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB;
import com.planet_ink.coffee_mud.core.CMLib;
import com.planet_ink.coffee_mud.core.collections.Pair;
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
public interface PlayerLibrary extends CMLibrary
{
	public int numPlayers();
	public void addPlayer(MOB newOne);
	public void delPlayer(MOB oneToDel);
	public MOB getPlayer(String calledThis);
	public MOB getLoadPlayer(String last);
	public MOB getLoadPlayerByEmail(String email);
	public MOB findPlayerOnline(final String srchStr, final boolean exactOnly);
	public PlayerAccount getLoadAccount(String calledThis);
	public PlayerAccount getLoadAccountByEmail(String email);
	public PlayerAccount getAccount(String calledThis);
	public void addAccount(PlayerAccount acct);
	public boolean accountExists(String name);
	public Enumeration<MOB> players();
	public Enumeration<PlayerAccount> accounts();
	public Enumeration<PlayerAccount> accounts(String sort, Map<String, Object> cache);
	public void obliteratePlayer(MOB deadMOB, boolean deleteAssets, boolean quiet);
	public void obliterateAccountOnly(PlayerAccount deadAccount);
	public void renamePlayer(MOB mob, String oldName);
	public boolean playerExists(String name);
	public void forceTick();
	public int savePlayers();
	public Enumeration<ThinPlayer> thinPlayers(String sort, Map<String, Object> cache);
	public int getCharThinSortCode(String codeName, boolean loose);
	public String getThinSortValue(ThinPlayer player, int code);
	public Set<MOB> getPlayersHere(Room room);
	public void changePlayersLocation(MOB mob, Room room);
	public Pair<Long,int[]>[] parsePrideStats(final String[] nextPeriods, final String[] prideStats);
	public int bumpPrideStat(final MOB mob, final AccountStats.PrideStat stat, final int amt);
	public List<Pair<String,Integer>> getTopPridePlayers(TimeClock.TimePeriod period, AccountStats.PrideStat stat);
	public List<Pair<String,Integer>> getTopPrideAccounts(TimeClock.TimePeriod period, AccountStats.PrideStat stat);

	public static final String[] CHAR_THIN_SORT_CODES={ "NAME","CLASS","RACE","LEVEL","AGE","LAST","EMAIL","IP"};
	public static final String[] CHAR_THIN_SORT_CODES2={ "CHARACTER","CHARCLASS","RACE","LVL","HOURS","DATE","EMAILADDRESS","LASTIP"};

	public static final String[] ACCOUNT_THIN_SORT_CODES={ "NAME","LAST","EMAIL","IP","NUMPLAYERS","EXPIRATION"};

	public static interface ThinPlayer
	{
		public String name();
		public String charClass();
		public String race();
		public int level();
		public int age();
		public long last();
		public String email();
		public String ip();
		public int exp();
		public int expLvl();
	}

	public static class ThinnerPlayer
	{
		public String name="";
		public String password="";
		public long expiration=0;
		public String accountName="";
		public String email="";
		public MOB loadedMOB=null;

		public boolean matchesPassword(String checkPass)
		{
			return CMLib.encoder().passwordCheck(checkPass, password);
		}
	}
}
