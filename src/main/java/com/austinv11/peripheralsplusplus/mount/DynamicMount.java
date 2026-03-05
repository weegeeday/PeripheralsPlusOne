package com.austinv11.peripheralsplusplus.mount;

import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.reference.Reference;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic mount implementation for attaching Lua scripts to peripherals.
 * Simplified for 1.20.1 migration - full implementation pending.
 */
public class DynamicMount {

public static List<String> attach(IComputerAccess computer, IPeripheral peripheral) {
List<String> attached = new ArrayList<>();
try {
IMount dynScript = ComputerCraftAPI.createResourceMount(Reference.MOD_ID, "lua/mount");
if (dynScript != null) {
String path = computer.mount("/ppo_lib", dynScript);
if (path != null) attached.add(path);
}
} catch (RuntimeException e) {
PeripheralsPlusPlus.LOGGER.error(e);
}
return attached;
}

public static void detach(IComputerAccess computer, List<String> paths) {
for (String path : paths) {
try {
computer.unmount(path);
} catch (RuntimeException ignore) {}
}
paths.clear();
}
}
