package com.austinv11.peripheralsplusplus.utils;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TurtleUtil {

public static List<ItemStack> harvestBlock(ITurtleAccess turtle, FakeTurtlePlayer player, Direction dir,
   ItemStack itemToUse) {
int x = turtle.getPosition().getX() + dir.getStepX();
int y = turtle.getPosition().getY() + dir.getStepY();
int z = turtle.getPosition().getZ() + dir.getStepZ();
BlockPos target = new BlockPos(x, y, z);
if (!turtle.getLevel().isEmptyBlock(target)) {
BlockState blockState = turtle.getLevel().getBlockState(target);
player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, itemToUse);
if (blockState.getDestroySpeed(turtle.getLevel(), target) >= 0) {
List<ItemStack> items = net.minecraft.world.level.block.Block.getDrops(
blockState, (net.minecraft.server.level.ServerLevel) turtle.getLevel(), target, turtle.getLevel().getBlockEntity(target), player, itemToUse);
turtle.getLevel().removeBlock(target, false);
return items;
}
}
return null;
}

public static List<Entity> getEntitiesNearTurtle(ITurtleAccess turtle, FakeTurtlePlayer player, Direction dir) {
int x = turtle.getPosition().getX() + dir.getStepX();
int y = turtle.getPosition().getY() + dir.getStepY();
int z = turtle.getPosition().getZ() + dir.getStepZ();
AABB box = new AABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
return turtle.getLevel().getEntities(player, box);
}

public static Entity getClosestEntity(List<Entity> list, Entity ent) {
Vec3 from = ent.position();
Entity returnVal = null;
double lastDistance = Double.MAX_VALUE;
for (Entity entity : list) {
Vec3 to = entity.position();
if (to.distanceTo(from) < lastDistance)
returnVal = entity;
}
return returnVal;
}

public static void addItemListToInv(List<ItemStack> items, ITurtleAccess turtle) {
for (ItemStack item : items) {
addToInv(turtle, item);
}
}

public static ArrayList<ItemStack> entityItemsToItemStack(ArrayList<ItemEntity> entities) {
ArrayList<ItemStack> stacks = new ArrayList<>();
for (ItemEntity e : entities) {
stacks.add(e.getItem());
}
return stacks;
}

public static void addToInv(ITurtleAccess turtle, ItemStack stack) {
boolean drop = true;
net.minecraft.world.Container inv = turtle.getInventory();
BlockPos coords = turtle.getPosition();
for (int i = 0; i < inv.getContainerSize(); i++) {
ItemStack currentStack = inv.getItem(i);
if (currentStack.isEmpty()) {
inv.setItem(i, stack);
drop = false;
break;
}
if (currentStack.isStackable() && ItemStack.isSameItemSameTags(currentStack, stack)) {
int space = currentStack.getMaxStackSize() - currentStack.getCount();
if (stack.getCount() > space) {
currentStack.setCount(currentStack.getMaxStackSize());
stack.setCount(stack.getCount() - space);
drop = true;
} else {
currentStack.setCount(currentStack.getCount() + stack.getCount());
stack.setCount(0);
drop = false;
break;
}
}
}
if (drop) {
Direction dir = turtle.getDirection();
turtle.getLevel().addFreshEntity(new ItemEntity(turtle.getLevel(),
coords.getX() + dir.getStepX(),
coords.getY() + dir.getStepY() + 1,
coords.getZ() + dir.getStepZ(), stack.copy()));
}
}

public static ItemStack getTurtle(boolean isAdvanced) {
// In 1.20.1 use proper ItemStack construction for CC:Tweaked turtles
net.minecraft.resources.ResourceLocation rl = new net.minecraft.resources.ResourceLocation(
"computercraft", isAdvanced ? "turtle_advanced" : "turtle_normal");
net.minecraft.world.item.Item item = net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(rl);
if (item == null) return ItemStack.EMPTY;
return new ItemStack(item);
}

public static <T> T getPeripheral(ITurtleAccess turtle, Class<T> clazz) {
for (TurtleSide side : EnumSet.allOf(TurtleSide.class)) {
IPeripheral peripheral = turtle.getPeripheral(side);
if (peripheral != null && clazz.isAssignableFrom(peripheral.getClass()))
return (T) peripheral;
}
return null;
}

public static TurtleSide getPeripheralSide(ITurtleAccess turtle, Class clazz) {
for (TurtleSide side : EnumSet.allOf(TurtleSide.class)) {
IPeripheral peripheral = turtle.getPeripheral(side);
if (peripheral != null && clazz.isAssignableFrom(peripheral.getClass()))
return side;
}
return null;
}

public static ItemStack getPocketServerItemStack(IPocketAccess access) {
try {
Class<?> pocketServer = Class.forName("dan200.computercraft.shared.pocket.core.PocketServerComputer");
if (!pocketServer.isInstance(access))
return ItemStack.EMPTY;
Field stack = pocketServer.getDeclaredField("stack");
stack.setAccessible(true);
ItemStack itemStack = (ItemStack) stack.get(access);
if (itemStack == null)
return ItemStack.EMPTY;
return itemStack;
} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
return ItemStack.EMPTY;
}
}
}
