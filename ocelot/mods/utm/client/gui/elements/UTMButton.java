package ocelot.mods.utm.client.gui.elements;

import ocelot.mods.utm.client.gui.UTMGUI;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class UTMButton extends GuiButton
{
	protected final ResourceLocation	buttonTexture;
	protected final int					xTexturePos;
	protected final int					yTexturePos;

	public UTMButton(int id, int xPos, int yPos, int width, int height, ResourceLocation texture, int xTexture, int yTexture)
	{
		super(id, xPos, yPos, width, height, "");
		buttonTexture = texture;
		xTexturePos = xTexture;
		yTexturePos = yTexture;
	}

	public void drawButton(Minecraft par1Minecraft, int x, int y)
	{
		if (this.drawButton)
		{
			par1Minecraft.getTextureManager().bindTexture(buttonTexture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
			int offset = xTexturePos;

			if (!this.enabled)
			{
				offset += this.width * 2;
			}
			else if (this.field_82253_i)
			{
				offset += this.width * 3;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, offset, this.yTexturePos, this.width, this.height);
		}
	}
}
