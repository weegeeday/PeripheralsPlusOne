package com.austinv11.peripheralsplusplus.data.world;

import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;

public class WorldDataRfidUniqueId extends SavedData {

private static final String TAG_KEY = Reference.MOD_ID + "_rfid_unique_id";
private long lastId;

public WorldDataRfidUniqueId() {}

@Override
public CompoundTag save(CompoundTag compound) {
compound.putLong("last_id", lastId);
return compound;
}

public static WorldDataRfidUniqueId load(CompoundTag tag) {
WorldDataRfidUniqueId data = new WorldDataRfidUniqueId();
data.lastId = tag.getLong("last_id");
return data;
}

@Nullable
public static WorldDataRfidUniqueId get() {
MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
if (server == null) return null;
return server.overworld().getDataStorage().computeIfAbsent(
WorldDataRfidUniqueId::load,
WorldDataRfidUniqueId::new,
TAG_KEY);
}

public long getLastId() {
return lastId;
}

public void setLastId(long lastId) {
this.lastId = lastId;
setDirty();
}
}
