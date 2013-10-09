package ocelot.mods.utm.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;
import ocelot.mods.utm.common.entity.TileMouldMaker;

public class UTMContainerNoEntity extends UTMContainer
{
	protected InventoryPlayer playerInv;
	protected World world;
	protected int xPos;
	protected int yPos;
	protected int zPoz;

	public UTMContainerNoEntity(InventoryPlayer inv, World world, int xPos, int yPos, int zPos)
	{
		super(inv);
		
		this.playerInv = inv;
		this.world = world;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPoz = zPos;
	}
}
