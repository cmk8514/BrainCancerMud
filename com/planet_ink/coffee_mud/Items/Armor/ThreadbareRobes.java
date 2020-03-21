package com.planet_ink.coffee_mud.Items.Armor;
import com.planet_ink.coffee_mud.Items.interfaces.RawMaterial;
import com.planet_ink.coffee_mud.Items.interfaces.Wearable;

public class ThreadbareRobes extends StdArmor
{
	@Override
	public String ID()
	{
		return "ThreadbareRobes";
	}

	public ThreadbareRobes()
	{
		super();
		setName("a set of worn robes");
		setDisplayText("a set of worn robes");
		setDescription("These robes are patched, yet still gape with ragged holes. Evidently having seen years of use, they are vitualy worthless");
		properWornBitmap=Wearable.WORN_TORSO | Wearable.WORN_ARMS | Wearable.WORN_LEGS;
		wornLogicalAnd=true;
		basePhyStats().setArmor(5);
		basePhyStats().setWeight(2);
		basePhyStats().setAbility(0);
		baseGoldValue=1;
		material=RawMaterial.RESOURCE_COTTON;
		recoverPhyStats();
	}

}
