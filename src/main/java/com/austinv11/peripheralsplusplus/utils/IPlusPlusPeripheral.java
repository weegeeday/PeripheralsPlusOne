package com.austinv11.peripheralsplusplus.utils;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Marker interface for PeripheralsPlusOne block entities.
 * BlockEntities that provide a peripheral should implement {@link HasPeripheral}.
 */
public interface IPlusPlusPeripheral {

    /**
     * Implemented by BlockEntities that expose an {@link IPeripheral} to CC:Tweaked.
     * This avoids the return-type conflict between {@code BlockEntity.getType()} and
     * {@code IPeripheral.getType()}.
     */
    interface HasPeripheral {
        IPeripheral getModPeripheral();
    }

    /**
     * Common provider for all PeripheralsPlusOne BlockEntities.
     */
    class Provider implements IPeripheralProvider {
        @Nonnull
        @Override
        public Optional<IPeripheral> getPeripheral(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Direction side) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof HasPeripheral hp) return Optional.of(hp.getModPeripheral());
            if (tile instanceof IPeripheral ip) return Optional.of(ip);
            return Optional.empty();
        }
    }
}
