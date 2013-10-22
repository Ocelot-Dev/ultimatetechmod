package ocelot.mods.utm.common.utils;

import ocelot.mods.utm.UTM;

public class ProxyCommon
{
	public static boolean debug = true;
	
	public void sendMeMessage(Object[] message, boolean disable)
	{
		if (debug && !disable)
		{
			for(int i = 0; i < message.length; i++)
			{
				UTM.log.info(message[i].toString());
			}
		}
	}
	
	public void init(){}
	
	public String getLocalization(String text)
	{
		return "null";
	}
}
