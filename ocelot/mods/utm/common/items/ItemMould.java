package ocelot.mods.utm.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemMould extends UTMItem
{

	public ItemMould(int id, String name)
	{
		super(id, 1, name);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) 
	{
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("mouldInfo"))
		{
			
		}
		else
			list.add(StatCollector.translateToLocal("info.mouldEmpty"));
	}
}
