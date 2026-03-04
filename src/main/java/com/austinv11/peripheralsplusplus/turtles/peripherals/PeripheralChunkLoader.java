package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.common.world.ForgeChunkManager;

public class PeripheralChunkLoader implements IPlusPlusPeripheral {

private final ITurtleAccess turtle;
private boolean attached = false;
private ChunkPos pos;

public PeripheralChunkLoader(ITurtleAccess turtle) {
this.turtle = turtle;
this.pos = new ChunkPos(turtle.getPosition());
}

@Override
public void attach(IComputerAccess computer) {
attached = true;
}

public void update() {
if (attached && !turtle.getLevel().isClientSide()) {
if (posChanged()) {
this.pos = new ChunkPos(turtle.getPosition());
updateChunkForcing();
}
}
}

@Override
public void detach(IComputerAccess computer) {
releaseChunks();
attached = false;
}

private void updateChunkForcing() {
releaseChunks();
for (int x = pos.x - Config.chunkLoadingRadius; x <= pos.x + Config.chunkLoadingRadius; x++) {
for (int z = pos.z - Config.chunkLoadingRadius; z <= pos.z + Config.chunkLoadingRadius; z++) {
ForgeChunkManager.forceChunk((net.minecraft.server.level.ServerLevel) turtle.getLevel(),
"peripheralsplusplus", BlockPos.ZERO, x, z, true, false);
}
}
}

private void releaseChunks() {
if (!turtle.getLevel().isClientSide()) {
for (int x = pos.x - Config.chunkLoadingRadius; x <= pos.x + Config.chunkLoadingRadius; x++) {
for (int z = pos.z - Config.chunkLoadingRadius; z <= pos.z + Config.chunkLoadingRadius; z++) {
ForgeChunkManager.forceChunk((net.minecraft.server.level.ServerLevel) turtle.getLevel(),
"peripheralsplusplus", BlockPos.ZERO, x, z, false, false);
}
}
}
}

public boolean posChanged() {
return !new ChunkPos(turtle.getPosition()).equals(pos);
}

@Override
public String getType() {
return "chunkLoader";
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}
}
