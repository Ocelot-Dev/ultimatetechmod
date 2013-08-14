package ocelot.mods.utm;

import java.io.File;
import java.util.logging.Logger;

import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = HardMachines.id, name = "Hard Machines")
public class HardMachines 
{
	public static Logger log = Logger.getLogger("HardMachines");
	
	@Mod.Instance(HardMachines.id)
	public static HardMachines Instance;
		
	public static final String id = "HardMachines";
	public static final String version = "0.0.1 Alpha_1";
	
	public static Configuration config;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		log.setParent(FMLLog.getLogger());
		this.log.info("Loading Hand Machines " + version);
		
		config = new Configuration(new File(event.getModConfigurationDirectory(), "HardMachines.cfg"));
		try
		{
			config.load();
		}
		finally
		{
			config.save();
		}
	}
}
