package ocelot.mods.utm.common.items;

import net.minecraft.item.ItemStack;

public class ItemGoldBook extends UTMItem
{

	public ItemGoldBook(int id, String name)
	{
		super(id, name);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isItemTool(ItemStack par1ItemStack)
    {
        return !par1ItemStack.isItemEnchanted();
    }
	
	@Override
	public int getItemEnchantability()
    {
        return 22;
    }
}
