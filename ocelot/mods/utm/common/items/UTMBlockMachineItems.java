package ocelot.mods.utm.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class UTMBlockMachineItems extends ItemBlock
{

	public UTMBlockMachineItems(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setUnlocalizedName("Machines");
	}
	
	@Override
	public int getMetadata(int damageValue)
	{
		return damageValue;
	}
	
	public int damageDropped(int damage)
    {
        return damage;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack stack)
	{
		int var1 = stack.getItemDamage();
		
		switch (var1)
		{
			case 0:
				return "block.baseblock";
			case 1:
				return "tile.solarfurnace";
			case 2:
				return "tile.glassformer";
		}
		return "info.invalidName";
	}
}
