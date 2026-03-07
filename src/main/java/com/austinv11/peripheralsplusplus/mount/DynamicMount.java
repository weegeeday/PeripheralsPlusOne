package com.austinv11.peripheralsplusplus.mount;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

import java.util.ArrayList;
import java.util.List;

public class DynamicMount {

    public static List<String> attach(IComputerAccess computer, IPeripheral peripheral) {
        return new ArrayList<>();
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
