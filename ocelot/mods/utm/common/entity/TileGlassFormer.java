package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.core.SafeTimeTracker;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.transport.IPipeConnection.ConnectOverride;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileGlassFormer extends TilePowered implements IFluidHandler
{
	private SafeTimeTracker coolDown = new SafeTimeTracker();
	private SafeTimeTracker heatUp = new SafeTimeTracker();
	
	public FluidTank tanks[] = new FluidTank[2];
	public int temp = 20;

	public TileGlassFormer()
	{
		super(4, 1500);
		
		tanks[0] = new FluidTank(5000);
		tanks[1] = new FluidTank(5000);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!this.worldObj.isRemote)
		{
			if(this.useEnergy(0.5F, false))
			{
				coolDown.markTime(worldObj);
				useEnergy(0.5F, true);
				
				if(heatUp.markTimeIfDelay(worldObj, 7))
					temp++;
			}
			else
			{
				heatUp.markTime(worldObj);
				if(coolDown.markTimeIfDelay(worldObj, 14))
				{
					temp = Math.max(temp--, 20);
				}
			}
		}
	}

	@Override
	public void doWork(PowerHandler workProvider)
	{}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		return ConnectOverride.DEFAULT;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getInvName()
	{
		return "inv.glassformer";
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesSideNotChangeActive(ForgeDirection side)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
