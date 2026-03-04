package com.austinv11.peripheralsplusplus.entities;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.items.ItemNanoSwarm;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.UUID;

public class EntityNanoBotSwarm extends ThrowableProjectile {

public UUID antennaIdentifier;
public String label;

public EntityNanoBotSwarm(EntityType<? extends ThrowableProjectile> type, Level level) {
super(type, level);
}

public EntityNanoBotSwarm(Level level, LivingEntity thrower) {
super(com.austinv11.peripheralsplusplus.init.ModEntities.NANO_BOT_SWARM.get(), thrower, level);
}

@Override
protected void defineSynchedData() {}

@Override
protected void onHitEntity(EntityHitResult result) {
if (!level().isClientSide()) {
result.getEntity().hurt(level().damageSources().generic(), 0);
ItemNanoSwarm.addSwarmForEntity(this, result.getEntity());
}
}

@Override
protected void onHitBlock(BlockHitResult result) {
if (!level().isClientSide()) {
ItemStack stack = new ItemStack(ModItems.NANO_SWARM.get());
stack.getOrCreateTag().putString("identifier", antennaIdentifier != null ? antennaIdentifier.toString() : "");
if (label != null)
stack.getTag().putString("label", label);
Direction dir = result.getDirection();
ItemEntity entity = new ItemEntity(level(),
result.getBlockPos().getX() + dir.getStepX() + 0.5,
result.getBlockPos().getY() + dir.getStepY() + 0.5,
result.getBlockPos().getZ() + dir.getStepZ() + 0.5,
stack);
level().addFreshEntity(entity);
}
discard();
}

@Override
public void addAdditionalSaveData(CompoundTag tag) {
super.addAdditionalSaveData(tag);
if (antennaIdentifier != null)
tag.putUUID("antennaId", antennaIdentifier);
if (label != null)
tag.putString("label", label);
}

@Override
public void readAdditionalSaveData(CompoundTag tag) {
super.readAdditionalSaveData(tag);
if (tag.hasUUID("antennaId"))
antennaIdentifier = tag.getUUID("antennaId");
if (tag.contains("label"))
label = tag.getString("label");
}
}
