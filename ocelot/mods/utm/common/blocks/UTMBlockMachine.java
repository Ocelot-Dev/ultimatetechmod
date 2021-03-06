package ocelot.mods.utm.common.blocks;

import java.util.List;
import java.util.Random;

import ocelot.mods.utm.UTM;
import ocelot.mods.utm.Utilities;
import ocelot.mods.utm.client.UTMCreativeTab;
import ocelot.mods.utm.common.entity.TileBase;
import ocelot.mods.utm.common.entity.TileGlassFormer;
import ocelot.mods.utm.common.entity.TileMouldMaker;
import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.common.ForgeDirection;

public class UTMBlockMachine extends BlockContainer
{
	private Icon[][]	icons;

	public UTMBlockMachine(int id, Material material)
	{
		super(id, Material.ground);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundMetalFootstep);
		setCreativeTab(UTMCreativeTab.tab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems)
	{
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
		subItems.add(new ItemStack(this, 1, 2));
		subItems.add(new ItemStack(this, 1, 3));
	}

	@Override
	public int damageDropped(int damage)
	{
		return damage;
	}

	@Override
	public TileEntity createTileEntity(World world, int id)
	{
		switch (id)
		{
		case 1:
			return new TilePrototypeSolarFurnace();
		case 2:
			return new TileGlassFormer();
		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)
	{
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, id, meta);
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

			if ((item == null) || (item.stackSize <= 0)) continue;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{		
		if(world.getBlockMetadata(x, y, z) == 3)
		{
			player.openGui(UTM.Instance, 3, world, x, y, z);
			return true;
		}

		TileBase tB = (TileBase) world.getBlockTileEntity(x, y, z);
		if (tB != null)
		{
			player.openGui(UTM.Instance, tB.getID(), world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entityliving, stack);
		
		TileBase te = (TileBase) world.getBlockTileEntity(x, y, z);
		if (te == null) return;

		if (entityliving == null)
			te.setFacing(ForgeDirection.NORTH);
		else
		{
			int l = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
			switch (l)
			{
			case 0:
				te.setFacing(ForgeDirection.NORTH);
				break;
			case 1:
				te.setFacing(ForgeDirection.EAST);
				break;
			case 2:
				te.setFacing(ForgeDirection.SOUTH);
				break;
			case 3:
				te.setFacing(ForgeDirection.WEST);
				break;
			}
		}
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileBase)
		{
			((TileBase) tile).setFacing(Utilities.getNextSide(((TileBase) tile).getFacing()));
			return true;
		}
		return false;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side)
	{
		ForgeDirection direction = ForgeDirection.getOrientation(side);
		TileBase entity = (TileBase) par1IBlockAccess.getBlockTileEntity(x, y, z);
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		if (entity == null) return super.getBlockTexture(par1IBlockAccess, x, y, z, side);
		ForgeDirection facing = entity.getFacing();
		int index = 0;
		if (facing.equals(direction))
			index = 0;
		else if (Utilities.isLeft(direction, facing) || Utilities.isRight(direction, facing))
			index = 2;
		else if (Utilities.isBack(direction, facing))
			index = 4;
		else if (direction.equals(ForgeDirection.UP))
			index = 6;
		else if (direction.equals(ForgeDirection.DOWN))
			index = 8;
		else
			index = 8;
		if (!entity.doesSideNotChangeActive(direction)) index += 1;
		return getValidIcon(meta, index);
	}

	public Icon getValidIcon(int meta, int index)
	{
		try
		{
			Icon icon = icons[meta][index];
			if (icon == null) return icons[0][0];
			return icons[meta][index];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return icons[0][0];
		}
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		switch (meta)
		{
		case 0:
			return icons[0][0];
		case 1:
			switch (side)
			{
			case 0:
				return icons[1][1];
			case 1:
				return icons[1][6];
			case 2:
				return icons[1][2];
			case 3:
				return icons[1][0];
			case 4:
				return icons[1][3];
			case 5:
				return icons[1][3];
			}
		default:
			return icons[0][0];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg)
	{
		this.icons = new Icon[3][10];

		this.icons[0][0] = reg.registerIcon("utm:prototypeSolarFurnace_bottom");

		this.icons[1][0] = reg.registerIcon("utm:prototypeSolarFurnace_front");
		this.icons[1][1] = reg.registerIcon("utm:prototypeSolarFurnace_front_on");
		this.icons[1][2] = reg.registerIcon("utm:prototypeSolarFurnace_side");
		this.icons[1][3] = reg.registerIcon("utm:prototypeSolarFurnace_side_on");
		this.icons[1][4] = reg.registerIcon("utm:prototypeSolarFurnace_back");
		this.icons[1][6] = reg.registerIcon("utm:prototypeSolarFurnace_top");
		this.icons[1][8] = reg.registerIcon("utm:prototypeSolarFurnace_bottom");
	}

}
