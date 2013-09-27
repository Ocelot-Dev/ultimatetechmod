package ocelot.mods.utm.common.entity;

import universalelectricity.compatibility.Compatibility;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.block.IElectricalStorage;
import universalelectricity.core.electricity.ElectricityPack;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public abstract class TilePowered extends TileInventory implements IEnergySink, IPowerReceptor, IElectrical, IElectricalStorage
{
	protected boolean isAddedToEnergyNet = false;
	public PowerHandler powerHandler;
	public Type bcBlockType = Type.MACHINE;
	
	public float storedEnergy;
	public final float maxEnergyStore;

	public TilePowered(int invSize, float energyStore)
	{
		super(invSize);
		this.maxEnergyStore = energyStore;
		this.initBuildCraft();
	}
	
	@Override
	public void updateEntity()
	{
		// Register to the IC2 Network
		if (!this.worldObj.isRemote)
		{
			if (!this.isAddedToEnergyNet)
			{
				this.initIC();
			}

			if (this.powerHandler == null)
			{
				this.initBuildCraft();
			}

			if (Compatibility.isBuildcraftLoaded())
			{
				if (this.powerHandler.getEnergyStored() > 0)
				{
					/**
					 * Cheat BuildCraft powerHandler and always empty energy inside of it.
					 */
					this.receiveElectricity(this.powerHandler.getEnergyStored() * Compatibility.BC3_RATIO, true);
					this.powerHandler.setEnergy(0);
				}
			}
		}
	}
	
	public int getScaledEnergy(int size)
	{
		return Math.round(storedEnergy * size / maxEnergyStore);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		setEnergyStored(tag.getFloat("energy"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		tag.setFloat("energy", storedEnergy);
	}
	
	public boolean useEnergy(float energy, boolean shouldUse)
	{
		if(storedEnergy - energy > 0.0F)
		{
			if(shouldUse)
				storedEnergy -= energy;
			return true;
		}
		return false;
	}
	
	/*IC2 SUPORT*/

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
	{
		return true;
	}

	@Override
	public double demandedEnergyUnits()
	{
		return Math.ceil(this.getRequest(ForgeDirection.UNKNOWN) * Compatibility.TO_IC2_RATIO);
	}

	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount)
	{
		float convertedEnergy = (float) (amount * Compatibility.IC2_RATIO);
		ElectricityPack toSend = ElectricityPack.getFromWatts(convertedEnergy, this.getVoltage());
		float receive = this.receiveElectricity(directionFrom, toSend, true);

		// Return the difference, since injectEnergy returns left over energy, and
		// receiveElectricity returns energy used.
		return Math.round(amount - (receive * Compatibility.TO_IC2_RATIO));
	}

	@Override
	public int getMaxSafeInput()
	{
		return 2048;
	}
	
	@Override
	public void invalidate()
	{
		this.unloadTileIC2();
		super.invalidate();
	}

	@Override
	public void onChunkUnload()
	{
		this.unloadTileIC2();
		super.onChunkUnload();
	}

	protected void initIC()
	{
		if (Compatibility.isIndustrialCraft2Loaded())
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
		}

		this.isAddedToEnergyNet = true;
	}

	private void unloadTileIC2()
	{
		if (this.isAddedToEnergyNet && this.worldObj != null)
		{
			if (Compatibility.isIndustrialCraft2Loaded())
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}

			this.isAddedToEnergyNet = false;
		}
	}
	
	/*BUILDCRAFT SUPORT**/
	
	public void initBuildCraft()
	{
		if (this.powerHandler == null)
		{
			this.powerHandler = new PowerHandler(this, this.bcBlockType);
		}
		this.powerHandler.configure(0, 100, 0, (int) Math.ceil(this.getMaxEnergyStored() * Compatibility.BC3_RATIO));
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side)
	{
		this.initBuildCraft();
		return powerHandler.getPowerReceiver();
	}

	@Override
	public World getWorld()
	{
		return this.worldObj;
	}
	
	/*UNIVERSAL ELECTRICY SUPORT*/

	@Override
	public boolean canConnect(ForgeDirection direction)
	{
		return true;
	}

	@Override
	public void setEnergyStored(float energy)
	{
		this.storedEnergy = Math.max(Math.min(energy, this.getMaxEnergyStored()), 0);
	}

	@Override
	public float getEnergyStored()
	{
		return this.storedEnergy;
	}

	@Override
	public float getMaxEnergyStored()
	{
		return this.maxEnergyStore;
	}

	@Override
	public float receiveElectricity(ForgeDirection from, ElectricityPack receive, boolean doReceive)
	{
		if (receive != null)
		{
			float prevEnergyStored = this.getEnergyStored();
			float newStoredEnergy = Math.min(this.getEnergyStored() + receive.getWatts(), this.getMaxEnergyStored());

			if (doReceive)
			{
				this.setEnergyStored(newStoredEnergy);
			}

			return Math.max(newStoredEnergy - prevEnergyStored, 0);
		}

		return 0;
	}
	/**Non-side specific version of recieveElectricity*/
	public float receiveElectricity(ElectricityPack receive, boolean doReceive)
	{
		if (receive != null)
		{
			float prevEnergyStored = this.getEnergyStored();
			float newStoredEnergy = Math.min(this.getEnergyStored() + receive.getWatts(), this.getMaxEnergyStored());

			if (doReceive)
			{
				this.setEnergyStored(newStoredEnergy);
			}

			return Math.max(newStoredEnergy - prevEnergyStored, 0);
		}

		return 0;
	}
	/**Non-side specific version of recieveElectricity that uses a float instead of ElectricityPack*/
	public float receiveElectricity(float energy, boolean doReceive)
	{
		return this.receiveElectricity(ElectricityPack.getFromWatts(energy, this.getVoltage()), doReceive);
	}

	@Override
	public ElectricityPack provideElectricity(ForgeDirection from, ElectricityPack request, boolean doProvide)
	{
		return null;
	}

	@Override
	public float getRequest(ForgeDirection direction)
	{
		return Math.max(maxEnergyStore - this.storedEnergy, 0);
	}

	@Override
	public float getProvide(ForgeDirection direction)
	{
		return 0;
	}

	@Override
	public float getVoltage()
	{
		return 0.120F;
	}
}
