package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityMEBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockMEBridge extends BlockContainerPPP {

public BlockMEBridge() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityMEBridge(pos, state);
}
}
