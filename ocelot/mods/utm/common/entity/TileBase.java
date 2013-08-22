package ocelot.mods.utm.common.entity;

import ocelot.mods.utm.Utilities;
import ocelot.mods.utm.common.network.packets.PacketFacing;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity
{

	protected int facing = 0;
	protected int prevFacing = 0;

	protected boolean updateFacing = false;

	public int getID()
	{
		return this.blockMetadata;
	}

	public int getFacing()
	{
		return this.facing;
	}

	public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToCustomNBT(var1);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
	}

	public void setFacing(int face)
	{
		facing = face;
		if (facing != prevFacing && !this.worldObj.isRemote)
		{
			PacketFacing packet = new PacketFacing(this.blockMetadata, this.xCoord, this.yCoord, this.zCoord, this.facing);
			Utilities.sendPacketToAll(worldObj, packet.getPacket());
		}
		prevFacing = facing;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		this.facing = tagCompound.getInteger("facing");
		this.blockMetadata = tagCompound.getInteger("meta");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("facing", facing);
		tagCompound.setInteger("meta", blockMetadata);
	}

	public void readFromCustomNBT(NBTTagCompound tagCompound)
	{
		this.facing = tagCompound.getInteger("facing");
	}

	public void writeToCustomNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setInteger("facing", facing);
	}

	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		switch (pkt.actionType)
		{
		case 1:
			this.readFromCustomNBT(pkt.customParam1);
			break;

		}
	}

	public boolean isFront(int side)
	{
		if (side == this.facing)
			return true;
		else
			return false;
	}

}
