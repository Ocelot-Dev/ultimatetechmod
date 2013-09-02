package ocelot.mods.utm.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.SlotFurnace;
import ocelot.mods.utm.common.entity.TileBase;
import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;

public class ContainerPrototypeSolarFurnace extends UTMContainer
{

	public ContainerPrototypeSolarFurnace(TilePrototypeSolarFurnace te, InventoryPlayer inv)
	{
		super(te, inv);
		addSlotToContainer(new SlotFurnace(inv.player, te, 0, 56, 35));
		this.addPlayerInventory(inv, 8, 84);
	}

}
