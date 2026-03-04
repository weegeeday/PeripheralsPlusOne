package com.austinv11.peripheralsplusplus.lua;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

/**
 * Wrapper for a peripheral in a Lua context.
 * In CC:Tweaked 1.20.1, ILuaObject is gone; methods are provided via @LuaFunction.
 * This class is a placeholder for future implementation.
 */
public class LuaObjectPeripheralWrap {

private final IPeripheral peripheral;
private final IComputerAccess computer;

public LuaObjectPeripheralWrap(IPeripheral peripheral, IComputerAccess computer) {
this.peripheral = peripheral;
this.computer = computer;
}

public IPeripheral getPeripheral() {
return peripheral;
}
}
