package com.austinv11.peripheralsplusplus.client.gui;

import com.austinv11.peripheralsplusplus.init.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;

/**
 * Registers all GUI screens with their corresponding menu types.
 */
public class GuiFactory {

    public static void registerScreens() {
        MenuScreens.register(ModMenus.INTERACTIVE_SORTER.get(), GuiInteractiveSorter::new);
        MenuScreens.register(ModMenus.RFID_READER_WRITER.get(), GuiRfidReaderWriter::new);
        MenuScreens.register(ModMenus.RESUPPLY_STATION.get(), GuiResupplyStation::new);
        MenuScreens.register(ModMenus.PLAYER_INTERFACE.get(), GuiPlayerInterface::new);
    }
}

