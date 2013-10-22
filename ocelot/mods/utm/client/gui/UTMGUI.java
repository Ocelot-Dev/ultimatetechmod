package ocelot.mods.utm.client.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ocelot.mods.utm.UTMConstants;
import ocelot.mods.utm.common.entity.TileBase;
import ocelot.mods.utm.common.entity.TileInventory;
import ocelot.mods.utm.common.utils.SessionVars;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class UTMGUI extends GuiContainer implements IGUIActionListener
{
	public ArrayList<UTMGUIWidget>			widgets			= new ArrayList<UTMGUIWidget>();

	public final ResourceLocation			TEXTURE;
	public final ResourceLocation			BLOCK_TEXTURE	= TextureMap.locationBlocksTexture;
	public static final ResourceLocation	LEDGER_TEXTURE	= new ResourceLocation("utm", "textures/gui/ledger.png");
	public static final ResourceLocation	UTIL_TEXTURE	= new ResourceLocation("utm", "textures/gui/util.png");
	protected final TileBase				tile;

	public UTMGUI(Container par1Container, TileBase entity, String textureName)
	{
		super(par1Container);

		this.TEXTURE = new ResourceLocation("utm", UTMConstants.GUI_TEXTURE_PATH + textureName);
		this.tile = entity;

		this.initLedgers((TileInventory) tile);
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		super.drawScreen(x, y, f);

		for (UTMGUIWidget widget : widgets)
			widget.draw(x, y, f);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		ledgerManager.drawLedgers(par1, par2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(TEXTURE);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton)
	{
		super.mouseClicked(x, y, mouseButton);

		// / Handle ledger clicks
		ledgerManager.handleMouseClicked(x, y, mouseButton);
		for (UTMGUIWidget widget : widgets)
			widget.mouseClicked(x, y, mouseButton);
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int button)
	{
		super.mouseMovedOrUp(x, y, button);
		for (UTMGUIWidget widget : widgets)
			widget.mouseMovedOrUp(x, y, button);
	}

	@Override
	protected void mouseClickMove(int x, int y, int button, long time)
	{
		super.mouseClickMove(x, y, button, time);
		for (UTMGUIWidget widget : widgets)
			widget.mouseDragged(x, y, button, time);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (mc.currentScreen == this) for (UTMGUIWidget widget : widgets)
			widget.update();
	}

	@Override
	public void keyTyped(char c, int keycode)
	{
		super.keyTyped(c, keycode);
		for (UTMGUIWidget widget : widgets)
			widget.keyTyped(c, keycode);
	}

	protected void drawToolTip(String line1, String line2, int x, int y)
	{
		List var4 = new ArrayList();
		var4.add(EnumChatFormatting.WHITE + line1);
		if (!line2.equals("")) var4.add(EnumChatFormatting.WHITE + line2);
		this.drawHoveringText(var4, x, y, fontRenderer);
	}

	protected void displayGauge(int x, int y, int yStart, int xStart, int scale, int height, FluidStack liquid)
	{
		if (liquid == null)
		{
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		Fluid fluid = liquid.getFluid();
		if (fluid != null && fluid.getStillIcon() != null)
		{
			liquidIcon = fluid.getStillIcon();
		}
		else if (fluid != null && Block.blocksList[fluid.getBlockID()] != null)
		{
			liquidIcon = Block.blocksList[fluid.getBlockID()].getIcon(1, 0);
		}
		else
			liquidIcon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
		mc.renderEngine.bindTexture(BLOCK_TEXTURE);

		if (liquidIcon != null)
		{
			while (true)
			{
				int i;

				if (scale > 16)
				{
					i = 16;
					scale -= 16;
				}
				else
				{
					i = scale;
					scale = 0;
				}

				drawTexturedModelRectFromIcon(x + xStart, y + yStart + height - i - start, liquidIcon, 16, 16 - (16 - i));
				start = start + 16;

				if (i == 0 || scale == 0)
				{
					break;
				}
			}
		}

		mc.renderEngine.bindTexture(TEXTURE);
		drawTexturedModalRect(x + xStart, y + yStart, 176, 84, 16, height);
	}

	public void add(UTMGUIWidget widget)
	{
		widgets.add(widget);
		widget.onAdded(this);
	}

	// LEDGER FROM BUILDCRAFT, THANK YOU BC TEAM
	protected LedgerManager	ledgerManager	= new LedgerManager(this);

	protected void initLedgers(IInventory inventory)
	{}

	protected class LedgerManager
	{

		private UTMGUI				gui;
		protected ArrayList<Ledger>	ledgers	= new ArrayList<Ledger>();

		public LedgerManager(UTMGUI gui)
		{
			this.gui = gui;
		}

		public void add(Ledger ledger)
		{
			this.ledgers.add(ledger);
			if (SessionVars.getOpenedLedger() != null && ledger.getClass().equals(SessionVars.getOpenedLedger()))
			{
				ledger.setFullyOpen();
			}
		}

		/**
		 * Inserts a ledger into the next-to-last position.
		 * 
		 * @param ledger
		 */
		public void insert(Ledger ledger)
		{
			this.ledgers.add(ledgers.size() - 1, ledger);
		}

		protected Ledger getAtPosition(int mX, int mY)
		{

			int xShift = ((gui.width - gui.xSize) / 2) + gui.xSize;
			int yShift = ((gui.height - gui.ySize) / 2) + 8;

			for (int i = 0; i < ledgers.size(); i++)
			{
				Ledger ledger = ledgers.get(i);
				if (!ledger.isVisible())
				{
					continue;
				}

				ledger.currentShiftX = xShift;
				ledger.currentShiftY = yShift;
				if (ledger.intersectsWith(mX, mY, xShift, yShift))
				{
					return ledger;
				}

				yShift += ledger.getHeight();
			}

			return null;
		}

		protected void drawLedgers(int mouseX, int mouseY)
		{

			int xPos = 8;
			for (Ledger ledger : ledgers)
			{

				ledger.update();
				if (!ledger.isVisible())
				{
					continue;
				}

				ledger.draw(xSize, xPos);
				xPos += ledger.getHeight();
			}

			Ledger ledger = getAtPosition(mouseX, mouseY);
			if (ledger != null)
			{
				int startX = mouseX - ((gui.width - gui.xSize) / 2) + 12;
				int startY = mouseY - ((gui.height - gui.ySize) / 2) - 12;

				String tooltip = ledger.getTooltip();
				int textWidth = fontRenderer.getStringWidth(tooltip);
				drawGradientRect(startX - 3, startY - 3, startX + textWidth + 3, startY + 8 + 3, 0xc0000000, 0xc0000000);
				fontRenderer.drawStringWithShadow(tooltip, startX, startY, -1);
			}
		}

		public void handleMouseClicked(int x, int y, int mouseButton)
		{

			if (mouseButton == 0)
			{

				Ledger ledger = this.getAtPosition(x, y);

				// Default action only if the mouse click was not handled by the
				// ledger itself.
				if (ledger != null && !ledger.handleMouseClicked(x, y, mouseButton))
				{

					for (Ledger other : ledgers)
					{
						if (other != ledger && other.isOpen())
						{
							other.toggleOpen();
						}
					}
					ledger.toggleOpen();
				}
			}

		}
	}

	/**
	 * Side ledger for guis
	 * 
	 * Thank you BuildCraft!!
	 */
	protected abstract class Ledger
	{

		private boolean	open;
		protected int	overlayColor	= 0xffffff;
		public int		currentShiftX	= 0;
		public int		currentShiftY	= 0;
		protected int	limitWidth		= 128;
		protected int	maxWidth		= 124;
		protected int	minWidth		= 24;
		protected int	currentWidth	= minWidth;
		protected int	maxHeight		= 24;
		protected int	minHeight		= 24;
		protected int	currentHeight	= minHeight;

		public void update()
		{
			// Width
			if (open && currentWidth < maxWidth)
			{
				currentWidth += 4;
			}
			else if (!open && currentWidth > minWidth)
			{
				currentWidth -= 4;
			}

			// Height
			if (open && currentHeight < maxHeight)
			{
				currentHeight += 4;
			}
			else if (!open && currentHeight > minHeight)
			{
				currentHeight -= 4;
			}
		}

		public int getHeight()
		{
			return currentHeight;
		}

		public abstract void draw(int x, int y);

		public abstract String getTooltip();

		public boolean handleMouseClicked(int x, int y, int mouseButton)
		{
			return false;
		}

		public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY)
		{

			if (mouseX >= shiftX && mouseX <= shiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + getHeight())
			{
				return true;
			}

			return false;
		}

		public void setFullyOpen()
		{
			open = true;
			currentWidth = maxWidth;
			currentHeight = maxHeight;
		}

		public void toggleOpen()
		{
			if (open)
			{
				open = false;
				SessionVars.setOpenedLedger(null);
			}
			else
			{
				open = true;
				SessionVars.setOpenedLedger(this.getClass());
			}
		}

		public boolean isVisible()
		{
			return true;
		}

		public boolean isOpen()
		{
			return this.open;
		}

		protected boolean isFullyOpened()
		{
			return currentWidth >= maxWidth;
		}

		protected void drawBackground(int x, int y)
		{

			float colorR = (overlayColor >> 16 & 255) / 255.0F;
			float colorG = (overlayColor >> 8 & 255) / 255.0F;
			float colorB = (overlayColor & 255) / 255.0F;

			GL11.glColor4f(colorR, colorG, colorB, 1.0F);

			mc.renderEngine.bindTexture(LEDGER_TEXTURE);
			drawTexturedModalRect(x, y, 0, 256 - currentHeight, 4, currentHeight);
			drawTexturedModalRect(x + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
			// Add in top left corner again
			drawTexturedModalRect(x, y, 0, 0, 4, 4);

			drawTexturedModalRect(x + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);

			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		}

		protected void drawIcon(Icon icon, int x, int y)
		{

			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
			drawTexturedModelRectFromIcon(x, y, icon, 16, 16);
		}
	}

	@Override
	public void actionPerformed(String actionCommand, Object... params)
	{}
}
