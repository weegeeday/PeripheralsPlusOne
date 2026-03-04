package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.tiles.TileEntityPeripheralContainer;
import com.austinv11.peripheralsplusplus.utils.peripheralcontainer.ContainedPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockPeripheralContainer extends BlockContainerPPP {

public BlockPeripheralContainer() {
super();
}

@Nullable
@Override
public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
return new TileEntityPeripheralContainer(pos, state);
}

@Override
public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
CompoundTag tag = stack.getTag();
if (tag == null) return;
String key = "peripheralsplusone:peripheral_container";
if (!tag.contains(key)) return;
ListTag peripherals = tag.getList(key, 10);
BlockEntity container = level.getBlockEntity(pos);
if (!(container instanceof TileEntityPeripheralContainer te)) return;
for (int i = 0; i < peripherals.size(); i++) {
CompoundTag peripheralTag = peripherals.getCompound(i);
te.addPeripheral(new ContainedPeripheral(peripheralTag));
}
}
}
