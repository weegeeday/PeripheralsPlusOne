package com.austinv11.peripheralsplusplus.tiles.containers;

import com.austinv11.peripheralsplusplus.init.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerPlayerInterface extends AbstractContainerMenu {
public ContainerPlayerInterface(int syncId, Inventory playerInv, net.minecraft.world.Container inv) {
super(null, syncId);
for (int i = 0; i < 8; i++)
addSlot(new Slot(inv, i, i * 18 + 16, 35) {
@Override
public boolean mayPlace(ItemStack stack) {
return stack.is(ModItems.PERM_CARD.get());
}
});
for (int row = 0; row < 3; row++)
for (int col = 0; col < 9; col++)
addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
for (int col = 0; col < 9; col++)
addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
}

@Override
public ItemStack quickMoveStack(Player player, int index) { return ItemStack.EMPTY; }

@Override
public boolean stillValid(Player player) { return true; }
}
