package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityEnvironmentScanner extends BlockEntity implements IPlusPlusPeripheral.HasPeripheral {

private boolean isRaining = false;
private String biome = "unknown";
private String temp = "MEDIUM";
private boolean isSnow = false;
private ITurtleAccess turtle;

public TileEntityEnvironmentScanner(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ENVIRONMENT_SCANNER.get(), pos, state);
}

public TileEntityEnvironmentScanner(ITurtleAccess turtle) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ENVIRONMENT_SCANNER.get(), turtle.getPosition(), turtle.getLevel().getBlockState(turtle.getPosition()));
this.turtle = turtle;
}

public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, TileEntityEnvironmentScanner self) {
if (level != null) {
self.isRaining = level.isRaining();
Biome b = level.getBiome(pos).value();
self.biome = level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.BIOME)
.getKey(b) != null ? level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.BIOME).getKey(b).toString() : "unknown";
self.temp = b.getBaseTemperature() < 0.15f ? "COLD" : (b.getBaseTemperature() < 1.0f ? "MEDIUM" : "WARM");
self.isSnow = b.coldEnoughToSnow(pos);
}
}
public final Object[] isRaining(IArguments args) throws LuaException {
if (!Config.enableEnvironmentScanner)
throw new LuaException("Environment Scanners have been disabled");
return new Object[]{isRaining};
}

public final Object[] getBiome(IArguments args) throws LuaException {
if (!Config.enableEnvironmentScanner)
throw new LuaException("Environment Scanners have been disabled");
return new Object[]{biome};
}

public final Object[] getTemperature(IArguments args) throws LuaException {
if (!Config.enableEnvironmentScanner)
throw new LuaException("Environment Scanners have been disabled");
return new Object[]{temp};
}

public final Object[] getTemp(IArguments args) throws LuaException {
return getTemperature(args);
}

public final Object[] isSnow(IArguments args) throws LuaException {
if (!Config.enableEnvironmentScanner)
throw new LuaException("Environment Scanners have been disabled");
return new Object[]{isSnow};
}
    private final IPeripheral peripheral = new IPeripheral() {
        @Override
        public String getType() { return "environmentScanner"; }

        @Override
        public boolean equals(IPeripheral other) { return other == TileEntityEnvironmentScanner.this; }

        @LuaFunction
        public final Object[] isRaining(IArguments args) throws LuaException {
            return TileEntityEnvironmentScanner.this.isRaining(args);
        }

        @LuaFunction
        public final Object[] getBiome(IArguments args) throws LuaException {
            return TileEntityEnvironmentScanner.this.getBiome(args);
        }

        @LuaFunction
        public final Object[] getTemperature(IArguments args) throws LuaException {
            return TileEntityEnvironmentScanner.this.getTemperature(args);
        }

        @LuaFunction
        public final Object[] getTemp(IArguments args) throws LuaException {
            return TileEntityEnvironmentScanner.this.getTemp(args);
        }

        @LuaFunction
        public final Object[] isSnow(IArguments args) throws LuaException {
            return TileEntityEnvironmentScanner.this.isSnow(args);
        }

    };

    @Override
    public IPeripheral getModPeripheral() { return peripheral; }

}
