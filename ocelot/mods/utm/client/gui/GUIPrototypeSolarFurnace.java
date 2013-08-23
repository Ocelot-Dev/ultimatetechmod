package ocelot.mods.utm.client.gui;

import ocelot.mods.utm.common.entity.TileBase;
import net.minecraft.inventory.Container;

public class GUIPrototypeSolarFurnace extends UTMGUI
{

	public GUIPrototypeSolarFurnace(Container par1Container, TileBase tile, String texturePath)
	{
		super(par1Container, tile, texturePath);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		super.drawGuiContainerBackgroundLayer(f, i, j);
	}
}
