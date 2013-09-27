package ocelot.mods.utm.common.utils;

import ocelot.mods.utm.UltimateTechMod;

public class ProxyCommon
{
	public static boolean debug = true;
	
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
	
	public void init(){}
}
