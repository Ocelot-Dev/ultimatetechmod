package ocelot.mods.utm.client.gui.elements;

import ocelot.mods.utm.client.gui.UTMGUI;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class UTMButton extends GuiButton
{
	public final UTMGUI gui;
	public final ResourceLocation texture;
	public final int xText;
	public final int yText;
	
	public UTMButton(int id, int xPos, int yPos, int width, int height, int xIconStart, int yIconStart, ResourceLocation resourceLocation, UTMGUI gui)
	{
		super(id, xPos, yPos, width, height, "");
		texture = resourceLocation;
		xText = xIconStart;
		yText = yIconStart;
		this.gui = gui;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            par1Minecraft.getTextureManager().bindTexture(gui.TEXTURE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            short short1 = 219;
            int k = 0;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, xText, yText, this.width, this.height);

            if (!gui.TEXTURE.equals(this.texture))
            {
                par1Minecraft.getTextureManager().bindTexture(this.texture);
            }

            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.xText, this.yText, this.width, this.height);
        }
    }

}
