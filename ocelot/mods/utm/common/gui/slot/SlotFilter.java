package ocelot.mods.utm.common.gui.slot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot
{
	private final List<ItemStack> filter;

	public SlotFilter(IInventory inventory, int ID, int x, int y, List<ItemStack> filter)
	{
		super(inventory, ID, x, y);
		
		this.filter = filter;
	}
	
	public SlotFilter(IInventory inventory, int ID, int x, int y, ItemStack filter)
	{
		super(inventory, ID, x, y);
		
		List<ItemStack> list = new ArrayList();
		list.add(filter);
		this.filter = list;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
        for(int i = 0; i < filter.size(); i++)
        {
        	if(filter.get(i).isItemEqual(stack))
        		return true;
        }
        return false;
    }
}
