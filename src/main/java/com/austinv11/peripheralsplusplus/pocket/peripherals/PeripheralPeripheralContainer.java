package com.austinv11.peripheralsplusplus.pocket.peripherals;

import com.austinv11.peripheralsplusplus.lua.LuaObjectPeripheralWrap;
import com.austinv11.peripheralsplusplus.reference.Config;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class PeripheralPeripheralContainer implements IPeripheral {

private final Map<IPocketUpgrade, IPeripheral> pocketUpgrades;
private final Map<ResourceLocation, ItemStack> unequippedItems = new HashMap<>();

public PeripheralPeripheralContainer(Map<IPocketUpgrade, IPeripheral> pocketUpgrades) {
this.pocketUpgrades = pocketUpgrades;
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
List<IPeripheral> peripherals = getPeripherals();
for (int i = 0; i < peripherals.size(); i++)
returnVals.put(i + 1, peripherals.get(i).getType());
return new Object[]{returnVals};
}

@LuaFunction
public final Object[] wrapPeripheral(IArguments args) throws LuaException {
if (!Config.enablePeripheralContainer)
throw new LuaException("Peripheral Containers have been disabled");
return new Object[]{new LuaObjectPeripheralWrap(getPeripheralByName(args.getString(0)), null)};
}

@LuaFunction
public final Object[] unequipPeripheral(IArguments args) throws LuaException {
if (!Config.enablePeripheralContainer)
throw new LuaException("Peripheral Containers have been disabled");
IPeripheral peripheral = getPeripheralByName(args.getString(0));
if (peripheral == null) return new Object[]{false};
for (Map.Entry<IPocketUpgrade, IPeripheral> entry : pocketUpgrades.entrySet()) {
if (entry.getValue().equals(peripheral)) {
unequippedItems.put(entry.getKey().getUpgradeID(), entry.getKey().getCraftingItem());
pocketUpgrades.remove(entry.getKey());
return new Object[]{true};
}
}
return new Object[]{false};
}

private IPeripheral getPeripheralByName(String name) {
for (IPeripheral p : pocketUpgrades.values())
if (p.getType().equals(name)) return p;
return null;
}

private List<IPeripheral> getPeripherals() {
return new ArrayList<>(pocketUpgrades.values());
}

@Override
public boolean equals(IPeripheral other) { return other == this; }

@Override
public void attach(IComputerAccess computer) {
for (IPeripheral p : getPeripherals()) p.attach(computer);
}

@Override
public void detach(IComputerAccess computer) {
for (IPeripheral p : getPeripherals()) p.detach(computer);
}

public Map<IPocketUpgrade, IPeripheral> getUpgrades() { return pocketUpgrades; }
public Map<ResourceLocation, ItemStack> getUnequippedItems() { return unequippedItems; }
}
