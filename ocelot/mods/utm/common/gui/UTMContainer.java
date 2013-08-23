package ocelot.mods.utm.common.gui;

import ocelot.mods.utm.common.entity.TileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class UTMContainer extends Container
{
	protected TileBase tileEntity;
	protected InventoryPlayer invPlayer;

	public UTMContainer(TileBase te, InventoryPlayer inv)
	{
		this.tileEntity = te;
		this.invPlayer = inv;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

}
