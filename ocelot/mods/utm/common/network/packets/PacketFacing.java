package ocelot.mods.utm.common.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketFacing extends UTMPacket
{

	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int facing;
	
	public PacketFacing(int id, int x, int y, int z, int facing)
	{
		this.packetId = id;
		this.packetType = 1;
		this.facing = facing;
		
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		
		this.isChunkDataPacket = true;
	}
	
	public TileEntity getTarget(World world)
	{
		return world.getBlockTileEntity(xCoord, yCoord, zCoord);
	}
	
	public boolean targetExist(World world)
	{
		return world.blockHasTileEntity(xCoord, yCoord, zCoord);
	}

	@Override
	public void readData(DataInputStream data) throws IOException
	{
		this.packetType = data.readInt();
		this.packetId = data.readInt();
		
		this.xCoord = data.readInt();
		this.yCoord = data.readInt();
		this.zCoord = data.readInt();
		
		this.facing = data.readInt();
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException
	{
		data.writeInt(packetType);
		data.writeInt(packetId);
		
		data.writeInt(xCoord);
		data.writeInt(yCoord);
		data.writeInt(zCoord);
		
		data.writeInt(facing);
	}

}
