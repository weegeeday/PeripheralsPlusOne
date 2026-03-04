package com.austinv11.peripheralsplusplus.lua;

import net.minecraft.world.entity.Entity;

import java.util.UUID;

/**
 * Lua interface for entity control via nano bots.
 * Exposes the antenna identifier and the target entity.
 *
 * Thread-safety: both fields are written once at construction and declared final.
 * Callers accessing mutable state on {@code entity} should synchronise on the
 * server thread as appropriate for the Minecraft concurrency model.
 */
public class LuaObjectEntityControl {

    public final UUID antennaIdentifier;
    public final Entity entity;

    public LuaObjectEntityControl() {
        this.antennaIdentifier = null;
        this.entity = null;
    }

    public LuaObjectEntityControl(UUID antennaIdentifier, Entity entity) {
        this.antennaIdentifier = antennaIdentifier;
        this.entity = entity;
    }
}
