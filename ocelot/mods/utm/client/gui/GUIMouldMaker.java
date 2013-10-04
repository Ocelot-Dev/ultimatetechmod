package ocelot.mods.utm.client.gui;

import net.minecraft.client.gui.GuiButton;
import ocelot.mods.utm.client.gui.elements.ButtonMouldMaker;
import ocelot.mods.utm.common.gui.ContainerMouldMaker;

public class GUIMouldMaker extends UTMGUI
{

	public GUIMouldMaker(ContainerMouldMaker par1Container)
	{
		super(par1Container, null, "mouldMaker.png");
	}
	
	public void initGui()
    {
        super.initGui();
        this.buttonList.add(new ButtonMouldMaker(1, this.guiLeft + 60, this.guiTop + 6, 12, 35, this.TEXTURE, 0, 166));
        this.buttonList.add(new ButtonMouldMaker(2, this.guiLeft + 101, this.guiTop + 6, 12, 35, this.TEXTURE, 48, 166));
    }

	protected void actionPerformed(GuiButton par1GuiButton)
	{
		
	}
}
