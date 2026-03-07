package com.austinv11.peripheralsplusplus.event.handler;

import com.austinv11.peripheralsplusplus.capabilities.nano.CapabilityNanoBot;
import com.austinv11.peripheralsplusplus.capabilities.rfid.CapabilityRfid;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAntenna;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilitiesHandler {

@SubscribeEvent
public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
if (Config.enableNanoBots)
event.addCapability(CapabilityNanoBot.ID, new CapabilityNanoBot.Provider(event.getObject()));
if (Config.enableRfidItems)
event.addCapability(CapabilityRfid.ID, new CapabilityRfid.Provider());
}

@SubscribeEvent
public void onEntityJoinWorld(EntityJoinLevelEvent event) {
event.getEntity().getCapability(CapabilityNanoBot.CAPABILITY).ifPresent(holder -> {
if (holder.getAntenna() != null && TileEntityAntenna.ANTENNA_REGISTRY.containsKey(holder.getAntenna()))
TileEntityAntenna.ANTENNA_REGISTRY.get(holder.getAntenna()).registerEntity(event.getEntity());
});
}
}
