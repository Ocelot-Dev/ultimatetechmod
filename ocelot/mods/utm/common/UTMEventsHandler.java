package ocelot.mods.utm.common;

import java.util.Random;

import ocelot.mods.utm.UTM;
import ocelot.mods.utm.common.items.ItemLightMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class UTMEventsHandler
{

	public UTMEventsHandler()
	{
		// TODO Auto-generated constructor stub
	}
	
	@ForgeSubscribe
	public void fallEvent(LivingFallEvent event)
	{
		if(event.distance >= 4 && event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			if(player.inventory.hasItem(UTM.lightMod.itemID))
			{
				Random rand = new Random();
				if(rand.nextInt(100) + event.distance > 90)
				{
					for(int i = 0; i > player.inventory.mainInventory.length; i++)
					{
						if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemLightMod)
						{
							ItemLightMod mod = (ItemLightMod) player.inventory.mainInventory[i].getItem();
							mod.onBreak(player, i);
						}
					}
				}
			}
		}
	}

}
