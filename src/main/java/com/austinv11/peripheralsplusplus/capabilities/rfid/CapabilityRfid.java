package com.austinv11.peripheralsplusplus.capabilities.rfid;

import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityRfid implements ICapabilityProvider {

public static final Capability<RfidTagHolder> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "rfid_tagged_entity");

private final RfidTagHolderDefault instance = new RfidTagHolderDefault();
private final LazyOptional<RfidTagHolder> optional = LazyOptional.of(() -> instance);

@Nonnull
@Override
public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
return CAPABILITY.orEmpty(cap, optional);
}

public static class Provider implements ICapabilityProvider, net.minecraftforge.common.util.INBTSerializable<CompoundTag> {
private final RfidTagHolderDefault instance = new RfidTagHolderDefault();
private final LazyOptional<RfidTagHolder> optional = LazyOptional.of(() -> instance);

@Nonnull
@Override
public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
return CAPABILITY.orEmpty(cap, optional);
}

@Override
public CompoundTag serializeNBT() {
CompoundTag tag = new CompoundTag();
tag.putBoolean("prodded", instance.hasBeenProdded());
if (!instance.getTag().isEmpty())
tag.put("tag", instance.getTag().save(new CompoundTag()));
return tag;
}

@Override
public void deserializeNBT(CompoundTag nbt) {
instance.setProdded(nbt.getBoolean("prodded"));
if (nbt.contains("tag"))
instance.setTag(ItemStack.of(nbt.getCompound("tag")));
}
}
}
