package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.items.ItemPlasticCard;
import com.austinv11.peripheralsplusplus.tiles.TileEntityMagReaderWriter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockMagReaderWriter extends BlockContainerPPP {

public BlockMagReaderWriter() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityMagReaderWriter(pos, state);
}

@Override
public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
 InteractionHand hand, BlockHitResult hit) {
ItemStack heldItem = player.getItemInHand(hand);
if (!level.isClientSide && heldItem.getItem() instanceof ItemPlasticCard) {
BlockEntity te = level.getBlockEntity(pos);
if (te instanceof TileEntityMagReaderWriter magRW) {
magRW.itemSwiped(heldItem);
return InteractionResult.CONSUME;
}
}
return InteractionResult.sidedSuccess(level.isClientSide);
}
}
