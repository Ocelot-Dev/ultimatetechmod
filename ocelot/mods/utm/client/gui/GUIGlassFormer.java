package ocelot.mods.utm.client.gui;

import net.minecraft.inventory.Container;
import ocelot.mods.utm.common.entity.TileBase;
import ocelot.mods.utm.common.entity.TileGlassFormer;

public class GUIGlassFormer extends UTMGUI
{
	private TileGlassFormer tile;

	public GUIGlassFormer(Container par1Container, TileGlassFormer entity, String texturePath)
	{
		super(par1Container, entity, texturePath);
		
		this.tile = entity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) 
	{
		int leftOff = this.guiLeft;
		int topOff = this.guiTop;
		if(x > 7 + leftOff && x < 11 + leftOff && y > 9 + topOff && y < 75 + topOff)
		{
			this.drawToolTip( tile.storedEnergy + " J", "", x - leftOff, y - topOff);
		}
		if(x > 16 + leftOff && x < 20 + leftOff && y > 9 + topOff && y < 75 + topOff)
		{
			this.drawToolTip( tile.temp + " C", "", x - leftOff, y - topOff);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		super.drawGuiContainerBackgroundLayer(f, i, j);
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        int i1 = this.tile.getScaledEnergy(67);
        drawTexturedModalRect(x + 7, y + 8 + 67 - i1, 176, 84 - i1, 4, i1);
        
        int i2 = this.tile.getScaledTemp(67);
        drawTexturedModalRect(x + 16, y + 8 + 67 - i2, 180, 84 - i2, 4, i2);
	}
}
