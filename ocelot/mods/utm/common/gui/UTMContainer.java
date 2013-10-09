package ocelot.mods.utm.common.gui;

import ocelot.mods.utm.common.entity.TileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class UTMContainer extends Container
{
	protected InventoryPlayer invPlayer;

	public UTMContainer(InventoryPlayer inv)
	{
		this.invPlayer = inv;
	}
	
	public void addPlayerInventory(InventoryPlayer inv, int startX, int startY)
	{
		for (int l = 0; l < 3; l++) {
			for (int k1 = 0; k1 < 9; k1++) {
				addSlotToContainer(new Slot(inv, k1 + l * 9 + 9, startX + k1 * 18, startY + l * 18));
			}

		}

		for (int i1 = 0; i1 < 9; i1++) {
			addSlotToContainer(new Slot(inv, i1, startX + i1 * 18, startY + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

}
