package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileEntityInteractiveSorter extends BlockEntity implements IPlusPlusPeripheral, MenuProvider, Container {

private ItemStack slot0 = ItemStack.EMPTY;
private final List<IComputerAccess> computers = new ArrayList<>();

public TileEntityInteractiveSorter(BlockPos pos, BlockState state) {
super(null, pos, state);
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
if (tag.contains("slot0"))
slot0 = ItemStack.of(tag.getCompound("slot0"));
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
if (!slot0.isEmpty())
tag.put("slot0", slot0.save(new CompoundTag()));
}

@Override
public String getType() {
return "interactiveSorter";
}

@LuaFunction
public final Object[] analyze(IArguments args) throws LuaException {
if (!Config.enableInteractiveSorter)
throw new LuaException("Interactive Sorters have been disabled");
return new Object[]{getItemInfo(slot0)};
}

@LuaFunction
public final Object[] push(IArguments args) throws LuaException {
if (!Config.enableInteractiveSorter)
throw new LuaException("Interactive Sorters have been disabled");
if (slot0.isEmpty())
return new Object[]{false};
Direction dir = parseDirection(args.getString(0));
int amount = args.count() > 1 ? Math.min(args.getInt(1), slot0.getCount()) : slot0.getCount();

IItemHandler inv = getInventoryForSide(dir);
if (inv == null) {
// Drop item
BlockPos pos = getBlockPos().relative(dir);
level.addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(level,
pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, slot0.split(amount)));
setChanged();
return new Object[]{true};
}

ItemStack toInsert = slot0.copy();
toInsert.setCount(amount);
ItemStack remainder = ItemHandlerHelper.insertItemStacked(inv, toInsert, false);
slot0.shrink(amount - remainder.getCount());
if (slot0.getCount() <= 0) slot0 = ItemStack.EMPTY;
setChanged();
return new Object[]{remainder.getCount() < amount};
}

@LuaFunction
public final Object[] pull(IArguments args) throws LuaException {
if (!Config.enableInteractiveSorter)
throw new LuaException("Interactive Sorters have been disabled");
Direction dir = parseDirection(args.getString(0));
int amount = args.count() > 1 ? args.getInt(1) : Integer.MAX_VALUE;

IItemHandler inv = getInventoryForSide(dir);
if (inv == null)
throw new LuaException("Block is not a valid inventory");

for (int i = 0; i < inv.getSlots(); i++) {
ItemStack found = inv.getStackInSlot(i);
if (!found.isEmpty() && (slot0.isEmpty() || ItemStack.isSameItemSameTags(slot0, found))) {
int toTake = Math.min(amount, found.getCount());
ItemStack taken = inv.extractItem(i, toTake, false);
if (!taken.isEmpty()) {
if (slot0.isEmpty()) {
slot0 = taken;
} else {
slot0.grow(taken.getCount());
}
setChanged();
for (IComputerAccess computer : computers)
computer.queueEvent("itemReady", null);
return new Object[]{true};
}
}
}
return new Object[]{false};
}

@LuaFunction
public final Object[] isInventoryPresent(IArguments args) throws LuaException {
if (!Config.enableInteractiveSorter)
throw new LuaException("Interactive Sorters have been disabled");
Direction dir = parseDirection(args.getString(0));
return new Object[]{getInventoryForSide(dir) != null};
}

private Direction parseDirection(String s) throws LuaException {
try {
return Direction.valueOf(s.toUpperCase());
} catch (IllegalArgumentException e) {
throw new LuaException("Invalid direction: " + s);
}
}

@Nullable
private IItemHandler getInventoryForSide(Direction dir) {
BlockPos pos = getBlockPos().relative(dir);
BlockEntity te = level.getBlockEntity(pos);
if (te == null) return null;
return te.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).orElse(null);
}

private HashMap<String, Object> getItemInfo(ItemStack stack) {
if (stack.isEmpty()) return null;
HashMap<String, Object> map = new HashMap<>();
map.put("amount", stack.getCount());
ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
map.put("stringId", id != null ? id.toString() : "unknown");
map.put("name", stack.getHoverName().getString());
if (stack.hasTag())
map.put("nbt", stack.getTag().toString());
return map;
}

@Override
public void attach(IComputerAccess computer) { computers.add(computer); }
@Override
public void detach(IComputerAccess computer) { computers.remove(computer); }
@Override
public boolean equals(IPeripheral other) { return other == this; }

// Container
@Override
public int getContainerSize() { return 1; }
@Override
public boolean isEmpty() { return slot0.isEmpty(); }
@Override
public ItemStack getItem(int i) { return i == 0 ? slot0 : ItemStack.EMPTY; }
@Override
public ItemStack removeItem(int i, int amount) {
if (i == 0) { ItemStack s = slot0.split(amount); setChanged(); return s; } return ItemStack.EMPTY;
}
@Override
public ItemStack removeItemNoUpdate(int i) {
if (i == 0) { ItemStack s = slot0; slot0 = ItemStack.EMPTY; return s; } return ItemStack.EMPTY;
}
@Override
public void setItem(int i, ItemStack stack) {
if (i == 0) {
slot0 = stack;
if (!stack.isEmpty())
for (IComputerAccess computer : computers)
computer.queueEvent("itemReady", null);
setChanged();
}
}
@Override
public boolean stillValid(Player player) { return Container.stillValidBlockEntity(this, player); }
@Override
public void clearContent() { slot0 = ItemStack.EMPTY; }

// MenuProvider
@Override
public Component getDisplayName() { return Component.translatable("block.peripheralsplusplus.interactive_sorter"); }
@Nullable
@Override
public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
return new com.austinv11.peripheralsplusplus.tiles.containers.ContainerInteractiveSorter(id, playerInv, this);
}
}
