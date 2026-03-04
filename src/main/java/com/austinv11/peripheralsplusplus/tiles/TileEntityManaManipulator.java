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
 * Mana Manipulator - Botania integration. Stubbed for 1.20.1 compatibility.
 */
public class TileEntityManaManipulator extends BlockEntity implements IPlusPlusPeripheral {

private final HashMap<IComputerAccess, Boolean> computers = new HashMap<>();

public TileEntityManaManipulator(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.MANA_MANIPULATOR.get(), pos, state);
}

@Override
public String getType() {
return "manaManipulator";
}

@LuaFunction
public final Object[] getMana(IArguments args) throws LuaException {
if (!Config.enableManaManipulator)
throw new LuaException("Mana Manipulators have been disabled");
throw new LuaException("Botania integration not yet implemented for 1.20.1");
}

@LuaFunction
public final Object[] setMana(IArguments args) throws LuaException {
if (!Config.enableManaManipulator)
throw new LuaException("Mana Manipulators have been disabled");
throw new LuaException("Botania integration not yet implemented for 1.20.1");
}

@Override
public void attach(IComputerAccess computer) { computers.put(computer, true); }
@Override
public void detach(IComputerAccess computer) { computers.remove(computer); }
@Override
public boolean equals(IPeripheral other) { return this == other; }
}
