package com.austinv11.peripheralsplusplus.pocket.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PeripheralMotionDetector implements IPlusPlusPeripheral {

private double oldX, oldY, oldZ;
private float oldPitch, oldYaw;
private IComputerAccess computer;
private boolean initialized = false;

public PeripheralMotionDetector(Entity entity) {
if (entity != null) {
oldX = entity.getX();
oldY = entity.getY();
oldZ = entity.getZ();
oldPitch = entity.getXRot();
oldYaw = entity.getYRot();
initialized = true;
}
MinecraftForge.EVENT_BUS.register(this);
}

@Override
public String getType() {
return "motionDetector";
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}

public void update(Entity entity) {
if (!Config.enableMotionDetector || computer == null || entity == null) return;
if (!initialized) {
oldX = entity.getX(); oldY = entity.getY(); oldZ = entity.getZ();
oldPitch = entity.getXRot(); oldYaw = entity.getYRot();
initialized = true;
return;
}
double dx = entity.getX() - oldX;
double dy = entity.getY() - oldY;
double dz = entity.getZ() - oldZ;
if (dx != 0 || dy != 0 || dz != 0) {
computer.queueEvent("locationChanged", new Object[]{dx, dy, dz});
oldX = entity.getX(); oldY = entity.getY(); oldZ = entity.getZ();
}
if (entity.getXRot() != oldPitch || entity.getYRot() != oldYaw) {
computer.queueEvent("rotationChanged", new Object[]{entity.getYRot(), entity.getXRot()});
oldPitch = entity.getXRot(); oldYaw = entity.getYRot();
}
}

@Override
public void attach(IComputerAccess computer) {
this.computer = computer;
}

@Override
public void detach(IComputerAccess computer) {
this.computer = null;
}

@SubscribeEvent
public void onPlayerInteract(PlayerInteractEvent event) {
if (!Config.enableMotionDetector || computer == null) return;
if (event instanceof PlayerInteractEvent.LeftClickBlock)
computer.queueEvent("blockHit", new Object[0]);
else if (event instanceof PlayerInteractEvent.RightClickBlock || event instanceof PlayerInteractEvent.RightClickItem)
computer.queueEvent("rightClick", new Object[0]);
}
}
