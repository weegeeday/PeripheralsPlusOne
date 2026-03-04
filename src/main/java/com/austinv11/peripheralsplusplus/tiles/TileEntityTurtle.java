package com.austinv11.peripheralsplusplus.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityTurtle extends BlockEntity {

public TileEntityTurtle(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.TURTLE.get(), pos, state);
}
}
