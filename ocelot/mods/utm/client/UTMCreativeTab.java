package ocelot.mods.utm.client;

import ocelot.mods.utm.UltimateTechMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class UTMCreativeTab extends CreativeTabs
{
	public static final CreativeTabs tab = new UTMCreativeTab("UTM");

	public UTMCreativeTab(String label)
	{
		super(label);
	}

	public UTMCreativeTab(int par1, String par2Str)
	{
		super(par1, par2Str);
	}
	
	@Override
    public ItemStack getIconItemStack() {
		return new ItemStack(UltimateTechMod.prototypeSolarFurnace, 1, 1);
	}

	@Override
    public String getTranslatedTabLabel() {
		return "Ultimate Tech Mod";
	}
}
