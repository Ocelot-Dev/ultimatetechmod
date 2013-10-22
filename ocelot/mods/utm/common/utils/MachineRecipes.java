package ocelot.mods.utm.common.utils;

import java.util.ArrayList;
import java.util.List;

import ocelot.mods.utm.UTMConstants;
import ocelot.mods.utm.UTM;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MachineRecipes
{
	public static List<meltMat>		glassMaterial	= new ArrayList();
	public static List<meltMat>		reflectMaterial	= new ArrayList();

	public static List<mirrorLens>	mouldInfo		= new ArrayList();

	private static boolean			init			= false;

	public static class meltMat
	{
		public final int		temp;
		public final ItemStack	ingred;
		public final FluidStack	result;

		public meltMat(ItemStack input, int minTemp, FluidStack result)
		{
			ingred = input;
			temp = minTemp;
			this.result = result;
		}

	}

	public static class mirrorLens
	{
		public final int		minFocus;
		public final int		maxFocus;
		public final float		fluidUseModifier;
		public final ItemStack	result;
		public final FluidStack	inputFluid;
		public final String unlocalName;

		public mirrorLens(FluidStack inputFluid, ItemStack result, int minFocus, int maxFocus, float modifier, String name)
		{
			this.result = result;
			this.minFocus = minFocus;
			this.maxFocus = maxFocus;
			this.inputFluid = inputFluid;
			this.fluidUseModifier = modifier;
			this.unlocalName = name;
		}

	}

	public static final int	ingotLiquidValue	= 144;

	/** Register all recipes here **/
	public static void InitRecipes()
	{
		if (!init)
		{
			init = true;

			registerGlassMaterial(new ItemStack(Block.sand), 2000, new FluidStack(UTM.UTMFluidGlass, FluidContainerRegistry.BUCKET_VOLUME));
			registerGlassMaterial(new ItemStack(Block.glass), 1600, new FluidStack(UTM.UTMFluidGlass, FluidContainerRegistry.BUCKET_VOLUME));
			registerGlassMaterial(new ItemStack(Block.thinGlass), 1600, new FluidStack(UTM.UTMFluidGlass, 250));

			registerReflective(new ItemStack(Item.ingotIron), 1600, null);

			registerMirrorLens(null, null, 0, 0, 0, "info.straightLight");
		}
	}

	public static void registerGlassMaterial(ItemStack glass, int minMeltTemp, FluidStack result)
	{
		glassMaterial.add(new meltMat(glass, minMeltTemp, result));
	}

	public static List<ItemStack> getGlassItems()
	{
		List<ItemStack> results = new ArrayList();
		for (int i = 0; i < glassMaterial.size(); i++)
		{
			results.add(glassMaterial.get(i).ingred);
		}
		return results;
	}

	public static boolean isGlassItem(ItemStack test)
	{
		for (int i = 0; i < glassMaterial.size(); i++)
		{
			if (glassMaterial.get(i).ingred.isItemEqual(test)) return true;
		}
		return false;
	}

	public static meltMat getGlassInfoFor(ItemStack test)
	{
		for (int i = 0; i < glassMaterial.size(); i++)
		{
			if (glassMaterial.get(i).ingred.isItemEqual(test)) return glassMaterial.get(i);
		}
		return null;
	}

	public static void registerReflective(ItemStack stack, int minMeltTemp, FluidStack result)
	{
		reflectMaterial.add(new meltMat(stack, minMeltTemp, result));
	}

	public static List<ItemStack> getReflective()
	{
		List<ItemStack> results = new ArrayList();
		for (int i = 0; i < reflectMaterial.size(); i++)
		{
			results.add(reflectMaterial.get(i).ingred);
		}
		return results;
	}

	public static boolean isReflectiveItem(ItemStack test)
	{
		for (int i = 0; i < reflectMaterial.size(); i++)
		{
			if (reflectMaterial.get(i).ingred.isItemEqual(test)) return true;
		}
		return false;
	}

	public static meltMat getReflectiveInfoFor(ItemStack test)
	{
		for (int i = 0; i < reflectMaterial.size(); i++)
		{
			if (reflectMaterial.get(i).ingred.isItemEqual(test)) return reflectMaterial.get(i);
		}
		return null;
	}

	public static void registerMirrorLens(ItemStack result, FluidStack inputFluid, int minFocus, int maxFocus, float fluidUseModifier, String unlocalName)
	{
		mouldInfo.add(new mirrorLens(inputFluid, result, minFocus, maxFocus, fluidUseModifier, unlocalName));
	}

	public static mirrorLens getMouldInfo(int index)
	{
		return mouldInfo.get(index);
	}
}
