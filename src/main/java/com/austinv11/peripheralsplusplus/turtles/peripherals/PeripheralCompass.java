package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;

public class PeripheralCompass implements IPlusPlusPeripheral {

private final ITurtleAccess turtle;

public PeripheralCompass(ITurtleAccess turtle) {
this.turtle = turtle;
}

@Override
public String getType() {
return "compass";
}

@LuaFunction
public final Object[] getFacing(IArguments args) throws LuaException {
if (!Config.enableNavigationTurtle)
throw new LuaException("The compass upgrade has been disabled");
return new Object[]{turtle.getDirection().getName(), turtle.getDirection().ordinal()};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}
}
