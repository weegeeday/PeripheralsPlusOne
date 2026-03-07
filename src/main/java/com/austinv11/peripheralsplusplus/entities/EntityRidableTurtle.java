package com.austinv11.peripheralsplusplus.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.UUID;

/**
 * Entity stub for ridable turtle - full implementation pending
 */
public class EntityRidableTurtle extends Mob {

public UUID turtleId;

public EntityRidableTurtle(EntityType<? extends EntityRidableTurtle> type, Level level) {
super(type, level);
}

public static AttributeSupplier.Builder createAttributes() {
return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0)
        .add(Attributes.MOVEMENT_SPEED, 0.25);
}

@Override
protected void registerGoals() {}

@Override
public void addAdditionalSaveData(CompoundTag tag) {
super.addAdditionalSaveData(tag);
if (turtleId != null)
tag.putUUID("turtleId", turtleId);
}

@Override
public void readAdditionalSaveData(CompoundTag tag) {
super.readAdditionalSaveData(tag);
if (tag.hasUUID("turtleId"))
turtleId = tag.getUUID("turtleId");
}
}
