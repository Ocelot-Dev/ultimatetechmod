package ocelot.mods.utm;

import java.io.File;
import java.util.logging.Logger;

import ocelot.mods.utm.client.utils.Localization;
import ocelot.mods.utm.common.CommonDefaults;
import ocelot.mods.utm.common.blocks.UTMBlock;
import ocelot.mods.utm.common.blocks.UTMBlockMachine;
import ocelot.mods.utm.common.items.UTMBlockMachineItems;
import ocelot.mods.utm.common.network.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = UltimateTechMod.id, name = "Ultimate Tech Mod")
@NetworkMod(channels = { "UTM" }, packetHandler = NetworkHandler.class, clientSideRequired = true, versionBounds = UltimateTechMod.version)
public class UltimateTechMod
{
	public static final String id = "UTM";
	public static final String version = "0.0.1 Alpha_1";
	
	@SidedProxy(clientSide = "ocelot.mods.utm.UtilitiesClient", serverSide = "ocelot.mods.utm.Utilities")
	public static Utilities util;
	
	@Mod.Instance(UltimateTechMod.id)
	public static UltimateTechMod Instance;
	
	public static Logger log = Logger.getLogger("UTM");

	public static Configuration config;
	
	//BLOCKS
	public static Block prototypeSolarFurnace;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		log.setParent(FMLLog.getLogger());
		this.log.info("Loading Ultimate Tech Mod " + version);

		config = new Configuration(new File(event.getModConfigurationDirectory(), "UltimateTechMod.cfg"));
		try
		{
			config.load();
			
			Property prop = config.getBlock("Machines1", 800);
			prototypeSolarFurnace = new UTMBlockMachine(prop.getInt(), Material.ground);
		}
		finally
		{
			config.save();
		}
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Utilities.FReg(prototypeSolarFurnace, UTMBlockMachineItems.class, "prototypeSolarFurnace", "Prototype Solar Furnace", "pickaxe", 0);
		
		Localization.addLocalization("/lang/ultimatetechmod/", "en_US");
	}
}
