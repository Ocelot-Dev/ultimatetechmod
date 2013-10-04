package ocelot.mods.utm.common.gui;

import ocelot.mods.utm.UltimateTechMod;
import ocelot.mods.utm.common.inventory.InventoryMouldMaker;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ContainerMouldMaker extends UTMContainerNoEntity
{
	public InventoryMouldMaker craftMatrix = new InventoryMouldMaker(this);
	public int index;

	public ContainerMouldMaker(InventoryPlayer inv, World world, int xPos, int yPos, int zPos)
	{
		super(inv, world, xPos, yPos, zPos);
		
		this.addPlayerInventory(inv, 8, 84);
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
    {
		ItemStack stack = new ItemStack(UltimateTechMod.mould);
		stack.setTagCompound(getTagForMould());
        this.craftMatrix.setInventorySlotContents(1, stack);
    }
	
	public NBTTagCompound getTagForMould()
	{
		//TODO make this work!
		return null;
	}
}
