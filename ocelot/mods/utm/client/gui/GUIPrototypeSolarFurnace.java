package ocelot.mods.utm.client.gui;

import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;
import net.minecraft.inventory.Container;
import net.minecraftforge.fluids.FluidRegistry;

public class GUIPrototypeSolarFurnace extends UTMGUI
{
	private TilePrototypeSolarFurnace entity;

	public GUIPrototypeSolarFurnace(Container par1Container, TilePrototypeSolarFurnace tile)
	{
		super(par1Container, tile, "textures/gui/PrototypeSolarfurnace.png");
		entity = tile;
	}
	
	@Override
	public void drawScreen(int x, int y, float par3)
	{
		super.drawScreen(x, y, par3);
		
		if(this.isPointInRegion(7, 9, 4, 67, x, y))
		{
			this.drawToolTip( "Per-Cent Done", entity.getScaledSmeltTime(100) + "", x, y);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		super.drawGuiContainerBackgroundLayer(f, i, j);
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        if(entity.getIsOn())
        {
        	this.drawTexturedModalRect(k + 83, l + 18, 176, 0, 14, 14);
        }
        
        int i1 = this.entity.getScaledSmeltTime(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
