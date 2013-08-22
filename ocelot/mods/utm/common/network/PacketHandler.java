package ocelot.mods.utm.common.network;

import java.io.DataInputStream;
import java.io.IOException;

import ocelot.mods.utm.common.entity.TileBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
			id = data.readInt();
			x = data.readInt();
			y = data.readInt();
			z = data.readInt();
			facing = data.readInt();
			
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile instanceof TileBase)
			{
				TileBase tB = (TileBase) tile;
				tB.setFacing(facing);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
