package com.austinv11.peripheralsplusplus.utils;

import dan200.computercraft.api.turtle.TurtleSide;

import java.util.Locale;

public class ModelUtil {
// Model registration is handled via data-driven resources in 1.20.1.
// Client-side turtle upgrade model handling uses IClientTurtleUpgrade.

public static void registerTurtleUpgradeModels(String name) {
// No-op: models registered via JSON resources
}

public static String getTurtleUpgradeModelKey(String modelName, TurtleSide side) {
return modelName + "_" + side.name().toLowerCase(Locale.US);
}
}
