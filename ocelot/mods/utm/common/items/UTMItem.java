package ocelot.mods.utm.common.items;

import ocelot.mods.utm.client.utils.Localization;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UTMItem extends Item
{
	public UTMItem(int id, String name)
	{
		super(id);
		this.setUnlocalizedName(name);
	}

	public UTMItem(int id, int stackSize, String name)
	{
		this(id, name);
		this.setMaxStackSize(stackSize);
	}

	public UTMItem(int id, int stackSize, int maxDamage, String name)
	{
		this(id, stackSize, name);
		this.setMaxDamage(maxDamage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("ultimatetechmod:" + this.getUnlocalizedName().replaceFirst("item.", ""));
	}
}
