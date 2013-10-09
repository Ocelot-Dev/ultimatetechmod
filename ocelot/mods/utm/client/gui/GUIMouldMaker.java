package ocelot.mods.utm.client.gui;

import net.minecraft.client.gui.GuiButton;
import ocelot.mods.utm.client.gui.elements.ButtonMouldMaker;
import ocelot.mods.utm.common.gui.ContainerMouldMaker;
import ocelot.mods.utm.common.utils.MachineRecipes;

public class GUIMouldMaker extends UTMGUI
{
	private ContainerMouldMaker container;
	private ButtonMouldMaker next;
	private ButtonMouldMaker prev;

	public GUIMouldMaker(ContainerMouldMaker container)
	{
		super(container, null, "mouldMaker.png");
		this.container = container;
	}
	
	public void initGui()
    {
        super.initGui();
        this.buttonList.add(prev = new ButtonMouldMaker(1, this.guiLeft + 60, this.guiTop + 6, 12, 35, this.TEXTURE, 48, 166));
        prev.enabled = false;
        this.buttonList.add(next = new ButtonMouldMaker(2, this.guiLeft + 104, this.guiTop + 6, 12, 35, this.TEXTURE, 0, 166));
    }

	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 1)
			container.index -= 1;
		else if(button.id == 2)
			container.index += 1;
		prev.enabled = container.index != 0;
		next.enabled = container.index < MachineRecipes.mouldTag.size();
			
	}
}
