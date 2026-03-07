package com.austinv11.peripheralsplusplus.event.handler;

import com.austinv11.peripheralsplusplus.items.ItemBlockPeripheralContainer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PeripheralContainerHandler {

@SubscribeEvent
public void onInteract(PlayerInteractEvent event) {
if (event instanceof PlayerInteractEvent.RightClickEmpty) {
if (!event.getEntity().getMainHandItem().isEmpty() &&
event.getEntity().getMainHandItem().getItem() instanceof ItemBlockPeripheralContainer) {
// Show contained peripherals tooltip
// Full implementation in future phase
}
}
}
}
