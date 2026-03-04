package com.austinv11.peripheralsplusplus.utils.peripheralcontainer;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ContainedPeripheral {

private IPeripheral peripheral;
private ResourceLocation blockResourceLocation;

public ContainedPeripheral(ResourceLocation blockResourceLocation, IPeripheral peripheral) {
this.blockResourceLocation = blockResourceLocation;
this.peripheral = peripheral;
}

public ContainedPeripheral(CompoundTag tagCompound) {
blockResourceLocation = new ResourceLocation(tagCompound.getString("id"));
try {
Class<?> clazz = Class.forName(tagCompound.getString("class"));
peripheral = (IPeripheral) clazz.getDeclaredConstructor().newInstance();
} catch (Exception e) {
peripheral = null;
}
}

@Nullable
public static IPeripheral getPeripheralForBlock(net.minecraft.world.level.block.Block block) {
if (block instanceof IPeripheral) return (IPeripheral) block;
return null;
}

public ResourceLocation getBlockResourceLocation() {
return blockResourceLocation;
}

public CompoundTag toNbt() {
CompoundTag tag = new CompoundTag();
tag.putString("id", blockResourceLocation.toString());
if (peripheral != null)
tag.putString("class", peripheral.getClass().getName());
return tag;
}

public IPeripheral getPeripheral() {
return peripheral;
}
}
