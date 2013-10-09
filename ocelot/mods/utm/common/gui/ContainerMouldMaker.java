package ocelot.mods.utm.common.gui;

import ocelot.mods.utm.UltimateTechMod;
import ocelot.mods.utm.common.gui.slot.SlotFilter;
import ocelot.mods.utm.common.inventory.InventoryMouldMaker;
import ocelot.mods.utm.common.utils.MachineRecipes;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
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
		
		this.addSlotToContainer(new SlotFilter(inv, 36, 50, 53, new ItemStack(Item.clay)));
		this.addSlotToContainer(new SlotCrafting(inv.player, craftMatrix, inv, 37, 112, 53));
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
		return MachineRecipes.getMouldTag(index);
	}
}
