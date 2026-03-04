package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityPlayerSensor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockPlayerSensor extends BlockContainerPPP {

public BlockPlayerSensor() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityPlayerSensor(pos, state);
}
}
