package com.austinv11.peripheralsplusplus.lua;

import java.util.UUID;

/**
 * Lua interface for the smart helmet HUD.
 * Provides access to HUD dimensions returned via the scale-request network round-trip.
 *
 * Thread-safety: {@code playerName} and {@code identifier} are written once at construction
 * and are effectively immutable (declared final). {@code width} and {@code height} may be
 * written from a network thread (via {@code TileEntityAntenna.onResponse}) and read from a
 * Lua thread, so they are declared {@code volatile}.
 */
public class LuaObjectHUD {

    public final String playerName;
    public final UUID identifier;
    public volatile int width;
    public volatile int height;

    public LuaObjectHUD() {
        this.playerName = null;
        this.identifier = null;
    }

    public LuaObjectHUD(String playerName, UUID identifier) {
        this.playerName = playerName;
        this.identifier = identifier;
    }
}
