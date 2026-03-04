package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityTurtle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockTurtle extends BlockPppDirectional {

public BlockTurtle() {
super();
this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
}

@Override
protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
builder.add(FACING);
}

@Nullable
@Override
public BlockState getStateForPlacement(BlockPlaceContext context) {
return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
}

@Override
public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
// Facing is set via getStateForPlacement
}

@Override
public boolean isCollisionShapeFullBlock(BlockState state, net.minecraft.world.level.BlockGetter getter, BlockPos pos) {
return false;
}

@Override
public RenderShape getRenderShape(BlockState state) {
return RenderShape.INVISIBLE;
}

@Override
public VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter getter, BlockPos pos, CollisionContext context) {
return Shapes.empty();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityTurtle(pos, state);
}
}
