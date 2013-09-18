package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileInventory extends TileBase implements IInventory, ISidedInventory
{
	protected ItemStack[] inv;

	public TileInventory(int invSize)
	{
		this.inv = new ItemStack[invSize];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if ((slot < 0) || (slot >= this.inv.length))
				continue;
			this.inv[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < this.inv.length; i++)
		{
			ItemStack stack = this.inv[i];
			if (stack == null)
				continue;
			NBTTagCompound tag = new NBTTagCompound();
			tag.setByte("Slot", (byte) i);
			stack.writeToNBT(tag);
			itemList.appendTag(tag);
		}
		
		tagCompound.setTag("Inventory", itemList);
	}
	
	public abstract void getGUINetworkData(int i, int data);
	
	public abstract void sendGUINetworkData(Container container, ICrafting iCrafting);

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
		if (this.inv[slot] != null)
        {
            ItemStack itemstack;

            if (this.inv[slot].stackSize <= amount)
            {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inv[slot].splitStack(amount);

                if (this.inv[slot].stackSize == 0)
                {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return inv[i];
	}

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
	public void openChest(){}

	@Override
	public void closeChest(){}
}
