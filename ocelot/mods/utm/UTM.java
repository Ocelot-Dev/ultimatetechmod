package ocelot.mods.utm;

import java.io.File;
import java.util.logging.Logger;

import ocelot.mods.utm.common.blocks.UTMBlockFluid;
import ocelot.mods.utm.common.blocks.UTMBlockMachine;
import ocelot.mods.utm.common.entity.TileGlassFormer;
import ocelot.mods.utm.common.entity.TileMouldMaker;
import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;
import ocelot.mods.utm.common.items.ItemLightMod;
import ocelot.mods.utm.common.items.ItemMould;
import ocelot.mods.utm.common.items.UTMBlockMachineItems;
import ocelot.mods.utm.common.items.UTMItem;
import ocelot.mods.utm.common.network.*;
import ocelot.mods.utm.common.utils.MachineRecipes;
import ocelot.mods.utm.common.utils.ProxyCommon;
import universalelectricity.compatibility.Compatibility;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = UTMConstants.MOD_ID, name = "Ultimate Tech Mod", version = UTMConstants.MOD_VERSION)
@NetworkMod(channels = { "UTM" }, packetHandler = NetworkHandler.class, clientSideRequired = true, versionBounds = UTMConstants.MOD_VERSION)
public class UTM
{
	@SidedProxy(clientSide = "ocelot.mods.utm.common.utils.ProxyCommon", serverSide = "ocelot.mods.utm.client.utils.ProxyClient")
	public static ProxyCommon	proxy;
	public static UTMConstants	constants;

	@Mod.Instance(UTMConstants.MOD_ID)
	public static UTM			Instance;

	public static Logger		log	= Logger.getLogger("UTM");

	public static Configuration	config;

	// BLOCKS
	public static Block			prototypeSolarFurnace;
	public static Block			blockFluidGlass;

	// ITEMS
	public static ItemMould		mould;
	public static ItemLightMod	lightMod;
	public static Item			glassDust;

	// FLUIDS
	public static Fluid			UTMFluidGlass;

	// public static Fluid fluidGlass;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		NetworkRegistry.instance().registerGuiHandler(Instance, new NetworkHandler());

		log.setParent(FMLLog.getLogger());
		this.log.info("Loading Ultimate Tech Mod " + UTMConstants.MOD_VERSION);

		config = new Configuration(new File(event.getModConfigurationDirectory(), "UltimateTechMod.cfg"));
		try
		{
			config.load();

			Property prop = config.getBlock("Machines1", 800);
			prototypeSolarFurnace = new UTMBlockMachine(prop.getInt(), Material.ground);

			UTMFluidGlass = new Fluid("Molten Glass");
			FluidRegistry.registerFluid(UTMFluidGlass);

			prop = config.getBlock("moltenGlass", 801);
			blockFluidGlass = new UTMBlockFluid(prop.getInt(), UTMFluidGlass, Material.water, "glass").setUnlocalizedName("block.moltenGlass");
			GameRegistry.registerBlock(blockFluidGlass, "block.moltenGlass");
			UTMFluidGlass.setBlockID(blockFluidGlass);

			prop = config.getItem("Mould", 5000);
			mould = new ItemMould(prop.getInt(), "mould");

			prop = config.getItem("LightModifier", 5001);
			lightMod = new ItemLightMod(prop.getInt(), "mod");

			prop = config.getItem("Glass_Dust", 5002);
			glassDust = new UTMItem(prop.getInt(), 1, "glassdust");
		}
		finally
		{
			if (config.hasChanged()) config.save();
		}
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Compatibility.initiate();
		MachineRecipes.InitRecipes();

		Utilities.FReg(prototypeSolarFurnace, UTMBlockMachineItems.class, "prototypeSolarFurnace", "pickaxe", 0);

		GameRegistry.registerTileEntity(TilePrototypeSolarFurnace.class, "ProtoSolar");
		GameRegistry.registerTileEntity(TileGlassFormer.class, "GlassFormer");
		GameRegistry.registerTileEntity(TileMouldMaker.class, "MouldMaker");

		MachineRecipes.InitRecipes();
	}
}
