package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityModNotLoaded extends BlockEntity implements IPlusPlusPeripheral.HasPeripheral {

private String modId;

public TileEntityModNotLoaded(BlockPos pos, BlockState state) {
super(null, pos, state);
}

public TileEntityModNotLoaded(String modId, BlockPos pos, BlockState state) {
super(null, pos, state);
this.modId = modId;
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
modId = tag.getString("modId");
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
if (modId != null)
tag.putString("modId", modId);
}
public final Object[] reason(IArguments args) {
return new Object[]{String.format("Mod with mod id \"%s\" is not installed.", modId)};
}
    private final IPeripheral peripheral = new IPeripheral() {
        @Override
        public String getType() { return String.format("modNotLoaded_%s", modId); }

        @Override
        public boolean equals(IPeripheral other) { return TileEntityModNotLoaded.this == other; }

        @LuaFunction
        public final Object[] reason(IArguments args) {
            return TileEntityModNotLoaded.this.reason(args);
        }

    };

    @Override
    public IPeripheral getModPeripheral() { return peripheral; }

}
