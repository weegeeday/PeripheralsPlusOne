package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.pocket.PocketMotionDetector;
import com.austinv11.peripheralsplusplus.pocket.PocketPeripheralContainer;
import com.austinv11.peripheralsplusplus.pocket.PocketRfid;
import com.austinv11.peripheralsplusplus.turtles.*;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import net.minecraftforge.common.MinecraftForge;

public class ModPeripherals {

    public static void registerWithComputerCraft() {
        PeripheralsPlusPlus.LOGGER.info("Registering peripheral provider...");
        ForgeComputerCraftAPI.registerPeripheralProvider(new IPlusPlusPeripheral.Provider());
        // Turtle and pocket upgrades are now registered via TurtleUpgradeSerialiser /
        // PocketUpgradeSerialiser (see ModUpgrades) and data JSON files.
        // Drop-collector event listener still needs manual wiring:
        MinecraftForge.EVENT_BUS.register(new TurtleDropCollector.Listener());
    }
}

