package ocelot.mods.utm.common.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryMouldMaker extends UTMInventory
{
	public InventoryMouldMaker(Container container)
	{
		super(container, 2);
	}

	@Override
	public String getInvName()
	{
		return "inventory.MouldMaker";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if(slot == 0 && stack.itemID == Item.clay.itemID)
		{
			return true;
		}
		return false;
	}
}
