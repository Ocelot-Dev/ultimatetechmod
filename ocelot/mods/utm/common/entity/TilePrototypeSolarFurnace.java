package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TilePrototypeSolarFurnace extends TileInventory
{
	private boolean canWork = false;

	public TilePrototypeSolarFurnace(int invSize)
	{
		super(invSize);
		// TODO Auto-generated constructor stub
	}
	
	public void updateEntity() 
	{
		if(!this.worldObj.isRaining() && this.worldObj.isDaytime() && this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord))
		{
			canWork = true;
		}
		else
			canWork = false;
	}
	
	public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        canWork = tag.getBoolean("seeSky");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setBoolean("seeSky", this.canWork);
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
