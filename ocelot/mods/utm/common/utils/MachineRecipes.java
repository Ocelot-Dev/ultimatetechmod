package ocelot.mods.utm.common.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MachineRecipes
{
	public static MachineRecipes instance = new MachineRecipes();
	
	public static List<meltMat> glassMaterial = new ArrayList();
	public static List<meltMat> reflectMaterial = new ArrayList();
	
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
	
	/**Register all recipes here**/
	public static void InitRecipes()
	{
		registerGlassMaterial(new ItemStack(Block.sand), 2000, null);
		registerGlassMaterial(new ItemStack(Block.glass), 1600, null);
		registerGlassMaterial(new ItemStack(Block.thinGlass), 1600, null);
		
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
}
