package com.planet_ink.coffee_mud.Items.Basic;

import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.core.CMLib;

/*
   Copyright 2002-2017 Bo Zimmerman

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
public class GenResource extends GenItem implements RawMaterial
{
	@Override
	public String ID()
	{
		return "GenResource";
	}

	public GenResource()
	{
		super();
		setName("a pile of resources");
		setDisplayText("a pile of resources sits here.");
		setDescription("");
		setMaterial(RawMaterial.RESOURCE_IRON);
		basePhyStats().setWeight(0);
		recoverPhyStats();
	}

	protected String	domainSource	= null;

	@Override
	public String domainSource()
	{
		return domainSource;
	}

	@Override
	public void setDomainSource(String src)
	{
		domainSource = src;
	}

	@Override
	public boolean rebundle()
	{
		return CMLib.materials().rebundle(this);
	}

	@Override
	public void quickDestroy()
	{
		CMLib.materials().quickDestroy(this);
	}
}
