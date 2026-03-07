package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.ReflectionHelper;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityRFCharger extends BlockEntity implements IEnergyStorage {

private EnergyStorage storage = new EnergyStorage(80000);
private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> this);

public TileEntityRFCharger(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.RF_CHARGER.get(), pos, state);
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
if (tag.contains("capacity")) {
int capacity = tag.getInt("capacity");
int energy = tag.getInt("energy");
storage = new EnergyStorage(capacity, capacity, capacity, energy);
}
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
tag.putInt("capacity", storage.getMaxEnergyStored());
tag.putInt("energy", storage.getEnergyStored());
}

public static void serverTick(Level level, BlockPos pos, BlockState state, TileEntityRFCharger self) {
if (level.isClientSide) return;
List<ITurtleAccess> turtles = new ArrayList<>(6);
for (Direction direction : Direction.values()) {
BlockPos neighbor = pos.relative(direction);
if (level.isEmptyBlock(neighbor)) continue;
BlockEntity te = level.getBlockEntity(neighbor);
if (te != null) {
try {
ITurtleAccess turtle = ReflectionHelper.getTurtle(te);
if (turtle != null) turtles.add(turtle);
} catch (Exception ignored) {}
}
}
if (turtles.isEmpty()) return;
int rate = Math.max(1, 6 / turtles.size());
for (ITurtleAccess turtle : turtles) {
if (self.storage.getEnergyStored() >= rate)
self.storage.extractEnergy(self.addFuel(turtle, rate) * Config.fuelRF, false);
}
}

private int addFuel(ITurtleAccess turtle, int rate) {
if (turtle.getFuelLimit() > turtle.getFuelLevel()) {
turtle.setFuelLevel(rate + turtle.getFuelLevel());
return rate;
}
return 0;
}

@Override
public int receiveEnergy(int maxReceive, boolean simulate) {
return storage.receiveEnergy(maxReceive, simulate);
}

@Override
public int extractEnergy(int maxExtract, boolean simulate) {
return 0;
}

@Override
public int getEnergyStored() {
return storage.getEnergyStored();
}

@Override
public int getMaxEnergyStored() {
return storage.getMaxEnergyStored();
}

@Override
public boolean canExtract() {
return false;
}

@Override
public boolean canReceive() {
return true;
}

@Nonnull
@Override
public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
if (cap == ForgeCapabilities.ENERGY)
return energyCap.cast();
return super.getCapability(cap, side);
}

@Override
public void invalidateCaps() {
super.invalidateCaps();
energyCap.invalidate();
}
}
