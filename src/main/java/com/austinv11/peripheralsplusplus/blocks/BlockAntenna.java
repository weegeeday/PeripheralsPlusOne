package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.items.ItemNanoSwarm;
import com.austinv11.peripheralsplusplus.items.ItemSmartHelmet;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAntenna;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import com.austinv11.peripheralsplusplus.init.ModTileEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlockAntenna extends BlockPppDirectional implements EntityBlock {

public BlockAntenna() {
super();
this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
}

@Override
protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
builder.add(FACING);
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityAntenna(pos, state);
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

@Override
public BlockState getStateForPlacement(BlockPlaceContext context) {
return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
}

@Override
public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
if (placer != null) {
level.setBlock(pos, state.setValue(FACING, placer.getDirection().getOpposite()), 3);
}
}

@Override
public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
 InteractionHand hand, BlockHitResult hit) {
ItemStack held = player.getMainHandItem();
if (held.isEmpty() || !((held.getItem() instanceof ItemSmartHelmet) || (held.getItem() instanceof ItemNanoSwarm)))
return InteractionResult.PASS;
if (!level.isClientSide) {
TileEntityAntenna antenna = (TileEntityAntenna) level.getBlockEntity(pos);
if (antenna == null)
return InteractionResult.FAIL;
UUID id = antenna.identifier;
CompoundTag tag = held.getOrCreateTag();
tag.putString("identifier", id.toString());
if (antenna.getLabelDirect() != null) {
tag.putString("label", antenna.getLabelDirect());
}
}
return InteractionResult.sidedSuccess(level.isClientSide);
}

    @Override
    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(net.minecraft.world.level.Level level, BlockState state, BlockEntityType<T> type) {
        if (!level.isClientSide && type == ModTileEntities.ANTENNA.get()) {
            @SuppressWarnings("unchecked")
            BlockEntityTicker<T> ticker = (BlockEntityTicker<T>) (BlockEntityTicker<TileEntityAntenna>) TileEntityAntenna::serverTick;
            return ticker;
        }
        return null;
    }

}