package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.lua.LuaObjectPeripheralWrap;
import com.austinv11.peripheralsplusplus.utils.peripheralcontainer.ContainedPeripheral;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileEntityPeripheralContainer extends BlockEntity implements IPlusPlusPeripheral {

private final List<ContainedPeripheral> peripheralsContained = new ArrayList<>();

public TileEntityPeripheralContainer(BlockPos pos, BlockState state) {
super(null, pos, state);
}

@Override
public void load(CompoundTag tag) {
super.load(tag);
if (tag.contains("peripherals")) {
ListTag peripherals = tag.getList("peripherals", 10);
for (int i = 0; i < peripherals.size(); i++) {
addPeripheral(new ContainedPeripheral(peripherals.getCompound(i)));
}
}
}

@Override
protected void saveAdditional(CompoundTag tag) {
super.saveAdditional(tag);
ListTag peripherals = new ListTag();
for (ContainedPeripheral peripheral : peripheralsContained)
peripherals.add(peripheral.toNbt());
tag.put("peripherals", peripherals);
}

@Override
public String getType() {
return "peripheralContainer";
}

@LuaFunction
public final Object[] getContainedPeripherals(IArguments args) throws LuaException {
if (!Config.enablePeripheralContainer)
throw new LuaException("Peripheral Containers have been disabled");
HashMap<Integer, String> returnVals = new HashMap<>();
for (int i = 0; i < peripheralsContained.size(); i++)
returnVals.put(i + 1, peripheralsContained.get(i).getPeripheral().getType());
return new Object[]{returnVals};
}

@LuaFunction
public final Object[] wrapPeripheral(IArguments args) throws LuaException {
if (!Config.enablePeripheralContainer)
throw new LuaException("Peripheral Containers have been disabled");
String name = args.getString(0);
return new Object[]{new LuaObjectPeripheralWrap(getPeripheralByName(name), null)};
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}

public void addPeripheral(ContainedPeripheral peripheral) {
if (peripheral.getPeripheral() == null)
return;
peripheralsContained.add(peripheral);
setChanged();
}

private IPeripheral getPeripheralByName(String name) {
for (ContainedPeripheral p : peripheralsContained)
if (p.getPeripheral().getType().equals(name))
return p.getPeripheral();
return null;
}

public List<ContainedPeripheral> getContainedPeripheralList() {
return peripheralsContained;
}
}
