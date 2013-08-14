package ocelot.mods.utm.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class HMItem extends Item 
{
	public HMItem(int id, String name) 
	{
		super(id);
		this.setUnlocalizedName("hardmachines:" + name);
	}
	
	public HMItem(int id, int stackSize, String name) 
	{
		this(id, name);
		this.setMaxStackSize(stackSize);
	}
	
	public HMItem(int id, int stackSize, int maxDamage, String name) 
	{
		this(id, stackSize, name);
		this.setMaxDamage(maxDamage);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.getUnlocalizedName());
    }

}
