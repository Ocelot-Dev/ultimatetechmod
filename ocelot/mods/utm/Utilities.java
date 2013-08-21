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
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Utilities
{
	public static boolean debug = true;
	
	public static void FReg(Block block, String internalName, String name, String tool, int toolLevel)
	{
		LanguageRegistry.addName(block, name);
		MinecraftForge.setBlockHarvestLevel(block, tool, toolLevel);
		GameRegistry.registerBlock(block, internalName);
	}
	
	public static void FReg(Block block, Class<? extends ItemBlock> itemclass, String internalName, String name, String tool, int toolLevel)
	{
		LanguageRegistry.addName(block, name);
		MinecraftForge.setBlockHarvestLevel(block, tool, toolLevel);
		GameRegistry.registerBlock(block, itemclass, internalName);
	}
	
	public static boolean isRight(ForgeDirection side, int facing)
	{
		
		switch (facing)
		{
			case 2:
				if (side == ForgeDirection.WEST)
				{
					return true;
				}
			case 3:
				if (side == ForgeDirection.EAST)
				{
					return true;
				}
			case 4:
				if (side == ForgeDirection.SOUTH)
				{
					return true;
				}
			case 5:
				if (side == ForgeDirection.NORTH)
				{
					return true;
				}
		}
		return false;
	}
	
	public static boolean isLeft(ForgeDirection side, int facing)
	{
		
		switch (facing)
		{
			case 2:
				if (side == ForgeDirection.EAST)
				{
					return true;
				}
			case 3:
				if (side == ForgeDirection.WEST)
				{
					return true;
				}
			case 4:
				if (side == ForgeDirection.NORTH)
				{
					return true;
				}
			case 5:
				if (side == ForgeDirection.SOUTH)
				{
					return true;
				}
		}
		return false;
	}
	
	public static boolean isBack(ForgeDirection side, int facing)
	{
		
		switch (facing)
		{
			case 2:
				if (side == ForgeDirection.NORTH)
				{
					return true;
				}
			case 3:
				if (side == ForgeDirection.SOUTH)
				{
					return true;
				}
			case 4:
				if (side == ForgeDirection.NORTH)
				{
					return true;
				}
			case 5:
				if (side == ForgeDirection.EAST)
				{
					return true;
				}
		}
		return false;
	}
	
	public static void sendPacketToAll(World world, Packet data)
	{
		if (data != null)
		{
			PacketDispatcher.sendPacketToAllInDimension(data, world.provider.dimensionId);
		}
	}
	
	public boolean isSimulating()
	{
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
	
	public void sendMeMessage(Object[] message, boolean disable)
	{
		if (debug && !disable)
		{
			for(int i = 0; i < message.length; i++)
			{
				UltimateTechMod.log.info(message[i].toString());
			}
		}
	}
	
	public void init()
	{
	}
	
	public boolean IsRemote()
	{
		return FMLCommonHandler.instance().getSide().isServer();
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
	
	public String getCurrentLanguage() {
		return null;
	}	
}
