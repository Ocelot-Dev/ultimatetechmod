package ocelot.mods.utm.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class UTMBlock extends Block
{

	public UTMBlock(int id, Material material)
	{
		super(id, material);
	}

	public final static Block prototypeSolarFurnace = new UTMBlock(800, Material.ground) .setHardness(0.5F) .setStepSound(Block.soundMetalFootstep) .setUnlocalizedName("prototypeSolarFurnace") .setCreativeTab(CreativeTabs.tabRedstone) .func_111022_d("buildcraft:architect_top_pos");
}
