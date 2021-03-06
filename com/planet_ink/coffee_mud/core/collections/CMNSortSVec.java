package com.planet_ink.coffee_mud.core.collections;

import com.planet_ink.coffee_mud.core.interfaces.CMObject;

public class CMNSortSVec<T extends CMObject> extends SortedStrSVector<T> implements SearchIDList<T>
{
	private static final long serialVersionUID = 6687178785122361992L;

	@SuppressWarnings("rawtypes")
	private static final SortedStrSVector.Str idStringer=new SortedStrSVector.Str<CMObject>()
	{
		@Override
		public String toString(CMObject t)
		{
			return t.name();
		}
	};

	@SuppressWarnings("unchecked")
	public CMNSortSVec(int size)
	{
		super(idStringer,size);
	}

	@SuppressWarnings("unchecked")
	public CMNSortSVec()
	{
		super(idStringer);
	}
}
