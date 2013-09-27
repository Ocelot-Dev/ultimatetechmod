package ocelot.mods.utm.common.entity;

import ocelot.mods.utm.Utilities;
import ocelot.mods.utm.common.fluidtank.UTMFluidTank;
import ocelot.mods.utm.common.utils.MachineRecipes;
import ocelot.mods.utm.common.utils.MachineRecipes.meltMat;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.transport.IPipeTile.PipeType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileGlassFormer extends TileTempControl implements IFluidHandler
{
	public UTMFluidTank Tanks[] = new UTMFluidTank[2];

	public TileGlassFormer()
	{
		super(4, 1500F, 20, 2000, 2, 28, 0.5F);

		Tanks[0] = new UTMFluidTank(5000);
		Tanks[1] = new UTMFluidTank(5000);
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!this.worldObj.isRemote)
		{
			if (this.inv[0] != null && Tanks[0].getFluidAmount() != Tanks[0].getCapacity())
			{
				meltMat glassMat = MachineRecipes.getGlassInfoFor(this.inv[0]);
				
				if(glassMat != null && Tanks[0].fill(glassMat.result, false) == glassMat.result.amount && this.temp >= glassMat.temp)
				{
					if(inv[0].stackSize == 1)
						inv[0] = null;
					else
						inv[0].stackSize--;
					
					Tanks[0].fill(glassMat.result, true);
				}
			}
			
			if (this.inv[1] != null && Tanks[1].getFluidAmount() != Tanks[1].getCapacity())
			{
				meltMat reflectMat = MachineRecipes.getReflectiveInfoFor(this.inv[1]);
				if(reflectMat != null && Tanks[1].fill(reflectMat.result, false) == reflectMat.result.amount && this.temp >= reflectMat.temp)
				{
					if(inv[1].stackSize == 1)
						inv[1] = null;
					else
						inv[1].stackSize--;
					
					Tanks[1].fill(reflectMat.result, true);
				}
			}
			boolean empty = false;
			
			if(empty)
				Tanks[0].setFluid(null);
		}
	}

	@Override
	public void getGUINetworkData(int type, int data)
	{
		switch (type)
		{
		case 0:
			this.temp = data;
		case 1:
			this.storedEnergy = data / 10;
		case 2:
			if (Tanks[0].getFluid() == null && FluidRegistry.getFluid(data) != null)
				Tanks[0].setFluid(new FluidStack(FluidRegistry.getFluid(data), 0));
			else if (Tanks[0].getFluid() != null && Tanks[0].getFluid().fluidID != data && FluidRegistry.getFluid(data) != null) Tanks[0].fluid.fluidID = data;
		case 3:
			if (Tanks[0].getFluid() == null)
				Tanks[0].setFluid(new FluidStack(0, data));
			else if (Tanks[0].getFluid().amount != data) Tanks[0].fluid.amount = data;
		case 4:
			if (Tanks[1].getFluid() == null && FluidRegistry.getFluid(data) != null)
				Tanks[1].setFluid(new FluidStack(FluidRegistry.getFluid(data), 0));
			else if (Tanks[1].getFluid() != null && Tanks[1].getFluid().fluidID != data && FluidRegistry.getFluid(data) != null) Tanks[1].fluid.fluidID = data;
		case 5:
			if (Tanks[1].getFluid() == null)
				Tanks[1].setFluid(new FluidStack(0, data));
			else if (Tanks[1].getFluid().amount != data) Tanks[1].fluid.amount = data;
		}
	}

	@Override
	public void sendGUINetworkData(Container container, ICrafting iCrafting)
	{
		iCrafting.sendProgressBarUpdate(container, 0, this.temp);
		iCrafting.sendProgressBarUpdate(container, 1, Math.round(this.storedEnergy * 10));
		iCrafting.sendProgressBarUpdate(container, 2, this.Tanks[0].fluid == null ? 0 : this.Tanks[0].fluid.fluidID);
		iCrafting.sendProgressBarUpdate(container, 3, this.Tanks[0].fluid == null ? 0 : this.Tanks[0].fluid.amount);
		iCrafting.sendProgressBarUpdate(container, 4, this.Tanks[1].fluid == null ? 0 : this.Tanks[1].fluid.fluidID);
		iCrafting.sendProgressBarUpdate(container, 5, this.Tanks[1].fluid == null ? 0 : this.Tanks[1].fluid.amount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		
		NBTTagCompound tag1 = (NBTTagCompound)tagCompound.getTag("tank0");
		Tanks[0].readFromNBT(tag1);
		NBTTagCompound tag2 = (NBTTagCompound)tagCompound.getTag("tank1");
		Tanks[1].readFromNBT(tag2);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		
		NBTTagCompound tag1 = new NBTTagCompound();
		Tanks[0].writeToNBT(tag1);
		tagCompound.setTag("tank0", tag1);
		NBTTagCompound tag2 = new NBTTagCompound();
		Tanks[1].writeToNBT(tagCompound);
		tagCompound.setTag("tank1", tag2);
	}
	
	@Override
	public void doWork(PowerHandler workProvider)
	{
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		ForgeDirection sideF = Utilities.getIntDirection(side);
		if(Utilities.isLeft(sideF, facing))
			return new int[] {0, 1};
		if(Utilities.isRight(sideF, facing))
			return new int[] {3};
		if(side == 1)
			return new int[] {2};
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		ForgeDirection sideF = Utilities.getIntDirection(side);
		if(slot == 0 && Utilities.isLeft(sideF, facing) && MachineRecipes.isGlassItem(itemstack))
			return true;
		if(slot == 1 && Utilities.isLeft(sideF, facing) && MachineRecipes.isReflectiveItem(itemstack))
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		ForgeDirection sideF = Utilities.getIntDirection(side);
		if(slot == 3 && Utilities.isRight(sideF, facing))
			return true;
		return false;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		return ConnectOverride.CONNECT;
	}

	@Override
	public String getInvName()
	{
		return "tile.glassformer.name";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public boolean doesSideNotChangeActive(ForgeDirection side)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* Tank Stuff */

	public int getScaledFluid(int tank, int size)
	{
		if (tank < 2 && !(tank < 0)) return Math.round(Tanks[tank].fluid.amount * size / Tanks[tank].getCapacity());
		return 0;
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
