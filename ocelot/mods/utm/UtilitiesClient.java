package ocelot.mods.utm;

import ocelot.mods.utm.client.utils.Localization;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.StringTranslate;

public class UtilitiesClient extends Utilities
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

	public void init()
	{
		super.init();
	}

	@Override
	public String getCurrentLanguage()
	{
		return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}
}
