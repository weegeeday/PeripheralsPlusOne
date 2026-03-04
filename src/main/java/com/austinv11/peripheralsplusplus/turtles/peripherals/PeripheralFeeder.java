package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class PeripheralFeeder implements IPlusPlusPeripheral {

private final ITurtleAccess turtle;

public PeripheralFeeder(ITurtleAccess turtle) {
this.turtle = turtle;
}

@Override
public String getType() {
return "feeder";
}

@LuaFunction
public final Object[] feed(IArguments args) throws LuaException {
if (!Config.enableFeederTurtle)
throw new LuaException("Feeder Turtles have been disabled");
ItemStack curItem = turtle.getInventory().getItem(turtle.getSelectedSlot());
if (curItem.isEmpty()) return new Object[]{false};
BlockPos pos = turtle.getPosition();
AABB box = new AABB(pos.getX() - 1.5, pos.getY() - 1.5, pos.getZ() - 1.5,
pos.getX() + 1.5, pos.getY() + 1.5, pos.getZ() + 1.5);
for (Animal animal : turtle.getLevel().getEntitiesOfClass(Animal.class, box)) {
if (!animal.isBaby() && !animal.isInLove() && animal.isFood(curItem)) {
animal.setInLove(null);
curItem.shrink(1);
if (curItem.getCount() <= 0)
turtle.getInventory().setItem(turtle.getSelectedSlot(), ItemStack.EMPTY);
return new Object[]{true};
}
}
return new Object[]{false};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}
}
