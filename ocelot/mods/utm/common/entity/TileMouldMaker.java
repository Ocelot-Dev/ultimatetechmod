package ocelot.mods.utm.common.entity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.transport.IPipeTile.PipeType;

public class TileMouldMaker extends TileInventory
{

	public TileMouldMaker()
	{
		super(2);
	}

	@Override
	public String getInvName()
	{
		return "tile.MouldMaker";
	}

	@Override
	public boolean canUpdate ()
    {
        return false;
    }
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
	{
		return false;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with)
	{
		return ConnectOverride.DISCONNECT;
	}

	@Override
	public void getGUINetworkData(int i, int data)
	{}

	@Override
	public void sendGUINetworkData(Container container, ICrafting iCrafting)
	{}

	@Override
	public boolean doesSideNotChangeActive(ForgeDirection side)
	{
		return true;
	}

}
