package ocelot.mods.utm.common.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;

public class PacketTileUpdate extends UTMPacket
{
	public int xCoord;
	public int yCoord;
	public int zCoord;
	
	public NBTTagCompound data;

	public PacketTileUpdate()
	{
		this.packetType = 2;
	}
	public PacketTileUpdate(int id, int x, int y, int z, NBTTagCompound tag)
	{
		this.packetId = id;
		this.packetType = 2;
		this.data = tag;
		
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		
		this.isChunkDataPacket = true;
	}

	@Override
	public void readData(DataInputStream Data) throws IOException
	{
		this.packetId = Data.read();
		
		this.xCoord = Data.readInt();
		this.yCoord = Data.readInt();
		this.zCoord = Data.readInt();
		
		this.data = this.readNBTTagCompound(Data);
	}

	@Override
	public void writeData(DataOutputStream Data) throws IOException
	{
		Data.writeInt(xCoord);
		Data.writeInt(yCoord);
		Data.writeInt(zCoord);
		
		this.writeNBTTagCompound(data, Data);
	}

}
