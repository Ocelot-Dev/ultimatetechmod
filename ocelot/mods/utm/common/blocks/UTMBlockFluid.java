package ocelot.mods.utm.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class UTMBlockFluid extends BlockFluidFinite
{
	public Icon[] icons;
	private String name;

	public UTMBlockFluid(int id, Fluid fluid, Material material, String fileName)
	{
		super(id, fluid, material);
		this.name = fileName;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? this.icons[1] : this.icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) 
	{
		this.icons = new Icon[]{iconRegister.registerIcon("utm:liquid_" + name), iconRegister.registerIcon("utm:liquid_" + name + "_flow")};
	}
}
