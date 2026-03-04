package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityAnalyzerButterfly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockAnalyzerButterfly extends BlockAnalyzer {

public BlockAnalyzerButterfly() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityAnalyzerButterfly(pos, state);
}
}
