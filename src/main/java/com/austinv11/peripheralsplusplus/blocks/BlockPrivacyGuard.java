package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityPrivacyGuard;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockPrivacyGuard extends BlockPppBase {

public BlockPrivacyGuard() {
super();
}

@Override
public boolean hasTileEntity(BlockState state) {
return true;
}

@Nullable
@Override
public BlockEntity createTileEntity(BlockState state, net.minecraft.world.level.BlockGetter world) {
return new TileEntityPrivacyGuard(BlockPos.ZERO, state);
}
}
