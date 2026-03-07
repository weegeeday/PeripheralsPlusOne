package com.austinv11.peripheralsplusplus.tiles.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ContainerEmpty extends AbstractContainerMenu {
public ContainerEmpty(int syncId, Inventory playerInv) {
super(null, syncId);
}

@Override
public ItemStack quickMoveStack(Player player, int index) { return ItemStack.EMPTY; }

@Override
public boolean stillValid(Player player) { return true; }
}
