package com.austinv11.peripheralsplusplus.tiles.containers;

import com.austinv11.peripheralsplusplus.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;

public class ContainerInteractiveSorter extends ContainerSingleSlot {
    public ContainerInteractiveSorter(int syncId, Inventory playerInv, net.minecraft.world.Container inv) {
        super(ModMenus.INTERACTIVE_SORTER.get(), syncId, playerInv, inv);
    }
}
