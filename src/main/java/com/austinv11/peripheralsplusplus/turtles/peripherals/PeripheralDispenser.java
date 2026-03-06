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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class PeripheralDispenser implements IPeripheral {

@SuppressWarnings("unchecked")
private static final java.util.Map<Item, DispenseItemBehavior> DISPENSER_REG;
static {
    java.util.Map<Item, DispenseItemBehavior> reg = null;
    try {
        java.lang.reflect.Field f = DispenserBlock.class.getDeclaredField("DISPENSER_REGISTRY");
        f.setAccessible(true);
        reg = (java.util.Map<Item, DispenseItemBehavior>) f.get(null);
    } catch (ReflectiveOperationException | ClassCastException e) {
        // In obfuscated environments the field name differs; dispense behavior will be unavailable
        // (dispense/dispenseUp/dispenseDown will silently return without dispensing)
        org.apache.logging.log4j.LogManager.getLogger("PeripheralsPlusOne")
            .warn("PeripheralDispenser: could not access DispenserBlock.DISPENSER_REGISTRY via reflection; dispense behavior disabled", e);
    }
    DISPENSER_REG = reg;
}

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
DispenseItemBehavior behavior = DISPENSER_REG != null ? DISPENSER_REG.get(stack.getItem()) : null;
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
