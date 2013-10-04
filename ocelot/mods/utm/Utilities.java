package ocelot.mods.utm;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;

public class Utilities
{
	public static void FReg(Block block, String internalName, String tool, int toolLevel)
	{
		MinecraftForge.setBlockHarvestLevel(block, tool, toolLevel);
		GameRegistry.registerBlock(block, internalName);
	}
	
	public static void FReg(Block block, Class<? extends ItemBlock> itemclass, String internalName, String tool, int toolLevel)
	{
		MinecraftForge.setBlockHarvestLevel(block, tool, toolLevel);
		GameRegistry.registerBlock(block, itemclass, internalName);
	}
	
	public static ForgeDirection getNextSide(ForgeDirection side)
	{
		int sideNum = getDirectionInt(side);
		if(sideNum == 2)
			return ForgeDirection.getOrientation(5);
		if(sideNum == 3)
			return ForgeDirection.getOrientation(4);
		if(sideNum == 4)
			return ForgeDirection.getOrientation(2);
		if(sideNum == 5)
			return ForgeDirection.getOrientation(3);
		return ForgeDirection.UNKNOWN;
	}
	
	public static ForgeDirection getPrevSide(ForgeDirection side)
	{
		int sideNum = getDirectionInt(side);
		if(sideNum == 2)
			return ForgeDirection.getOrientation(4);
		else if(sideNum == 3)
			return ForgeDirection.getOrientation(5);
		else if(sideNum == 4)
			return ForgeDirection.getOrientation(3);
		else if(sideNum == 5)
			return ForgeDirection.getOrientation(2);
		return ForgeDirection.UNKNOWN;
	}
	
	public static int getDirectionInt(ForgeDirection direction)
	{
		if(direction.equals(ForgeDirection.DOWN))
			return 0;
		if(direction.equals(ForgeDirection.UP))
			return 1;
		if(direction.equals(ForgeDirection.NORTH))
			return 2;
		if(direction.equals(ForgeDirection.SOUTH))
			return 3;
		if(direction.equals(ForgeDirection.WEST))
			return 4;
		if(direction.equals(ForgeDirection.EAST))
			return 5;
		return -1;
	}
	
	public static ForgeDirection getIntDirection(int direction)
	{
		if(direction == 0)
			return ForgeDirection.DOWN;
		if(direction == 1)
			return ForgeDirection.UP;
		if(direction == 2)
			return ForgeDirection.NORTH;
		if(direction == 3)
			return ForgeDirection.SOUTH;
		if(direction == 4)
			return ForgeDirection.WEST;
		if(direction == 5)
			return ForgeDirection.EAST;
		return ForgeDirection.UNKNOWN;
	}
	
	public static boolean isRight(ForgeDirection side, ForgeDirection facing)
	{
		if(getNextSide(facing).equals(side))
			return true;
		return false;
	}
	
	public static boolean isLeft(ForgeDirection side, ForgeDirection facing)
	{
		if(getPrevSide(facing).equals(side))
			return true;
		return false;
	}
	
	public static boolean isBack(ForgeDirection side, ForgeDirection facing)
	{
		if(facing.getOpposite().equals(side))
		{	
			return true;
		}
		return false;
	}
	
	public static void sendPacketToAllAround(World world, int x, int y, int z, int range,  Packet data)
	{
		if (data != null)
		{
			PacketDispatcher.sendPacketToAllAround(x, y, z, range, world.provider.dimensionId, data);
		}
	}
	
	
	
	public void sendToPlayers(Packet250CustomPayload packet, World world, int x, int y, int z, int maxDistance) 
	{
		if (packet != null) {
			if(world.playerEntities.get(0) instanceof EntityPlayerMP)
			for (int j = 0; j < world.playerEntities.size(); j++) {
				EntityPlayerMP player = (EntityPlayerMP) world.playerEntities.get(j);

				if (Math.abs(player.posX - x) <= maxDistance && Math.abs(player.posY - y) <= maxDistance && Math.abs(player.posZ - z) <= maxDistance) 
				{
					player.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
			
			else if(world.playerEntities.get(0) instanceof EntityClientPlayerMP)
			for (int j = 0; j < world.playerEntities.size(); j++) {
				EntityClientPlayerMP player = (EntityClientPlayerMP) world.playerEntities.get(j);

				if (Math.abs(player.posX - x) <= maxDistance && Math.abs(player.posY - y) <= maxDistance && Math.abs(player.posZ - z) <= maxDistance) 
				{
					player.sendQueue.addToSendQueue(packet);
				}
			}
		}
	}	
}
