package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class PeripheralSignReader implements IPlusPlusPeripheral {

private final ITurtleAccess turtle;

public PeripheralSignReader(ITurtleAccess turtle) {
this.turtle = turtle;
}

@Override
public String getType() {
return "signReader";
}

@LuaFunction
public final Object[] read(IArguments args) throws LuaException {
if (!Config.enableReaderTurtle)
throw new LuaException("Sign Reading Turtles have been disabled");
return getSignText(turtle.getPosition().relative(turtle.getDirection()));
}

@LuaFunction
public final Object[] readUp(IArguments args) throws LuaException {
if (!Config.enableReaderTurtle)
throw new LuaException("Sign Reading Turtles have been disabled");
return getSignText(turtle.getPosition().above());
}

@LuaFunction
public final Object[] readDown(IArguments args) throws LuaException {
if (!Config.enableReaderTurtle)
throw new LuaException("Sign Reading Turtles have been disabled");
return getSignText(turtle.getPosition().below());
}

private Object[] getSignText(BlockPos pos) throws LuaException {
BlockEntity te = turtle.getLevel().getBlockEntity(pos);
if (!(te instanceof SignBlockEntity sign))
throw new LuaException("No sign found.");
ArrayList<String> lines = new ArrayList<>();
for (int i = 0; i < 4; i++)
lines.add(sign.getMessage(i, false).getString());
return new Object[]{Util.arrayToMap(lines.toArray())};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}
}
