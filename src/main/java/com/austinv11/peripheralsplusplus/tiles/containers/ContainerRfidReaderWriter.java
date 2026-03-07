package com.austinv11.peripheralsplusplus.tiles.containers;

import com.austinv11.peripheralsplusplus.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;

public class ContainerRfidReaderWriter extends ContainerSingleSlot {
    public ContainerRfidReaderWriter(int syncId, Inventory playerInv, net.minecraft.world.Container inv) {
        super(ModMenus.RFID_READER_WRITER.get(), syncId, playerInv, inv);
    }
}
