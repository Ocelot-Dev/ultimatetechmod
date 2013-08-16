package ocelot.mods.utm;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
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
}
