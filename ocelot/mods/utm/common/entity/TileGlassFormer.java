package ocelot.mods.utm.common.entity;

import ocelot.mods.utm.common.fluidtank.UTMFluidTank;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.transport.IPipeTile.PipeType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileGlassFormer extends TileTempControl implements IFluidHandler
{
	public UTMFluidTank Tanks[] = new UTMFluidTank[2];

	public TileGlassFormer()
	{
		super(4, 1500F, 20, 2000, 7, 14, 0.5F);
		
		Tanks[0] = new UTMFluidTank(5000);
		Tanks[1] = new UTMFluidTank(5000);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!this.worldObj.isRemote)
		{
		}
	}
	
	@Override
	public void getGUINetworkData(int type, int data)
	{
		switch(type)
		{
		case 0:
			this.temp = data;
		case 1:
			this.storedEnergy = data / 10;
		/*case 2:
			if(Tanks[0].getFluid() == null)
				Tanks[0].setFluid(new FluidStack(FluidRegistry.getFluid(data), 0));
			else if (Tanks[0].getFluid().fluidID != data)
				Tanks[0].fluid.fluidID = data;
		case 3:
			if(Tanks[0].getFluid() == null)
				Tanks[0].setFluid(new FluidStack(0, data));
			else if (Tanks[0].getFluid().fluidID != data)
				Tanks[0].fluid.amount = data;
		case 4:
			if(Tanks[1].getFluid() == null)
				Tanks[1].setFluid(new FluidStack(FluidRegistry.getFluid(data), 0));
			else if (Tanks[1].getFluid().fluidID != data)
				Tanks[1].fluid.fluidID = data;
		case 5:
			if(Tanks[1].getFluid() == null)
				Tanks[1].setFluid(new FluidStack(0, data));
			else if (Tanks[1].getFluid().fluidID != data)
				Tanks[1].fluid.amount = data;*/
		}
	}

	@Override
	public void sendGUINetworkData(Container container, ICrafting iCrafting)
	{
		iCrafting.sendProgressBarUpdate(container, 0, this.temp);
		iCrafting.sendProgressBarUpdate(container, 1, Math.round(this.storedEnergy * 10));
		//iCrafting.sendProgressBarUpdate(container, 2, this.Tanks[0].fluid.fluidID);
		//iCrafting.sendProgressBarUpdate(container, 3, this.Tanks[0].fluid.amount);
		//iCrafting.sendProgressBarUpdate(container, 4, this.Tanks[1].fluid.fluidID);
		//iCrafting.sendProgressBarUpdate(container, 5, this.Tanks[1].fluid.amount);
	}

	@Override
	public void doWork(PowerHandler workProvider){}

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
		return ConnectOverride.CONNECT;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getInvName()
	{
		return "tile.glassformer";
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
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

	/*Tank Stuff */
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
