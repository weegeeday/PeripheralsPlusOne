package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

/**
 * ME Bridge peripheral - Applied Energistics 2 integration.
 * AE2 for 1.20.1 uses a different API; this is a stub for compilation.
 */
public class TileEntityMEBridge extends BlockEntity implements IPlusPlusPeripheral {

private final HashMap<IComputerAccess, Boolean> computers = new HashMap<>();

public TileEntityMEBridge(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ME_BRIDGE.get(), pos, state);
}

@Override
public String getType() {
return "meBridge";
}

@LuaFunction
public final Object[] getItems(IArguments args) throws LuaException {
if (!Config.enableMEBridge)
throw new LuaException("ME Bridge has been disabled");
// TODO: Implement AE2 1.20.1 API integration
throw new LuaException("ME Bridge AE2 integration not yet implemented for 1.20.1");
}

@LuaFunction
public final Object[] exportItem(IArguments args) throws LuaException {
if (!Config.enableMEBridge)
throw new LuaException("ME Bridge has been disabled");
throw new LuaException("ME Bridge AE2 integration not yet implemented for 1.20.1");
}

@LuaFunction
public final Object[] importItem(IArguments args) throws LuaException {
if (!Config.enableMEBridge)
throw new LuaException("ME Bridge has been disabled");
throw new LuaException("ME Bridge AE2 integration not yet implemented for 1.20.1");
}

@LuaFunction
public final Object[] craftItem(IArguments args) throws LuaException {
if (!Config.enableMEBridge)
throw new LuaException("ME Bridge has been disabled");
throw new LuaException("ME Bridge AE2 integration not yet implemented for 1.20.1");
}

@Override
public void attach(IComputerAccess computer) {
computers.put(computer, true);
}

@Override
public void detach(IComputerAccess computer) {
computers.remove(computer);
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}
}
