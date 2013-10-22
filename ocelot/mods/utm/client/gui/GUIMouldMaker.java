package ocelot.mods.utm.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import ocelot.mods.utm.client.gui.elements.ButtonMouldMaker;
import ocelot.mods.utm.common.gui.ContainerMouldMaker;
import ocelot.mods.utm.common.utils.MachineRecipes;

public class GUIMouldMaker extends UTMGUI
{
	private ContainerMouldMaker container;
	private ButtonMouldMaker next;
	private ButtonMouldMaker prev;
	private GUIWidgetTextField focalSet;

	public GUIMouldMaker(ContainerMouldMaker container)
	{
		super(container, null, "mouldMaker.png");
		this.container = container;
	}
	
	public void initGui()
    {
        super.initGui();
        this.buttonList.add(prev = new ButtonMouldMaker(0, this.guiLeft + 60, this.guiTop + 6, 12, 35, this.UTIL_TEXTURE, 48, 0));
        prev.enabled = false;
        this.buttonList.add(next = new ButtonMouldMaker(1, this.guiLeft + 104, this.guiTop + 6, 12, 35, this.UTIL_TEXTURE, 0, 0));
        this.add(focalSet = new GUIWidgetTextField(this.guiLeft + 10, this.guiTop + 20, 40, 18, "").setActionCommand("setFocus").setMaxStringLength(3).setAllowedCharacters("0123456789"));
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		super.drawGuiContainerForegroundLayer(par1, par2);
		
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 93 + 2, 4210752);
    }

	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 0)
			container.index -= 1;
		else if(button.id == 1)
			container.index += 1;
		prev.enabled = container.index != 0;
		next.enabled = container.index < MachineRecipes.mouldInfo.size() - 1;
		System.out.println(MachineRecipes.mouldInfo.size());
			
	}
	
	public void setFocalString(int distance)
	{
		focalSet.setText(Integer.toString(distance));
	}
	
	@Override
	public void actionPerformed(String command, Object... params)
	{
		if(command.equals("setFocus")) 
		{
			container.setFocalPoint(focalSet.getText());
			setFocalString(container.setFocalPoint(focalSet.getText()) ? container.focalPoint : 0);
		}
	}
}
