package ocelot.mods.utm.client.gui;

import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class GUIPrototypeSolarFurnace extends UTMGUI
{
	private TilePrototypeSolarFurnace entity;

	public GUIPrototypeSolarFurnace(Container par1Container, TilePrototypeSolarFurnace tile)
	{
		super(par1Container, tile, "PrototypeSolarfurnace.png");
		entity = tile;
	}

	@Override
	public void drawScreen(int x, int y, float par3)
	{
		super.drawScreen(x, y, par3);

		if (this.isPointInRegion(7, 9, 4, 67, x, y))
		{
			this.drawToolTip("Per-Cent Done", entity.getScaledSmeltTime(100) + "", x, y);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		super.drawGuiContainerBackgroundLayer(f, i, j);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		if (entity.getIsOn())
		{
			this.drawTexturedModalRect(k + 83, l + 18, 176, 0, 14, 14);
		}

		int i1 = this.entity.getScaledSmeltTime(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}

	@Override
	protected void initLedgers(IInventory inventory)
	{
		super.initLedgers(inventory);

		ledgerManager.add(new protoLedger((TilePrototypeSolarFurnace)this.tile));
	}

	protected class protoLedger extends Ledger
	{

		TilePrototypeSolarFurnace tile;
		int headerColour = 0xe1c92f;
		int subheaderColour = 0xaaafb8;
		int textColour = 0x000000;

		public protoLedger(TilePrototypeSolarFurnace tile)
		{
			this.tile = tile;
			maxHeight = 94;
			overlayColor = 0xd46c1f;
		}

		@Override
		public void draw(int x, int y)
		{

			// Draw background
			drawBackground(x, y);

			// Draw icon
			// Minecraft.getMinecraft().renderEngine.bindTexture(ITEM_TEXTURE);
			// drawIcon(BuildCraftCore.iconProvider.getIcon(CoreIconProvider.ENERGY),
			// x + 3, y + 4);

			if (!isFullyOpened()) return;

			fontRenderer.drawStringWithShadow("hi", x + 22, y + 8, headerColour);
			fontRenderer.drawStringWithShadow("ho", x + 22, y + 20, subheaderColour);
		}

		@Override
		public String getTooltip()
		{
			return String.format("%d Per-Cent", tile.getScaledSmeltTime(100));
		}
	}
}
