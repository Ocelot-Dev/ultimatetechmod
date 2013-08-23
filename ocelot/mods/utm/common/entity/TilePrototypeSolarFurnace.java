package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TilePrototypeSolarFurnace extends TileInventory
{

	public TilePrototypeSolarFurnace(int invSize)
	{
		super(invSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getInvName()
	{
		return "inv.PrototypeSolarFurnace";
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		// TODO Auto-generated method stub
		return true;
	}

}
