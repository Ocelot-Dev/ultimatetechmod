package ocelot.mods.utm.client.gui;

import java.util.ArrayList;
import java.util.List;

import ocelot.mods.utm.common.entity.TileBase;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class UTMGUI extends GuiContainer
{
	protected final ResourceLocation TEXTURE;
	protected final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
	protected final TileBase tile;
	
	public UTMGUI(Container par1Container, TileBase entity, String texturePath)
	{
		super(par1Container);
		
		this.TEXTURE = new ResourceLocation("ultimatetechmod", texturePath);
		this.tile = entity;
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
	
	protected void drawToolTip(String line1, String line2, int x, int y)
    {
        List var4 = new ArrayList();
        var4.add(EnumChatFormatting.WHITE + line1);
        if(!line2.equals(""))
        	var4.add(EnumChatFormatting.WHITE + line2);
        this.drawHoveringText(var4, x, y, fontRenderer);
    }
	
	protected void displayGauge(int x, int y, int yStart, int xStart, int scale, int height, FluidStack liquid) 
	{
		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		Fluid fluid = liquid.getFluid();
		if (fluid != null && fluid.getStillIcon() != null) {
			liquidIcon = fluid.getStillIcon();
		}
		mc.renderEngine.bindTexture(BLOCK_TEXTURE);

		if (liquidIcon != null) {
			while (true) {
				int i;

				if (scale > 16) {
					i = 16;
					scale -= 16;
				} else {
					i = scale;
					scale = 0;
				}

				drawTexturedModelRectFromIcon(x + xStart, y + yStart + height - i - start, liquidIcon, 16, 16 - (16 - i));
				start = start + 16;

				if (i == 0 || scale == 0) {
					break;
				}
			}
		}

		mc.renderEngine.bindTexture(TEXTURE);
		drawTexturedModalRect(x + xStart, y + yStart, 176, 84, 16, height);
	}

}
