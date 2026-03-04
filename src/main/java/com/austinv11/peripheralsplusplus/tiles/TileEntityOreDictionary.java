package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityOreDictionary extends BlockEntity implements IPlusPlusPeripheral {

private ITurtleAccess turtle = null;

public TileEntityOreDictionary(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ORE_DICTIONARY.get(), pos, state);
}

public TileEntityOreDictionary(ITurtleAccess turtle) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ORE_DICTIONARY.get(), turtle.getPosition(), turtle.getLevel().getBlockState(turtle.getPosition()));
this.turtle = turtle;
}

@Override
public String getType() {
return "oreDictionary";
}

private boolean isTurtle() {
return turtle != null;
}

@LuaFunction
public final Object[] getEntries(IArguments args) throws LuaException {
if (!Config.enableOreDictionary)
throw new LuaException("Ore Dictionaries have been disabled");
if (!isTurtle())
throw new LuaException("Only available on turtles");
ItemStack slot = turtle.getInventory().getItem(turtle.getSelectedSlot());
if (slot.isEmpty())
throw new LuaException("Empty slot");
// Tags replace ore dict in 1.20.1
java.util.HashMap<Integer, String> entries = new java.util.HashMap<>();
int i = 1;
for (net.minecraft.tags.TagKey<net.minecraft.world.item.Item> tag : slot.getItem().builtInRegistryHolder().tags().toList()) {
entries.put(i++, tag.location().toString());
}
return new Object[]{entries};
}

@LuaFunction
public final Object[] doItemsMatch(IArguments args) throws LuaException {
if (!Config.enableOreDictionary)
throw new LuaException("Ore Dictionaries have been disabled");
if (!isTurtle())
throw new LuaException("Only available on turtles");
int slot1 = args.getInt(0) - 1;
int slot2 = args.getInt(1) - 1;
if (slot1 == slot2)
return new Object[]{true};
ItemStack stack1 = turtle.getInventory().getItem(slot1);
ItemStack stack2 = turtle.getInventory().getItem(slot2);
if (stack1.isEmpty() || stack2.isEmpty())
return new Object[]{false};
return new Object[]{ItemStack.isSameItemSameTags(stack1, stack2)};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}
}
