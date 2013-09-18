package ocelot.mods.utm.common.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public abstract class TileTempControl extends TilePowered
{
	private SafeTimeTracker coolDown = new SafeTimeTracker();
	private SafeTimeTracker heatUp = new SafeTimeTracker();
	
	public int temp;
	public final int minTemp;
	public final int maxTemp;
	public final int heatUpTick;
	public final int coolDownTick;
	public final float energyTick;

	public TileTempControl(int invSize, float energyStore, int minTemp, int maxTemp, int heatUpTick, int coolDownTick, float energyTick)
	{
		super(4, 1500);
		
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		temp = minTemp;
		this.heatUpTick = heatUpTick;
		this.coolDownTick = coolDownTick;
		this.energyTick = energyTick;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!this.worldObj.isRemote)
		{
			if(this.useEnergy(energyTick, false))
			{
				coolDown.markTime(worldObj);
				useEnergy(energyTick, true);
				
				if(heatUp.markTimeIfDelay(worldObj, heatUpTick))
					temp = Math.min(temp + 1, maxTemp);
			}
			else
			{
				heatUp.markTime(worldObj);
				if(coolDown.markTimeIfDelay(worldObj, coolDownTick))
				{
					temp = Math.max(temp - 1, minTemp);
				}
			}
		}
	}
	
	public int getScaledTemp(int size)
	{
		return temp * size / maxTemp;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		temp = tag.getInteger("temperature");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		tag.setInteger("temperature", temp);
	}
}
