package com.austinv11.peripheralsplusplus.tiles.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerResupplyStation extends AbstractContainerMenu {
public ContainerResupplyStation(int syncId, Inventory playerInv, net.minecraft.world.Container inv) {
super(null, syncId);
// 56-slot chest layout
for (int row = 0; row < 6; row++)
for (int col = 0; col < 9; col++)
addSlot(new Slot(inv, col + row * 9, 8 + col * 18, 18 + row * 18));
for (int row = 0; row < 3; row++)
for (int col = 0; col < 9; col++)
addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
for (int col = 0; col < 9; col++)
addSlot(new Slot(playerInv, col, 8 + col * 18, 198));
}

@Override
public ItemStack quickMoveStack(Player player, int index) { return ItemStack.EMPTY; }

@Override
public boolean stillValid(Player player) { return true; }
}
