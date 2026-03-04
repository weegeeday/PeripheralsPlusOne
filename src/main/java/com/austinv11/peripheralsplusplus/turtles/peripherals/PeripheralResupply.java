package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.blocks.BlockResupplyStation;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.tiles.TileEntityResupplyStation;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class PeripheralResupply implements IPlusPlusPeripheral {

private final ITurtleAccess turtle;
private BlockPos linkedStation;

public PeripheralResupply(ITurtleAccess turtle) {
this.turtle = turtle;
}

@Override
public String getType() {
return "resupply";
}

@LuaFunction
public final Object[] link(IArguments args) throws LuaException {
if (!Config.enableResupplyStation)
throw new LuaException("The resupply station has been disabled");
if (args.count() >= 3) {
linkedStation = new BlockPos(args.getInt(0), args.getInt(1), args.getInt(2));
return new Object[]{true};
}
String dirStr = args.getString(0);
Direction dir;
try {
dir = Direction.valueOf(dirStr.toUpperCase());
} catch (IllegalArgumentException e) {
throw new LuaException("Invalid direction: " + dirStr);
}
BlockPos newLink = turtle.getPosition().relative(dir);
Level level = turtle.getLevel();
if (level.isEmptyBlock(newLink) || !(level.getBlockState(newLink).getBlock() instanceof BlockResupplyStation))
return new Object[]{false};
linkedStation = newLink;
return new Object[]{true};
}

@LuaFunction
public final Object[] resupply(IArguments args) throws LuaException {
if (!Config.enableResupplyStation)
throw new LuaException("The resupply station has been disabled");
if (linkedStation == null)
throw new LuaException("A station has not been linked!");
Level level = turtle.getLevel();
if (level.isEmptyBlock(linkedStation) ||
!(level.getBlockState(linkedStation).getBlock() instanceof BlockResupplyStation))
throw new LuaException("The linked station is nonexistent!");

int slot = args.count() > 0 ? args.getInt(0) - 1 : turtle.getSelectedSlot();
ResourceLocation id;
if (args.count() > 1) {
id = new ResourceLocation(args.getString(1));
} else {
ItemStack stack = turtle.getInventory().getItem(slot);
if (stack.isEmpty()) return new Object[]{false};
id = ForgeRegistries.ITEMS.getKey(stack.getItem());
if (id == null) return new Object[]{false};
}

BlockEntity te = level.getBlockEntity(linkedStation);
if (!(te instanceof TileEntityResupplyStation station))
throw new LuaException("No resupply station at linked position");

return new Object[]{station.resupply(turtle, slot, id)};
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}
}
