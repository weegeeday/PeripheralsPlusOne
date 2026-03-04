package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityOreDictionary;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockOreDictionary extends BlockContainerPPP {

public BlockOreDictionary() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityOreDictionary(pos, state);
}
}
