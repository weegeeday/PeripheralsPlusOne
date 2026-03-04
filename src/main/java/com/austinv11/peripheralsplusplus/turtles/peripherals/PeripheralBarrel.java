package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

public class PeripheralBarrel implements IPlusPlusPeripheral {

private int MAX_SIZE = 4096;
private int STACK_SIZE = 64;
private Item ITEM_TYPE_STORED;
private int ITEM_META_STORED = 0;
private int CURRENT_USAGE = 0;
private final ITurtleAccess turtle;
private final TurtleSide side;
public boolean changed = false;
private CompoundTag itemStoredTag;

public PeripheralBarrel(ITurtleAccess turtle, TurtleSide side) {
this.turtle = turtle;
this.side = side;
CompoundTag tag = turtle.getUpgradeNBTData(side);
if (tag.getInt("maxSize") > 0)
MAX_SIZE = tag.getInt("maxSize");
if (tag.getInt("stackSize") > 0)
STACK_SIZE = tag.getInt("stackSize");
CURRENT_USAGE = tag.getInt("currentUsage");
if (tag.getBoolean("isKnown")) {
ITEM_TYPE_STORED = ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("itemID")));
ITEM_META_STORED = tag.getInt("stackMeta");
if (tag.contains("itemTag"))
itemStoredTag = tag.getCompound("itemTag");
}
checkUsageStats();
}

private void checkUsageStats() {
if (CURRENT_USAGE <= 0 || ITEM_TYPE_STORED == null) {
CURRENT_USAGE = 0;
STACK_SIZE = 64;
MAX_SIZE = 64 * STACK_SIZE;
ITEM_TYPE_STORED = null;
ITEM_META_STORED = 0;
itemStoredTag = null;
}
}

@Override
public String getType() {
return "barrel";
}

@LuaFunction
public final Object[] get(IArguments args) throws LuaException {
if (!Config.enableBarrelTurtle)
throw new LuaException("Barrel Turtles have been disabled");
int amount = args.count() > 0 ? args.getInt(0) : STACK_SIZE;
if (CURRENT_USAGE < amount) amount = CURRENT_USAGE;
if (ITEM_TYPE_STORED == null) return new Object[]{0};
ItemStack slot = turtle.getInventory().getItem(turtle.getSelectedSlot());
int stackCount = amount;
if (!slot.isEmpty()) {
ItemStack compareStack = new ItemStack(ITEM_TYPE_STORED, stackCount);
compareStack.setTag(itemStoredTag);
if (!ItemStack.isSameItem(slot, compareStack))
throw new LuaException("Item mismatch");
if (amount + slot.getCount() > STACK_SIZE)
amount = STACK_SIZE - slot.getCount();
stackCount = amount + slot.getCount();
}
ItemStack stack = new ItemStack(ITEM_TYPE_STORED, stackCount);
stack.setTag(itemStoredTag);
CURRENT_USAGE -= amount;
checkUsageStats();
turtle.getInventory().setItem(turtle.getSelectedSlot(), stack.copy());
changed = true;
return new Object[]{amount};
}

@LuaFunction
public final Object[] put(IArguments args) throws LuaException {
if (!Config.enableBarrelTurtle)
throw new LuaException("Barrel Turtles have been disabled");
int amount = args.count() > 0 ? args.getInt(0) : 64;
ItemStack items = turtle.getInventory().getItem(turtle.getSelectedSlot());
if (items.isEmpty()) return new Object[]{0};
items = items.copy();
if (amount > items.getCount()) amount = items.getCount();
if (amount > (MAX_SIZE - CURRENT_USAGE)) amount = MAX_SIZE - CURRENT_USAGE;
if (ITEM_TYPE_STORED != null) {
ItemStack temp = new ItemStack(ITEM_TYPE_STORED, 1);
temp.setTag(itemStoredTag);
if (!ItemStack.isSameItem(temp, items))
throw new LuaException("Item mismatch");
} else {
ITEM_TYPE_STORED = items.getItem();
ITEM_META_STORED = items.getDamageValue();
itemStoredTag = items.getTag();
STACK_SIZE = items.getItem().getMaxStackSize();
MAX_SIZE = 64 * STACK_SIZE;
}
CURRENT_USAGE += amount;
int remaining = items.getCount() - amount;
turtle.getInventory().setItem(turtle.getSelectedSlot(),
remaining <= 0 ? ItemStack.EMPTY : new ItemStack(items.getItem(), remaining));
changed = true;
return new Object[]{amount};
}

@LuaFunction
public final Object[] getUnlocalizedName(IArguments args) {
if (ITEM_TYPE_STORED != null)
return new Object[]{ForgeRegistries.ITEMS.getKey(ITEM_TYPE_STORED) != null ?
ForgeRegistries.ITEMS.getKey(ITEM_TYPE_STORED).toString() : null};
return new Object[0];
}

@LuaFunction
public final Object[] getLocalizedName(IArguments args) {
if (ITEM_TYPE_STORED != null)
return new Object[]{new ItemStack(ITEM_TYPE_STORED).getHoverName().getString()};
return new Object[0];
}

@LuaFunction
public final Object[] getItemID(IArguments args) {
if (ITEM_TYPE_STORED != null) {
ResourceLocation key = ForgeRegistries.ITEMS.getKey(ITEM_TYPE_STORED);
return new Object[]{key != null ? key.toString() : null};
}
return new Object[0];
}

@LuaFunction
public final Object[] getAmount(IArguments args) {
return new Object[]{ITEM_TYPE_STORED != null ? CURRENT_USAGE : 0};
}

@LuaFunction
public final Object[] getOreDictEntries(IArguments args) {
return new Object[]{new HashMap<>()};
}

@LuaFunction
public final Object[] getNbtTag(IArguments args) {
if (itemStoredTag != null) return new Object[]{itemStoredTag.toString()};
return new Object[0];
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}

public void update() {
CompoundTag tag = turtle.getUpgradeNBTData(side);
tag.putInt("maxSize", MAX_SIZE);
tag.putInt("stackSize", STACK_SIZE);
tag.putInt("currentUsage", CURRENT_USAGE);
if (ITEM_TYPE_STORED == null) {
tag.putBoolean("isKnown", false);
} else {
tag.putBoolean("isKnown", true);
ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(ITEM_TYPE_STORED);
tag.putString("itemID", itemId == null ? "" : itemId.toString());
tag.putInt("stackMeta", ITEM_META_STORED);
if (itemStoredTag != null)
tag.put("itemTag", itemStoredTag);
}
turtle.updateUpgradeNBTData(side);
changed = false;
}
}
