package ocelot.mods.utm.client;

import net.minecraft.creativetab.CreativeTabs;

public class UTMCreativeTab extends CreativeTabs
{
	public static final CreativeTabs tab = new UTMCreativeTab("UTM");

	public UTMCreativeTab(String label)
	{
		super(label);
		// TODO Auto-generated constructor stub
	}

	public UTMCreativeTab(int par1, String par2Str)
	{
		super(par1, par2Str);
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getTranslatedTabLabel() {
		return "Ultimate Tech Mod";
	}
}
