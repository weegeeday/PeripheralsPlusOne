package com.austinv11.peripheralsplusplus.capabilities.nano;

import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityNanoBot implements ICapabilityProvider {

public static final Capability<NanoBotHolder> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "nano_bot_embedded_entity");

private final NanoBotHolderDefault instance;
private final LazyOptional<NanoBotHolder> optional;

public CapabilityNanoBot() {
this.instance = new NanoBotHolderDefault();
this.optional = LazyOptional.of(() -> instance);
}

@Nonnull
@Override
public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
return CAPABILITY.orEmpty(cap, optional);
}

public static class Provider implements ICapabilityProvider, net.minecraftforge.common.util.INBTSerializable<CompoundTag> {
private final NanoBotHolderDefault instance;
private final LazyOptional<NanoBotHolder> optional;

public Provider(Entity entity) {
this.instance = new NanoBotHolderDefault();
this.instance.setEntity(entity);
this.optional = LazyOptional.of(() -> instance);
}

@Nonnull
@Override
public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
return CAPABILITY.orEmpty(cap, optional);
}

@Override
public CompoundTag serializeNBT() {
CompoundTag tag = new CompoundTag();
tag.putInt("bots", instance.getBots());
if (instance.getAntenna() != null)
tag.putUUID("antenna", instance.getAntenna());
return tag;
}

@Override
public void deserializeNBT(CompoundTag nbt) {
instance.setBots(nbt.getInt("bots"));
if (nbt.hasUUID("antenna"))
instance.setAntenna(nbt.getUUID("antenna"));
}
}
}
