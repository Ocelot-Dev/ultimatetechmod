package ocelot.mods.utm;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = UltimateTechMod.id, name = "Ultimate Tech Mod")
public class UltimateTechMod
{
	public static final String id = "UTM";
	public static final String version = "0.0.1 Alpha_1";
	
	@Mod.Instance(UltimateTechMod.id)
	public static UltimateTechMod Instance;
	
	public static Logger log = Logger.getLogger("UTM");

	public static Configuration config;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		log.setParent(FMLLog.getLogger());
		this.log.info("Loading Ultimate Tech Mod " + version);

		config = new Configuration(new File(event.getModConfigurationDirectory(), "UltimateTechMod.cfg"));
		try
		{
			config.load();
		}
		finally
		{
			config.save();
		}
	}
	
	public void FReg(Block block, String internalName, String name, String tool, int toolLevel)
	{
		LanguageRegistry.addName(block, name);
		MinecraftForge.setBlockHarvestLevel(block, tool, toolLevel);
		GameRegistry.registerBlock(block, internalName);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		
		FReg(ocelot.mods.utm.common.blocks.UTMBlock.prototypeSolarFurnace , "prototypeSolarFurnace", "Prototype Solar Furnace", "shovel", 0);
		
	}
}
