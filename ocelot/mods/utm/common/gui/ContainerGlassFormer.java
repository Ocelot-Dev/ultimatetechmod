package ocelot.mods.utm.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import ocelot.mods.utm.UltimateTechMod;
import ocelot.mods.utm.common.entity.TileGlassFormer;
import ocelot.mods.utm.common.gui.slot.SlotFilter;
import ocelot.mods.utm.common.utils.MachineRecipes;

public class ContainerGlassFormer extends UTMContainer
{
	private TileGlassFormer tile;

	public ContainerGlassFormer(TileGlassFormer te, InventoryPlayer inv)
	{
		super(te, inv);
		
		tile = te;
		addSlotToContainer(new SlotFilter(te, 0, 25, 59, MachineRecipes.getGlassItems()));
		addSlotToContainer(new SlotFilter(te, 1, 46, 59, MachineRecipes.getReflective()));
		addSlotToContainer(new SlotFilter(te, 2, 76, 35, new ItemStack(UltimateTechMod.mould)));
		addSlotToContainer(new SlotFurnace(inv.player, te, 3, 139, 35));
		this.addPlayerInventory(inv, 8, 84);
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        
        tile.sendGUINetworkData(this, par1ICrafting);
    }
	
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
        	tile.sendGUINetworkData(this, (ICrafting)this.crafters.get(i));
        }
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        tile.getGUINetworkData(par1, par2);
    }

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNum);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNum == 1)
            {
                if (!this.mergeItemStack(itemstack1, 2, 38, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotNum != 1 && slotNum != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (slotNum >= 2 && slotNum < 29)
                {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false))
                    {
                        return null;
                    }
                }
                else if (slotNum >= 29 && slotNum < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 2, 38, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}
