package ocelot.mods.utm.common.blocks;

import java.util.List;
import java.util.Random;

import ocelot.mods.utm.UltimateTechMod;
import ocelot.mods.utm.common.entity.TileBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class UTMBlockMachine extends BlockContainer
{
	private Icon[] icons = new Icon[6];

	public UTMBlockMachine(int id, Material material)
	{
		super(id, Material.ground);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems)
	{
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
		subItems.add(new ItemStack(this, 1, 2));
	}
	
	@Override
	public TileEntity createTileEntity(World world, int id)
	{
		switch (id)
		{
			case 1:
				return null;
			case 2:
				return null;
		}
		return null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	private void dropItems(World world, int x, int y, int z)
	{
		Random rand = new Random();
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory))
		{
			return;
		}
		IInventory inventory = (IInventory) tileEntity;
		
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack item = inventory.getStackInSlot(i);
			
			if ((item == null) || (item.stackSize <= 0))
				continue;
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
			
			if (item.hasTagCompound())
			{
				entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
			}
			
			float factor = 0.05F;
			entityItem.motionX = (rand.nextGaussian() * factor);
			entityItem.motionY = (rand.nextGaussian() * factor + 0.2000000029802322D);
			entityItem.motionZ = (rand.nextGaussian() * factor);
			world.spawnEntityInWorld(entityItem);
			item.stackSize = 0;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
	{
		super.onBlockActivated(world, x, y, z, player, idk, what, these, are);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if ((tileEntity == null) || (player.isSneaking()))
		{
			return false;
		}
		TileBase tB = (TileBase) tileEntity;
		if (!world.isRemote && tB != null)
			player.openGui(UltimateTechMod.Instance, tB.getID(), world, x, y, z);
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack stack)
	{
		TileBase te = (TileBase) world.getBlockTileEntity(x, y, z);
		if (te == null)
			return;
		
		if (entityliving == null)
			te.setFacing(2);
		else
		{
			int l = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
			switch (l)
			{
				case 0:
					te.setFacing(2);
					break;
				case 1:
					te.setFacing(5);
					break;
				case 2:
					te.setFacing(3);
					break;
				case 3:
					te.setFacing(4);
					break;
			}
		}
		
		super.onBlockPlacedBy(world, x, y, z, entityliving, stack);
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side)
	{
		TileBase entity = (TileBase) par1IBlockAccess.getBlockTileEntity(x, y, z);
		if (entity == null)
		{
			return super.getBlockTexture(par1IBlockAccess, x, y, z, side);
		}
		if(entity.isSpecialSide(side))
		{
			int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
			switch(meta)
			{
				case 0:
					return icons[0];
				case 1:
					return icons[1];
				case 2:
					return icons[2];
				default:
					return icons[5];
			}
		}
		if(side == 1)
			return icons[4];
		else
			return icons[0];
	}
	@Override
	public Icon getIcon(int side, int meta)
	{	
		if(side == 1)
			return icons[4];
		if(side == 2)
		{
			switch(meta)
			{
				case 0:
					return icons[0];
				case 1:
					return icons[1];
				case 2:
					return icons[2];
				default:
					return icons[5];
			}
		}
		else
			return icons[0];
	}

}
