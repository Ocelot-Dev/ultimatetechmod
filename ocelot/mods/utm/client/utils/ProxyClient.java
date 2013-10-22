package ocelot.mods.utm.client.utils;

import ocelot.mods.utm.common.utils.ProxyCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;

public class ProxyClient extends ProxyCommon
{
	@Override
	public void sendMeMessage(Object[] message, boolean disable)
	{
		if (!disable)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (debug && message != null && mc.theWorld != null) mc.thePlayer.sendChatToPlayer(new ChatMessageComponent().createFromText(message.toString()));
		}
	}

	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	public String getLocalization(String text)
	{
		return I18n.getString(text);
	}
}
