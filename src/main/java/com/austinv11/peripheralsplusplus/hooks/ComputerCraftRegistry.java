package com.austinv11.peripheralsplusplus.hooks;

import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.turtle.ITurtleUpgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Legacy registry helper - most functionality no longer needed in 1.20.1
 */
public class ComputerCraftRegistry {

public static Map<String, ITurtleUpgrade> getTurtleUpgrades() {
return new HashMap<>();
}

public static Map<String, IPocketUpgrade> getPocketUpgrades() {
return new HashMap<>();
}
}
