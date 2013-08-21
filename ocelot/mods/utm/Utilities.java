package ocelot.mods.utm;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Utilities
{
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
}
