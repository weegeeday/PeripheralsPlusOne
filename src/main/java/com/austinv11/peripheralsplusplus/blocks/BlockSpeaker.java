package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntitySpeaker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import com.austinv11.peripheralsplusplus.init.ModTileEntities;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockSpeaker extends BlockContainerPPP {

public BlockSpeaker() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntitySpeaker(pos, state);
}

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(net.minecraft.world.level.Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModTileEntities.SPEAKER.get(), TileEntitySpeaker::serverTick);
    }

}