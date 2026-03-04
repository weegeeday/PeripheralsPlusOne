package com.austinv11.peripheralsplusplus.tiles;

import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class TileEntityResupplyStation extends BlockEntity implements MenuProvider, Container {

private static final int SIZE = 56;
private final ItemStack[] items = new ItemStack[SIZE];

public TileEntityResupplyStation(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.RESUPPLY_STATION.get(), pos, state);
for (int i = 0; i < SIZE; i++) items[i] = ItemStack.EMPTY;
}

public synchronized boolean resupply(ITurtleAccess turtle, int toSlot, ResourceLocation id) {
Item item = ForgeRegistries.ITEMS.getValue(id);
if (item == null) return false;
ItemStack currentStack = turtle.getInventory().getItem(toSlot);
if (!currentStack.isEmpty() && !currentStack.is(item)) return false;
int amountToFill = currentStack.isEmpty() ? item.getMaxStackSize() :
currentStack.getMaxStackSize() - currentStack.getCount();
for (int i = 0; i < SIZE && amountToFill > 0; i++) {
ItemStack slot = items[i];
if (slot.isEmpty() || !slot.is(item)) continue;
int toTake = Math.min(amountToFill, slot.getCount());
slot.shrink(toTake);
amountToFill -= toTake;
}
if (amountToFill == item.getMaxStackSize() - (currentStack.isEmpty() ? 0 : currentStack.getCount()))
return false; // nothing was taken
int added = item.getMaxStackSize() - (currentStack.isEmpty() ? 0 : currentStack.getCount()) - amountToFill;
if (currentStack.isEmpty())
turtle.getInventory().setItem(toSlot, new ItemStack(item, added));
else
currentStack.grow(added);
setChanged();
return true;
}

// Container
@Override
public int getContainerSize() { return SIZE; }
@Override
public boolean isEmpty() { for (ItemStack s : items) if (!s.isEmpty()) return false; return true; }
@Override
public ItemStack getItem(int i) { return i < SIZE ? items[i] : ItemStack.EMPTY; }
@Override
public ItemStack removeItem(int i, int amt) { if (i < SIZE) { ItemStack s = items[i].split(amt); setChanged(); return s; } return ItemStack.EMPTY; }
@Override
public ItemStack removeItemNoUpdate(int i) { if (i < SIZE) { ItemStack s = items[i]; items[i] = ItemStack.EMPTY; return s; } return ItemStack.EMPTY; }
@Override
public void setItem(int i, ItemStack s) { if (i < SIZE) { items[i] = s; setChanged(); } }
@Override
public boolean stillValid(Player player) { return Container.stillValidBlockEntity(this, player); }
@Override
public void clearContent() { for (int i = 0; i < SIZE; i++) items[i] = ItemStack.EMPTY; }

// MenuProvider
@Override
public Component getDisplayName() { return Component.translatable("block.peripheralsplusone.resupply_station"); }
@Nullable
@Override
public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
return new com.austinv11.peripheralsplusplus.tiles.containers.ContainerResupplyStation(id, playerInv, this);
}
}
