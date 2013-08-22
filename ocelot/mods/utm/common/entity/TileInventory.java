package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class TileInventory extends TileBase implements IInventory
{
	protected ItemStack[] inv;

	public TileInventory(int invSize)
	{
		this.inv = new ItemStack[invSize];
	}

	@Override
	public int getSizeInventory()
	{
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inv[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return inv[i];
	}

	@Override
	public abstract void setInventorySlotContents(int i, ItemStack itemstack);

	@Override
	public abstract String getInvName();

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public abstract boolean isUseableByPlayer(EntityPlayer entityplayer);

	@Override
	public void openChest()
	{}

	@Override
	public void closeChest()
	{}

	@Override
	public abstract boolean isItemValidForSlot(int i, ItemStack itemstack);

}
