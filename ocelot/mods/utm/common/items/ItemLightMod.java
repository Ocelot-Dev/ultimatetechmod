package ocelot.mods.utm.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLightMod extends UTMItem
{
	private Icon[] icons;

	public ItemLightMod(int id, String name)
	{
		super(id, name);
		this.setHasSubtypes(true);
	}
	
	public Icon getIconFromDamage(int damage)
    {
        return icons[damage];
    }
	
	public void onBreak(EntityPlayer player, int slot)
	{
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("utm:" + this.getUnlocalizedName().replaceFirst("item.", ""));
	}
}
