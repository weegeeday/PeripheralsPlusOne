package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockTeleporter extends BlockPppDirectional {
public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 1);

public BlockTeleporter() {
super();
this.registerDefaultState(this.stateDefinition.any()
.setValue(FACING, Direction.NORTH)
.setValue(TIER, 0));
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityTeleporter(pos, state);
}

@Override
protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
builder.add(FACING, TIER);
}

@Nullable
@Override
public BlockState getStateForPlacement(BlockPlaceContext context) {
int tier = context.getItemInHand().getDamageValue();
return this.defaultBlockState()
.setValue(FACING, context.getHorizontalDirection().getOpposite())
.setValue(TIER, Math.min(tier, 1));
}

@Override
public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
// Facing already set in getStateForPlacement
}

@Override
public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
 InteractionHand hand, BlockHitResult hit) {
TileEntityTeleporter tp = (TileEntityTeleporter) level.getBlockEntity(pos);
if (tp == null)
return InteractionResult.PASS;
if (player.getItemInHand(hand).isEmpty() || !player.getItemInHand(hand).is(Items.REPEATER))
return InteractionResult.PASS;
if (!level.isClientSide)
tp.blockActivated(player, hand);
return InteractionResult.sidedSuccess(level.isClientSide);
}
}
