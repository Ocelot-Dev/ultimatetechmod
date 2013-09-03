package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class TilePrototypeSolarFurnace extends TileInventory
{
	private boolean canSeeSky = false;
	public int smelttime;
	private int cooktime = 1600;

	public TilePrototypeSolarFurnace()
	{
		super(2);
	}
	
	public int getScaledSmeltTime(int size)
	{
		return smelttime * size / cooktime;
	}
	
	public void updateEntity() 
	{
		if(!this.worldObj.isRemote)
		{
			if(this.worldObj.isRaining() || !this.worldObj.isDaytime() || !this.worldObj.canBlockSeeTheSky(this.yCoord, this.xCoord, this.zCoord))
			{
				this.canSeeSky = false;
				this.smelttime = 0;
				return;
			}
			this.canSeeSky = true;
			
			if(this.canSmelt())
			{
				smelttime++;
				if(smelttime == cooktime)
				{
					smelttime = 0;
					this.smeltItem();
				}
			}
			else
				this.smelttime = 0;
		}
	}
	
	private boolean canSmelt()
    {
        if (this.inv[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inv[0]);
            if (itemstack == null) return false;
            if (this.inv[1] == null) return true;
            if (!this.inv[1].isItemEqual(itemstack)) return false;
            int result = inv[1].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }
	
	public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inv[0]);

            if (this.inv[1] == null)
            {
                this.inv[1] = itemstack.copy();
            }
            else if (this.inv[1].isItemEqual(itemstack))
            {
                inv[1].stackSize += itemstack.stackSize;
            }

            --this.inv[0].stackSize;

            if (this.inv[0].stackSize <= 0)
            {
                this.inv[0] = null;
            }
        }
    }
	
	public boolean getCanSeeSky()
	{
		return this.canSeeSky;
	}
	
	public void setCanSeeSky(int bool)
	{
		this.canSeeSky = (bool == 1 ? true : false); 
	}
	
	public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        smelttime = tag.getInteger("smeltTime");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("smeltTime", smelttime);
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		inv[i] = itemstack;

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
		if(this.inv[i].equals(itemstack) || this.inv[i].equals(null) || this.inv[i].equals(new ItemStack(0, 0, 0)))
			return true;
		else 
			return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		int[] slots = new int[1];
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side)
	{
		if(slot == 0 && inv[slot] == null && FurnaceRecipes.smelting().getSmeltingResult(item) != null)
			return true;
		else if(slot == 0 && item != null && FurnaceRecipes.smelting().getSmeltingResult(item) != null && inv[slot].isItemEqual(item))
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		if(slot == 1)
			return true;
		return false;
	}

}
