package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PeripheralGarden implements IPeripheral {

private final ITurtleAccess turtle;

public PeripheralGarden(ITurtleAccess turtle) {
this.turtle = turtle;
}

@Override
public String getType() {
return "gardener";
}

@LuaFunction
public final Object[] getGrowth(IArguments args) throws LuaException {
if (!Config.enableGardeningTurtle)
throw new LuaException("Gardening Turtles have been disabled!");
return getGrowthAt(turtle.getPosition().relative(turtle.getDirection()));
}

@LuaFunction
public final Object[] getGrowthUp(IArguments args) throws LuaException {
if (!Config.enableGardeningTurtle)
throw new LuaException("Gardening Turtles have been disabled!");
return getGrowthAt(turtle.getPosition().above());
}

@LuaFunction
public final Object[] getGrowthDown(IArguments args) throws LuaException {
if (!Config.enableGardeningTurtle)
throw new LuaException("Gardening Turtles have been disabled!");
return getGrowthAt(turtle.getPosition().below());
}

@LuaFunction
public final Object[] fertilize(IArguments args) throws LuaException {
if (!Config.enableGardeningTurtle)
throw new LuaException("Gardening Turtles have been disabled!");
return fertilizeAt(turtle.getPosition().relative(turtle.getDirection()));
}

@LuaFunction
public final Object[] fertilizeUp(IArguments args) throws LuaException {
if (!Config.enableGardeningTurtle)
throw new LuaException("Gardening Turtles have been disabled!");
return fertilizeAt(turtle.getPosition().above());
}

@LuaFunction
public final Object[] fertilizeDown(IArguments args) throws LuaException {
if (!Config.enableGardeningTurtle)
throw new LuaException("Gardening Turtles have been disabled!");
return fertilizeAt(turtle.getPosition().below());
}

private Object[] fertilizeAt(BlockPos pos) {
ItemStack selected = turtle.getInventory().getItem(turtle.getSelectedSlot());
if (!selected.is(Items.BONE_MEAL)) return new Object[]{false};
BlockState state = turtle.getLevel().getBlockState(pos);
if (state.getBlock() instanceof BonemealableBlock growable) {
boolean success = BoneMealItem.applyBonemeal(selected, turtle.getLevel(), pos, null);
return new Object[]{success};
}
return new Object[]{false};
}

private Object[] getGrowthAt(BlockPos pos) throws LuaException {
BlockState state = turtle.getLevel().getBlockState(pos);
if (!(state.getBlock() instanceof BonemealableBlock))
throw new LuaException("Block is not growable");
for (Property<?> prop : state.getProperties()) {
if (prop.getName().equalsIgnoreCase("age") || prop.getName().equalsIgnoreCase("stage")) {
if (prop instanceof IntegerProperty ip) {
Collection<Integer> values = ip.getPossibleValues();
int[] arr = values.stream().mapToInt(i -> i).sorted().toArray();
int cur = (Integer) state.getValue(ip);
Map<String, Object> growth = new HashMap<>();
growth.put("age", cur);
growth.put("min", arr[0]);
growth.put("max", arr[arr.length - 1]);
growth.put("percent", (float) cur / arr[arr.length - 1]);
return new Object[]{growth};
}
}
}
throw new LuaException("Growable block does not have an age property");
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}
}
