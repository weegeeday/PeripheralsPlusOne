package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;

public class PeripheralDispenser implements IPeripheral {

private final ITurtleAccess turtle;

public PeripheralDispenser(ITurtleAccess turtle) {
this.turtle = turtle;
}

@Override
public String getType() {
return "flinging";
}

@LuaFunction
public final Object[] dispense(IArguments args) throws LuaException {
return doDispense(turtle.getDirection(), args);
}

@LuaFunction
public final Object[] dispenseUp(IArguments args) throws LuaException {
return doDispense(Direction.UP, args);
}

@LuaFunction
public final Object[] dispenseDown(IArguments args) throws LuaException {
return doDispense(Direction.DOWN, args);
}

private Object[] doDispense(Direction direction, IArguments args) throws LuaException {
if (!Config.enableFlingingTurtle)
throw new LuaException("Flinging turtles have been disabled");
int slot = args.count() > 0 ? args.getInt(0) : turtle.getSelectedSlot();
synchronized (this) {
ItemStack stack = turtle.getInventory().getItem(slot);
if (!stack.isEmpty()) {
DispenseItemBehavior behavior = DispenserBlock.DISPENSER_REGISTRY.get(stack.getItem());
if (behavior != null) {
BlockPos pos = turtle.getPosition();
// Create a fake dispenser source
ItemStack result = dispenseItem(behavior, stack, pos, direction, (ServerLevel) turtle.getLevel());
turtle.getInventory().setItem(slot, result);
}
}
}
return new Object[0];
}

private ItemStack dispenseItem(DispenseItemBehavior behavior, ItemStack stack, BlockPos pos, Direction dir, ServerLevel level) {
// Simplified: just drop the item in the direction
BlockPos target = pos.relative(dir);
level.addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(level,
target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5,
stack.copy()));
return ItemStack.EMPTY;
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}
}
