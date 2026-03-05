package com.austinv11.peripheralsplusplus.turtles.peripherals;

import com.austinv11.peripheralsplusplus.reference.Config;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Optional;

public class PeripheralTank implements IPeripheral {

private static final int TRANSFER_AMOUNT = 1000;
private final ITurtleAccess turtle;
private final TurtleSide side;
private net.minecraftforge.fluids.capability.templates.FluidTank fluidTank;

public PeripheralTank(ITurtleAccess turtle, TurtleSide side) {
this.turtle = turtle;
this.side = side;
fluidTank = new net.minecraftforge.fluids.capability.templates.FluidTank(Config.maxNumberOfMillibuckets);
if (!turtle.getLevel().isClientSide()) {
CompoundTag turtleTag = turtle.getUpgradeNBTData(side);
if (turtleTag.contains("TankData"))
fluidTank.readFromNBT(turtleTag.getCompound("TankData"));
}
}

@Override
public String getType() {
return "tank";
}

@LuaFunction
public final Object[] getFluid(IArguments args) {
FluidStack fluid = fluidTank.getFluid();
if (!fluid.isEmpty()) {
HashMap<String, Object> map = new HashMap<>();
map.put("amount", fluid.getAmount());
map.put("name", fluid.getDisplayName().getString());
ResourceLocation key = ForgeRegistries.FLUIDS.getKey(fluid.getFluid());
map.put("id", key != null ? key.toString() : "unknown");
return new Object[]{map};
}
return new Object[0];
}

@LuaFunction
public final Object[] fill(IArguments args) throws LuaException {
int fillSlot = args.count() > 0 ? args.getInt(0) : turtle.getSelectedSlot();
return doFill(fillSlot);
}

@LuaFunction
public final Object[] drain(IArguments args) throws LuaException {
int drainSlot = args.count() > 0 ? args.getInt(0) : turtle.getSelectedSlot();
return doDrain(drainSlot);
}

@LuaFunction
public final Object[] place(IArguments args) throws LuaException {
return doEmpty(turtle.getDirection());
}

@LuaFunction
public final Object[] placeUp(IArguments args) throws LuaException {
return doEmpty(Direction.UP);
}

@LuaFunction
public final Object[] placeDown(IArguments args) throws LuaException {
return doEmpty(Direction.DOWN);
}

@LuaFunction
public final Object[] suck(IArguments args) throws LuaException {
return doSuck(turtle.getDirection());
}

@LuaFunction
public final Object[] suckUp(IArguments args) throws LuaException {
return doSuck(Direction.UP);
}

@LuaFunction
public final Object[] suckDown(IArguments args) throws LuaException {
return doSuck(Direction.DOWN);
}

private Object[] doSuck(Direction direction) throws LuaException {
BlockPos pos = turtle.getPosition().relative(direction);
IFluidHandler handler = FluidUtil.getFluidHandler(turtle.getLevel(), pos, direction.getOpposite()).resolve().orElse(null);
if (handler == null)
throw new LuaException("Block is not a fluid block");
FluidStack transferred = FluidUtil.tryFluidTransfer(fluidTank, handler, TRANSFER_AMOUNT, true);
if (transferred.isEmpty()) return new Object[]{0};
saveTankData();
return new Object[]{transferred.getAmount()};
}

private Object[] doEmpty(Direction direction) {
FluidStack fluid = fluidTank.getFluid();
if (fluid.isEmpty()) return new Object[]{0};
BlockPos pos = turtle.getPosition().relative(direction);
boolean placed = FluidUtil.tryPlaceFluid(null, turtle.getLevel(), null, pos, fluidTank, fluid);
if (placed) {
saveTankData();
return new Object[]{TRANSFER_AMOUNT};
}
Optional<IFluidHandler> handlerOpt = FluidUtil.getFluidHandler(turtle.getLevel(), pos, direction.getOpposite()).resolve();
if (handlerOpt.isPresent()) {
IFluidHandler handler = handlerOpt.get();
FluidStack transferred = FluidUtil.tryFluidTransfer(handler, fluidTank, TRANSFER_AMOUNT, true);
if (!transferred.isEmpty()) {
saveTankData();
return new Object[]{transferred.getAmount()};
}
}
return new Object[]{0};
}

private Object[] doDrain(int slot) throws LuaException {
ItemStack stack = turtle.getInventory().getItem(slot);
Optional<IFluidHandlerItem> handlerOpt = FluidUtil.getFluidHandler(stack).resolve();
if (handlerOpt.isEmpty())
throw new LuaException("Item does not contain fluid");
IFluidHandlerItem handler = handlerOpt.get();
FluidStack transfer = FluidUtil.tryFluidTransfer(fluidTank, handler, Config.maxNumberOfMillibuckets, true);
if (transfer.isEmpty()) return new Object[]{0};
turtle.getInventory().setItem(slot, handler.getContainer());
saveTankData();
return new Object[]{transfer.getAmount()};
}

private Object[] doFill(int slot) throws LuaException {
ItemStack stack = turtle.getInventory().getItem(slot);
Optional<IFluidHandlerItem> handlerOpt = FluidUtil.getFluidHandler(stack).resolve();
if (handlerOpt.isEmpty())
throw new LuaException("Item cannot contain fluid");
IFluidHandlerItem handler = handlerOpt.get();
if (fluidTank.getFluidAmount() == 0)
throw new LuaException("Internal tank does not contain fluid");
FluidStack transfer = FluidUtil.tryFluidTransfer(handler, fluidTank, Config.maxNumberOfMillibuckets, true);
if (transfer.isEmpty()) return new Object[]{0};
turtle.getInventory().setItem(slot, handler.getContainer());
saveTankData();
return new Object[]{transfer.getAmount()};
}

private void saveTankData() {
CompoundTag turtleTag = turtle.getUpgradeNBTData(side);
CompoundTag tankData = new CompoundTag();
fluidTank.writeToNBT(tankData);
turtleTag.put("TankData", tankData);
turtle.updateUpgradeNBTData(side);
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}
}
