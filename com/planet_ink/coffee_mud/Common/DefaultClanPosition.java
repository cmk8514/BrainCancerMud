package com.planet_ink.coffee_mud.Common;
import java.util.List;

import com.planet_ink.coffee_mud.Common.interfaces.Clan;
import com.planet_ink.coffee_mud.Common.interfaces.ClanPosition;
import com.planet_ink.coffee_mud.core.CMClass;
import com.planet_ink.coffee_mud.core.CMParms;
import com.planet_ink.coffee_mud.core.CMath;
import com.planet_ink.coffee_mud.core.Log;
import com.planet_ink.coffee_mud.core.interfaces.CMObject;

public class DefaultClanPosition implements ClanPosition
{
	@Override
	public String ID()
	{
		return "DefaultClanPosition";
	}

	@Override
	public String name()
	{
		return ID();
	}

	/** the named ID of the position */
	protected String 	ID;
	/** the named ID of the position */
	protected int 		roleID;
	/** the ordered rank of the position */
	protected int 		rank;
	/** the name of the position within this government */
	protected String	name;
	/** the plural name of the position within this government */
	protected String	pluralName;
	/** the maximum number of members that can hold this position */
	protected int		max;
	/** the internal zapper mask for internal requirements to this position */
	protected String	innerMaskStr;
	/** the internal zapper mask for internal requirements to this position */
	protected boolean 	isPublic;
	/** a chart of whether this position can perform the indexed function in this government */
	protected Clan.Authority[] functionChart;

	/** return a new instance of the object*/
	@Override
	public CMObject newInstance()
	{
		try
		{
			return getClass().newInstance();
		}
		catch(final Exception e)
		{
			return new DefaultClanPosition();
		}
	}

	@Override
	public void initializeClass()
	{
	}

	@Override
	public int compareTo(CMObject o)
	{
		return CMClass.classID(this).compareToIgnoreCase(CMClass.classID(o));
	}

	@Override
	public CMObject copyOf()
	{
		try
		{
			return (ClanPosition)this.clone();
		}
		catch(final CloneNotSupportedException e)
		{
			return new DefaultClanPosition();
		}
	}

	@Override
	public String getID()
	{
		return ID;
	}

	@Override
	public void setID(String iD)
	{
		ID = iD;
	}

	@Override
	public int getRoleID()
	{
		return roleID;
	}

	@Override
	public void setRoleID(int roleID)
	{
		this.roleID = roleID;
	}

	@Override
	public int getRank()
	{
		return rank;
	}

	@Override
	public void setRank(int rank)
	{
		this.rank = rank;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getPluralName()
	{
		return pluralName;
	}

	@Override
	public void setPluralName(String pluralName)
	{
		this.pluralName = pluralName;
	}

	@Override
	public int getMax()
	{
		return max;
	}

	@Override
	public void setMax(int max)
	{
		this.max = max;
	}

	@Override
	public String getInnerMaskStr()
	{
		return innerMaskStr;
	}

	@Override
	public void setInnerMaskStr(String innerMaskStr)
	{
		this.innerMaskStr = innerMaskStr;
	}

	@Override
	public boolean isPublic()
	{
		return isPublic;
	}

	@Override
	public void setPublic(boolean isPublic)
	{
		this.isPublic = isPublic;
	}

	@Override
	public Clan.Authority[] getFunctionChart()
	{
		return functionChart;
	}

	@Override
	public void setFunctionChart(Clan.Authority[] functionChart)
	{
		this.functionChart = functionChart;
	}

	private static enum POS_STAT_CODES {
		ID,RANK,NAME,PLURALNAME,MAX,INNERMASK,ISPUBLIC,FUNCTIONS
	}

	@Override
	public String[] getStatCodes()
	{
		return CMParms.toStringArray(POS_STAT_CODES.values());
	}

	@Override
	public int getSaveStatIndex()
	{
		return POS_STAT_CODES.values().length;
	}

	private POS_STAT_CODES getStatIndex(String code) { return (POS_STAT_CODES)CMath.s_valueOf(POS_STAT_CODES.values(),code); }
	@Override
	public String getStat(String code)
	{
		final POS_STAT_CODES stat = getStatIndex(code);
		if(stat==null)
		{
			return "";
		}
		switch(stat)
		{
		case NAME:
			return name;
		case ID:
			return ID;
		case RANK:
			return Integer.toString(rank);
		case MAX:
			return Integer.toString(max);
		case PLURALNAME:
			return pluralName;
		case INNERMASK:
			return innerMaskStr;
		case ISPUBLIC:
			return Boolean.toString(isPublic);
		case FUNCTIONS:
		{
			final StringBuilder str = new StringBuilder("");
			for (int a = 0; a < Clan.Function.values().length; a++)
			{
				if (functionChart[a] == Clan.Authority.CAN_DO)
				{
					if (str.length() > 0)
						str.append(",");
					str.append(Clan.Function.values()[a]);
				}
			}
			return str.toString();
		}
		default:
			Log.errOut("Clan", "getStat:Unhandled:" + stat.toString());
			break;
		}
		return "";
	}

	@Override
	public boolean isStat(String code)
	{
		return getStatIndex(code)!=null;
	}

	@Override
	public void setStat(String code, String val)
	{
		final POS_STAT_CODES stat = getStatIndex(code);
		if(stat==null)
		{
			return;
		}
		switch(stat)
		{
		case NAME:
			name = val;
			break;
		case ISPUBLIC:
			isPublic = CMath.s_bool(val);
			break;
		case ID:
			ID = val.toUpperCase().trim();
			break;
		case RANK:
			rank = CMath.s_int(val);
			break;
		case MAX:
			max = CMath.s_int(val);
			break;
		case PLURALNAME:
			pluralName = val;
			break;
		case INNERMASK:
			innerMaskStr = val;
			break;
		case FUNCTIONS:
		{
			final List<String> funcs = CMParms.parseCommas(val.toUpperCase().trim(), true);
			for (int a = 0; a < Clan.Function.values().length; a++)
			{
				if (functionChart[a] != Clan.Authority.MUST_VOTE_ON)
					functionChart[a] = Clan.Authority.CAN_NOT_DO;
			}
			for (final String funcName : funcs)
			{
				final Clan.Function func = (Clan.Function) CMath.s_valueOf(Clan.Function.values(), funcName);
				if (func != null)
					functionChart[func.ordinal()] = Clan.Authority.CAN_DO;
			}
			break;
		}
		default:
			Log.errOut("Clan", "setStat:Unhandled:" + stat.toString());
			break;
		}
	}

	@Override
	public String toString()
	{
		return ID;
	}
}
