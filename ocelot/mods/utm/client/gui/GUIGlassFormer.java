package ocelot.mods.utm.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraftforge.fluids.FluidStack;
import ocelot.mods.utm.common.entity.TileGlassFormer;

public class GUIGlassFormer extends UTMGUI
{
	private TileGlassFormer tile;

	public GUIGlassFormer(Container par1Container, TileGlassFormer entity)
	{
		super(par1Container, entity, "glassFormer.png");
		
		this.tile = entity;
	}
	
	@Override
	public void drawScreen(int x, int y, float par3)
	{
		super.drawScreen(x, y, par3);
		
		if(this.isPointInRegion(7, 9, 4, 67, x, y))
		{
			this.drawToolTip( tile.storedEnergy + " J", "", x, y);
		}
		if(this.isPointInRegion(16, 9, 4, 67, x, y))
		{
			this.drawToolTip( tile.temp + " C", "", x, y);
		}
		if(this.isPointInRegion(25, 8, 16, 46, x, y) && tile.Tanks[0].fluid != null && tile.Tanks[0].getFluidAmount() > 0)
		{
			this.drawToolTip(tile.Tanks[0].fluid.getFluid().getLocalizedName(), tile.Tanks[0].fluid.amount + " mB", x, y);
		}
		
		if(this.isPointInRegion(46, 8, 16, 46, x, y) && tile.Tanks[1].fluid != null && tile.Tanks[1].fluid != new FluidStack(0, 0) && tile.Tanks[1].fluid.amount > 0)
		{
			this.drawToolTip(tile.Tanks[1].fluid.getFluid().getLocalizedName(), tile.Tanks[1].fluid.amount + " mB", x, y);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		super.drawGuiContainerForegroundLayer(par1, par2);
		
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 93 + 2, 4210752);
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
		
        if(tile.Tanks[0].fluid != null && tile.Tanks[0].fluid.amount > 0)
        {
        	int i3 = tile.getScaledFluid(0, 46);
        	this.displayGauge(25, 8 + y, 0, x, i3, 46, tile.Tanks[0].fluid);
        }
        
        if(tile.Tanks[1].fluid != null && tile.Tanks[1].fluid.amount > 0)
        {
        	int i3 = tile.getScaledFluid(1, 46);
        	this.displayGauge(46, 8 + y, 0, x, i3, 46, tile.Tanks[1].fluid);
        }
	}
}
