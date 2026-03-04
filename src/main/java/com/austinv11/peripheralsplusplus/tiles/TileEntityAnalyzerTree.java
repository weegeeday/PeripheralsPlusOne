package com.austinv11.peripheralsplusplus.tiles;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityAnalyzerTree extends TileEntityAnalyzer {

public TileEntityAnalyzerTree(BlockPos pos, BlockState state) {
super(pos, state);
}

@Override
public String getType() {
return "treeAnalyzer";
}

@Override
protected Object[] doAnalyze() throws LuaException {
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
