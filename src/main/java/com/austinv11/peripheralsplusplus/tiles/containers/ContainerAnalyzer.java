package com.austinv11.peripheralsplusplus.tiles.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerAnalyzer extends AbstractContainerMenu {

public ContainerAnalyzer(int syncId, Inventory playerInv, net.minecraft.world.Container inv) {
super(null, syncId);
addSlot(new Slot(inv, 0, 80, 34));
layoutPlayerInventory(playerInv, 8, 84);
}

protected void layoutPlayerInventory(Inventory playerInv, int left, int top) {
for (int row = 0; row < 3; row++)
for (int col = 0; col < 9; col++)
addSlot(new Slot(playerInv, col + row * 9 + 9, left + col * 18, top + row * 18));
for (int col = 0; col < 9; col++)
addSlot(new Slot(playerInv, col, left + col * 18, top + 58));
}

@Override
public ItemStack quickMoveStack(net.minecraft.world.entity.player.Player player, int index) {
return ItemStack.EMPTY;
}

@Override
public boolean stillValid(net.minecraft.world.entity.player.Player player) {
return true;
}
}
