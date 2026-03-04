package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public class TileEntityAnalyzerBee extends TileEntityAnalyzer {

public TileEntityAnalyzerBee(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.ANALYZER_BEE.get(), pos, state);
}

@Override
public String getType() {
return "beeAnalyzer";
}

@Override
protected Object[] doAnalyze() throws LuaException {
// Forestry for 1.20.1 not available - return stub
return new Object[]{null};
}

@Override
protected boolean isMemberOf(ItemStack stack) {
return false;
}

@Override
protected IPeripheral getInstance() {
return this;
}
}
