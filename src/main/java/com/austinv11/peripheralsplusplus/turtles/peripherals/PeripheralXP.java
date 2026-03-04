package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class PeripheralXP implements IPlusPlusPeripheral {

private static final int MAX_LEVEL = 30;
private static final double COLLECT_RANGE = 2.0D;
private final BetterRandom random = new BetterRandom();
private int experience = 0;
private int experienceRemainder = 0;
private int experienceLevel = 0;
private boolean autoCollect = false;
private int ticker;
public boolean changed = false;
private final ITurtleAccess turtle;
private final TurtleSide side;

public PeripheralXP(ITurtleAccess turtle, TurtleSide side) {
this.turtle = turtle;
this.side = side;
CompoundTag tag = turtle.getUpgradeNBTData(side);
experience = tag.getInt("experience");
experienceRemainder = tag.getInt("experienceRemainder");
experienceLevel = tag.getInt("experienceLevel");
random.setSeed(tag.getLong("rndSeed"));
ticker = random.nextInt(20);
}

public void update() {
if (autoCollect && ++ticker >= 20) {
ticker = 0;
addExperience(collect());
changed = true;
}
if (changed) {
CompoundTag tag = turtle.getUpgradeNBTData(side);
tag.putInt("experience", experience);
tag.putInt("experienceRemainder", experienceRemainder);
tag.putInt("experienceLevel", experienceLevel);
tag.putLong("rndSeed", random.getSeed());
turtle.updateUpgradeNBTData(side);
changed = false;
}
}

public void addExperience(int amount) {
int var = Integer.MAX_VALUE - this.experience;
if (amount > var) amount = var;
this.experienceRemainder += amount;
this.experience += amount;
while (experienceRemainder < 0 || experienceRemainder >= levelXP(experienceLevel)) {
int sign = experienceRemainder < 0 ? -1 : 1;
this.experienceRemainder -= levelXP(experienceLevel) * sign;
this.addLevels(sign, false);
}
}

public void addLevels(int par1, boolean updateXP) {
this.experienceLevel += par1;
if (this.experienceLevel < 0) this.experienceLevel = 0;
if (updateXP) experience = calculateLevelXP(experienceLevel) + experienceRemainder;
}

public int levelXP(int level) {
return level >= 30 ? 62 + (level - 30) * 7 : (level >= 15 ? 17 + (level - 15) * 3 : 17);
}

public int calculateLevelXP(int level) {
int levelXP = 0;
for (int i = 1; i <= level; i++) levelXP += levelXP(i);
return levelXP;
}

private int collect() {
BlockPos pos = turtle.getPosition();
AABB box = new AABB(pos.getX() - COLLECT_RANGE, pos.getY() - COLLECT_RANGE, pos.getZ() - COLLECT_RANGE,
pos.getX() + 1 + COLLECT_RANGE, pos.getY() + 1 + COLLECT_RANGE, pos.getZ() + 1 + COLLECT_RANGE);
int ret = 0;
for (ExperienceOrb orb : turtle.getLevel().getEntitiesOfClass(ExperienceOrb.class, box)) {
ret += orb.getValue();
orb.discard();
}
return ret;
}

@Override
public String getType() {
return "xp";
}

@LuaFunction
public final Object[] add(IArguments args) throws LuaException {
if (!Config.enableXPTurtle)
throw new LuaException("XP Turtles have been disabled");
int amount = args.count() > 0 ? args.getInt(0) : Integer.MAX_VALUE;
ItemStack slot = turtle.getInventory().getItem(turtle.getSelectedSlot());
if (slot.isEmpty()) return new Object[]{0};
amount = Math.min(amount, slot.getCount());
int recharge = 0;
if (slot.is(Items.EXPERIENCE_BOTTLE)) {
recharge = (3 + random.nextInt(5) + random.nextInt(5)) * amount;
}
addExperience(recharge);
if (recharge > 0) {
slot.shrink(amount);
if (slot.getCount() <= 0) slot = ItemStack.EMPTY;
turtle.getInventory().setItem(turtle.getSelectedSlot(), slot);
}
changed = true;
return new Object[]{recharge};
}

@LuaFunction
public final Object[] getXP(IArguments args) throws LuaException {
if (!Config.enableXPTurtle)
throw new LuaException("XP Turtles have been disabled");
return new Object[]{experience};
}

@LuaFunction
public final Object[] getLevels(IArguments args) throws LuaException {
if (!Config.enableXPTurtle)
throw new LuaException("XP Turtles have been disabled");
return new Object[]{experienceLevel};
}

@LuaFunction
public final Object[] collect(IArguments args) throws LuaException {
if (!Config.enableXPTurtle)
throw new LuaException("XP Turtles have been disabled");
int collected = collect();
addExperience(collected);
changed = true;
return new Object[]{collected};
}

@LuaFunction
public final Object[] setAutoCollect(IArguments args) throws LuaException {
if (!Config.enableXPTurtle)
throw new LuaException("XP Turtles have been disabled");
autoCollect = args.count() > 0 ? args.getBoolean(0) : !autoCollect;
return new Object[]{autoCollect};
}

@LuaFunction
public final Object[] enchant(IArguments args) throws LuaException {
if (!Config.enableXPTurtle)
throw new LuaException("XP Turtles have been disabled");
int levels = args.getInt(0);
if (levels < 1 || levels > MAX_LEVEL)
throw new LuaException("invalid level count " + levels + " (expected 1-" + MAX_LEVEL + ")");
ItemStack slot = turtle.getInventory().getItem(turtle.getSelectedSlot());
if (!slot.isEnchantable()) return new Object[]{false};
if (experienceLevel < levels) return new Object[]{false};
List<EnchantmentInstance> enchants = EnchantmentHelper.selectEnchantment(random, slot, levels, true);
if (enchants.isEmpty()) return new Object[]{false};
ItemStack enchanted = slot.copy();
if (enchanted.is(Items.BOOK)) {
enchanted = new ItemStack(Items.ENCHANTED_BOOK);
}
for (EnchantmentInstance data : enchants) {
enchanted.enchant(data.enchantment, data.level);
}
addLevels(-levels, true);
turtle.getInventory().setItem(turtle.getSelectedSlot(), enchanted);
changed = true;
return new Object[]{true};
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}

private static class BetterRandom extends Random {
private long seed;

@Override
public void setSeed(long seed) {
super.setSeed(seed);
this.seed = seed;
}

public long getSeed() {
return seed;
}
}
}
