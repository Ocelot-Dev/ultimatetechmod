package ocelot.mods.utm.client.gui;

import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;
import net.minecraft.inventory.Container;

public class GUIPrototypeSolarFurnace extends UTMGUI
{
	private TilePrototypeSolarFurnace entity;

	public GUIPrototypeSolarFurnace(Container par1Container, TilePrototypeSolarFurnace tile, String texturePath)
	{
		super(par1Container, tile, texturePath);
		entity = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) 
	{
		int leftOff = this.guiLeft;
		int topOff = this.guiTop;
		if(x > 79 + leftOff && x < 103 + leftOff && y > 34 + topOff && y < 50 + topOff)
		{
			this.drawToolTip("Per-Cent Done", entity.getScaledSmeltTime(100) + "", x - leftOff, y - topOff);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		super.drawGuiContainerBackgroundLayer(f, i, j);
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        if(entity.getCanSeeSky())
        {
        	this.drawTexturedModalRect(k + 83, l + 18, 176, 0, 14, 14);
        }
        
        int i1 = this.entity.getScaledSmeltTime(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
