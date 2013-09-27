package ocelot.mods.utm.common.entity;

import buildcraft.api.transport.IPipeTile.PipeType;
import ocelot.mods.utm.Utilities;
import ocelot.mods.utm.common.network.packets.PacketTileUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class TilePrototypeSolarFurnace extends TileInventory
{
	private boolean isOn = false;
	private boolean prevIsOn = false;
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

	@Override
	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			shouldSendUpdate();
			if (this.canSmelt())
			{
				smelttime++;
				if (smelttime == cooktime)
				{
					smelttime = 0;
					this.smeltItem();
				}
			}
			else
			{
				if (smelttime != 0) smelttime--;
				super.onInventoryChanged();
			}
		}
	}

	private boolean canSmelt()
	{
		if (this.worldObj.isRaining() || !this.worldObj.isDaytime() || !this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord))
		{
			this.isOn = false;
			return false;
		}
		this.isOn = true;

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

	public boolean getIsOn()
	{
		return this.isOn;
	}

	public void setIsOn(int bool)
	{
		this.isOn = (bool == 1 ? true : false);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		smelttime = tag.getInteger("smeltTime");
		isOn = tag.getBoolean("isOn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("smeltTime", smelttime);
		tag.setBoolean("isOn", this.isOn);
	}
	
	@Override
	public void readFromCustomNBT(NBTTagCompound tag)
	{
		this.facing =  ForgeDirection.getOrientation(tag.getInteger("facing"));
		this.isOn = tag.getBoolean("isOn");
	}

	@Override
	public void writeToCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("facing", Utilities.getDirectionInt(facing));
		tag.setBoolean("isOn", this.isOn);
	}
	
	public void shouldSendUpdate()
	{
		if(this.isOn != this.prevIsOn || !this.facing.equals(prevFacing))
		{
			NBTTagCompound tag = new NBTTagCompound();
			writeToCustomNBT(tag);
			PacketTileUpdate packet = new PacketTileUpdate(this.getID(), this.xCoord, this.yCoord, this.zCoord, tag);
			Utilities.sendPacketToAllAround(worldObj, this.xCoord, this.yCoord, this.zCoord, 30, packet.getPacket());
			prevIsOn = isOn;
			prevFacing = facing;
		}
			
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
		if (this.inv[i].equals(itemstack) || this.inv[i].equals(null) || this.inv[i].equals(new ItemStack(0, 0, 0)))
			return true;
		else
			return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		int[] slots = new int[1];
		slots[0] = 1;
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side)
	{
		if (slot == 0 && inv[slot] == null && FurnaceRecipes.smelting().getSmeltingResult(item) != null)
			return true;
		else if (slot == 0 && item != null && FurnaceRecipes.smelting().getSmeltingResult(item) != null && inv[slot].isItemEqual(item)) return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		if (slot == 1) return true;
		return false;
	}

	@Override
	public boolean doesSideNotChangeActive(ForgeDirection side)
	{
		if (!this.isOn) return true;
		if (this.facing.getOpposite().equals(side) || side.equals(ForgeDirection.UP) || side.equals(ForgeDirection.DOWN)) return true;
		return false;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		if (type == PipeType.ITEM)
			return ConnectOverride.DEFAULT;
		return ConnectOverride.DISCONNECT;
	}

	@Override
	public void getGUINetworkData(int i, int data){}

	@Override
	public void sendGUINetworkData(Container container, ICrafting iCrafting){}
}