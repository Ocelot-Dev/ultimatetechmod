package ocelot.mods.utm.common.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;

public abstract class TileEngine extends TileInventory implements IPowerReceptor, IPowerEmitter, IPipeConnection
{
	protected PowerHandler powerHandler;

	public TileEngine()
	{
		super(0);
		
		powerHandler = new PowerHandler(this, Type.ENGINE);
		powerHandler.configurePowerPerdition(1, 100);
	}

	@Override
	public abstract boolean canEmitPowerFrom(ForgeDirection side);

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		if (type == PipeType.POWER)
			return ConnectOverride.DEFAULT;
		return ConnectOverride.DISCONNECT;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side)
	{
		return this.powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public World getWorld()
	{
		return this.worldObj;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		// TODO Auto-generated method stub
		
	}
}
