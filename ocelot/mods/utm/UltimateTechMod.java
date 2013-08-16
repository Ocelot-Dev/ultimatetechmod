package ocelot.mods.utm;

import java.io.File;
import java.util.logging.Logger;

import ocelot.mods.utm.common.blocks.UTMBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
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
			
			Property prop = config.getBlock("Prototype_Solar_Furnace", 800);
			prototypeSolarFurnace = new UTMBlock(prop.getInt(), Material.ground) .setHardness(0.5F) .setStepSound(Block.soundMetalFootstep) .setUnlocalizedName("prototypeSolarFurnace") .setCreativeTab(CreativeTabs.tabRedstone) .func_111022_d("ultimatetechmod:prototypeSolarFurnace_top");
		}
		finally
		{
			config.save();
		}
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Utilities.FReg(prototypeSolarFurnace , "prototypeSolarFurnace", "Prototype Solar Furnace", "pickaxe", 0);
	}
}
