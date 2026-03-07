package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityRfidReaderWriter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockRfidReaderWriter extends BlockContainerPPP {

public BlockRfidReaderWriter() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityRfidReaderWriter(pos, state);
}

@Override
public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
 InteractionHand hand, BlockHitResult hit) {
if (!level.isClientSide) {
BlockEntity te = level.getBlockEntity(pos);
if (te instanceof net.minecraft.world.MenuProvider menuProvider)
NetworkHooks.openScreen((ServerPlayer) player, menuProvider, pos);
}
return InteractionResult.sidedSuccess(level.isClientSide);
}
}
