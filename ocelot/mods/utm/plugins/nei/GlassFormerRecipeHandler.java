package ocelot.mods.utm.plugins.nei;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class GlassFormerRecipeHandler extends TemplateRecipeHandler
{

	public class mould extends CachedRecipe
	{
		
		
		public mould(ItemStack ingred)
		{
			this.ingred = new PositionedStack(ingred, 76, 35);
			ItemStack result = new ItemStack(0,0,0);
			result.readFromNBT(ingred.stackTagCompound.getCompoundTag("result"));
			this.result = new PositionedStack(result, 76, 35);
		}

		@Override
		public PositionedStack getResult()
		{
			return null;
		}
		
		PositionedStack ingred;
		PositionedStack result;
		FluidStack fluid;
	}

	@Override
	public String getRecipeName()
	{
		return I18n.getString("recipe.GlassFormer");
	}

	@Override
	public String getGuiTexture()
	{
		return "utm/textures/gui/GlassFormer.png";
	}

}
