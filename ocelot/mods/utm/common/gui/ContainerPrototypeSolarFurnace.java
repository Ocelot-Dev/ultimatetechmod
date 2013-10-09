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
import ocelot.mods.utm.common.entity.TilePrototypeSolarFurnace;

public class ContainerPrototypeSolarFurnace extends UTMContainer
{
	private TilePrototypeSolarFurnace furnace;
	
	private int lastSmeltTime;
	private boolean lastCanSeeSky;

	public ContainerPrototypeSolarFurnace(TilePrototypeSolarFurnace te, InventoryPlayer inv)
	{
		super(inv);
		furnace = te;
		addSlotToContainer(new Slot(te, 0, 56, 35));
		addSlotToContainer(new SlotFurnace(inv.player, te, 1, 116, 35));
		this.addPlayerInventory(inv, 8, 84);
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.smelttime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.getIsOn() == true ? 1 : 0);
    }
	
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastSmeltTime != this.furnace.smelttime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.furnace.smelttime);
            }

            if (this.lastCanSeeSky != this.furnace.getIsOn())
            {
                icrafting.sendProgressBarUpdate(this, 1, this.furnace.getIsOn() == true ? 1 : 0);
            }

        }

        this.lastSmeltTime = this.furnace.smelttime;
        this.lastCanSeeSky = this.furnace.getIsOn();
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.furnace.smelttime = par2;
        }

        if (par1 == 1)
        {
            this.furnace.setIsOn(par2);
        }
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
