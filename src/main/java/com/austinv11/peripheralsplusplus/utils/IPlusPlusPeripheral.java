package com.austinv11.peripheralsplusplus.utils;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implement this on any BlockEntity in PeripheralsPlusOne instead of {@code IPeripheral} to have a way of detecting if a peripheral is from this mod.
 */
public interface IPlusPlusPeripheral extends IPeripheral {
@Override
default void attach(@Nonnull IComputerAccess computer) {
}

@Override
default void detach(@Nonnull IComputerAccess computer) {
}

/**
 * This is the common provider for all PeripheralsPlusOne BlockEntities
 */
class Provider implements IPeripheralProvider {
@Nullable
@Override
public IPeripheral getPeripheral(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Direction side) {
BlockEntity tile = level.getBlockEntity(pos);
return tile instanceof IPlusPlusPeripheral ? (IPlusPlusPeripheral) tile : null;
}
}
}
