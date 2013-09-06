package ocelot.mods.utm.common.network;

import java.io.DataInputStream;
import java.io.IOException;

import ocelot.mods.utm.common.entity.TileBase;
import ocelot.mods.utm.common.network.packets.PacketTileUpdate;
import ocelot.mods.utm.common.network.packets.UTMPacket;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class PacketHandler
{

	public void handelFacing(DataInputStream data, World world)
	{
		int id;
		int x;
		int y;
		int z;
		int facing;
		try
		{
			id = data.read();
			x = data.read();
			y = data.read();
			z = data.read();
			facing = data.read();

			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if (tile instanceof TileBase)
			{
				TileBase tB = (TileBase) tile;
				tB.setFacing(ForgeDirection.getOrientation(facing));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void handleTileUpdate(PacketTileUpdate packet, World world)
	{
		TileEntity tile = world.getBlockTileEntity(packet.xCoord, packet.yCoord, packet.zCoord);
		if (tile instanceof TileBase)
		{
			TileBase tB = (TileBase) tile;
			tB.updateTile(packet.data);
		}
	}
}
