package ocelot.mods.utm.common.utils;

import java.util.ArrayList;
import java.util.List;

import ocelot.mods.utm.UTMConstants;
import ocelot.mods.utm.UltimateTechMod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MachineRecipes
{
	public static MachineRecipes instance = new MachineRecipes();
	
	public static List<meltMat> glassMaterial = new ArrayList();
	public static List<meltMat> reflectMaterial = new ArrayList();
	
	public static List<NBTTagCompound> mouldTag = new ArrayList();
	
	public class meltMat
	{
		public final int temp;
		public final ItemStack ingred;
		public final FluidStack result;
		
		public meltMat(ItemStack input, int minTemp, FluidStack result)
		{
			ingred = input;
			temp = minTemp;
			this.result = result;
		}
		
	}
	
	public static final int ingotLiquidValue = 144;
	
	/**Register all recipes here**/
	public static void InitRecipes()
	{
		registerGlassMaterial(new ItemStack(Block.sand), 2000, new FluidStack(UltimateTechMod.UTMFluidGlass, FluidContainerRegistry.BUCKET_VOLUME));
		registerGlassMaterial(new ItemStack(Block.glass), 1600, new FluidStack(UltimateTechMod.UTMFluidGlass, FluidContainerRegistry.BUCKET_VOLUME));
		registerGlassMaterial(new ItemStack(Block.thinGlass), 1600, new FluidStack(UltimateTechMod.UTMFluidGlass, 250));
		
		registerReflective(new ItemStack(Item.ingotIron), 1600, null);
	}
	
	public static void registerGlassMaterial(ItemStack glass, int minMeltTemp, FluidStack result)
	{
		glassMaterial.add(instance.new meltMat(glass, minMeltTemp, result));
	}
	public static List<ItemStack> getGlassItems()
	{
		List<ItemStack> results = new ArrayList();
		for(int i = 0; i < glassMaterial.size(); i++)
		{
			results.add(glassMaterial.get(i).ingred);
		}
		return results;
	}
	public static boolean isGlassItem(ItemStack test)
	{
		for(int i = 0; i < glassMaterial.size(); i++)
		{
			if(glassMaterial.get(i).ingred.isItemEqual(test))
				return true;
		}
		return false;
	}
	public static meltMat getGlassInfoFor(ItemStack test)
	{
		for(int i = 0; i < glassMaterial.size(); i++)
		{
			if(glassMaterial.get(i).ingred.isItemEqual(test))
				return glassMaterial.get(i);
		}
		return null;
	}
	
	public static void registerReflective(ItemStack stack, int minMeltTemp, FluidStack result)
	{
		reflectMaterial.add(instance.new meltMat(stack, minMeltTemp, result));
	}
	public static List<ItemStack> getReflective()
	{
		List<ItemStack> results = new ArrayList();
		for(int i = 0; i < reflectMaterial.size(); i++)
		{
			results.add(reflectMaterial.get(i).ingred);
		}
		return results;
	}
	public static boolean isReflectiveItem(ItemStack test)
	{
		for(int i = 0; i < reflectMaterial.size(); i++)
		{
			if(reflectMaterial.get(i).ingred.isItemEqual(test))
				return true;
		}
		return false;
	}
	public static meltMat getReflectiveInfoFor(ItemStack test)
	{
		for(int i = 0; i < reflectMaterial.size(); i++)
		{
			if(reflectMaterial.get(i).ingred.isItemEqual(test))
				return reflectMaterial.get(i);
		}
		return null;
	}
	
	public static void registerMouldTag(ItemStack result, FluidStack inputFluid)
	{
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound item = new NBTTagCompound();
		NBTTagCompound fluid = new NBTTagCompound();
		result.writeToNBT(item);
		inputFluid.writeToNBT(fluid);
		tag.setTag(UTMConstants.STACK_TAG_NAME, item);
		tag.setTag(UTMConstants.FLUID_TAG_NAME, fluid);
		
		mouldTag.add(tag);
	}
	public static NBTTagCompound getMouldTag(int index)
	{
		return mouldTag.get(index);
	}
}
