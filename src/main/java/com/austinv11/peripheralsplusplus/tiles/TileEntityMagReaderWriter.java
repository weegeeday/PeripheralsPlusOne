package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityMagReaderWriter extends BlockEntity implements IPlusPlusPeripheral {

private static final int MAX_TRACKS = 3;
private static final String MAG_TAG = Reference.MOD_ID + ":mag_card";
private String[] buffers = new String[MAX_TRACKS];
private final List<IComputerAccess> computers = new ArrayList<>();

public TileEntityMagReaderWriter(BlockPos pos, BlockState state) {
super(null, pos, state);
}

@Nonnull
@Override
public String getType() {
return "mag_reader_writer";
}

@LuaFunction
public final Object[] write(IArguments args) throws LuaException {
return writeLua(new Object[]{args.get(0), args.get(1)});
}

@LuaFunction
public final Object[] clear(IArguments args) throws LuaException {
return clearLua(args.count() > 0 ? new Object[]{args.get(0)} : new Object[0]);
}

private Object[] clearLua(Object[] arguments) throws LuaException {
if (arguments.length < 1) {
buffers = new String[MAX_TRACKS];
return new Object[0];
}
int trackIndex = parseTrackIndex(arguments[0], "First");
buffers[trackIndex] = null;
return new Object[0];
}

private Object[] writeLua(Object[] arguments) throws LuaException {
if (arguments.length < 2)
throw new LuaException("Not enough arguments");
int trackIndex = parseTrackIndex(arguments[0], "First");
if (!(arguments[1] instanceof String))
throw new LuaException("Second argument expected to be a string");
if (!isBufferValid(trackIndex, (String) arguments[1]))
throw new LuaException("Buffer data is invalid for track");
synchronized (this) {
buffers[trackIndex] = (String) arguments[1];
}
return new Object[0];
}

private boolean isBufferValid(int trackIndex, String buffer) {
switch (trackIndex) {
case 0: return buffer.length() <= 79 && buffer.matches("[A-Za-z0-9%^?;=]*");
case 1: return buffer.length() <= 40 && buffer.matches("[0-9%^?;=]*");
case 2: return buffer.length() <= 107 && buffer.matches("[0-9%^?;=]*");
}
return false;
}

private int parseTrackIndex(Object argument, String position) throws LuaException {
if (!(argument instanceof Double))
throw new LuaException(String.format("%s argument expected to be an integer", position));
int trackIndex = ((Double) argument).intValue();
if (trackIndex >= MAX_TRACKS || trackIndex < 0)
throw new LuaException(String.format("Track index out of bounds: %d", trackIndex));
return trackIndex;
}

public void itemSwiped(ItemStack swipedItem) {
if (!isMagCard(swipedItem)) return;
CompoundTag itemTag = swipedItem.getOrCreateTag();
CompoundTag magTag = itemTag.getCompound(MAG_TAG);
synchronized (this) {
for (int i = 0; i < buffers.length; i++)
if (buffers[i] != null)
magTag.putString(String.valueOf(i), buffers[i]);
}
itemTag.put(MAG_TAG, magTag);
Object[] event = new Object[]{magTag.getString("0"), magTag.getString("1"), magTag.getString("2")};
for (IComputerAccess computer : computers)
computer.queueEvent("mag_swipe", event);
}

private boolean isMagCard(ItemStack item) {
return item.is(ModItems.PLASTIC_CARD.get()) && item.hasTag() && item.getTag().contains(MAG_TAG) && item.getCount() == 1;
}

@Override
public void attach(IComputerAccess computer) { computers.add(computer); }
@Override
public void detach(IComputerAccess computer) { computers.remove(computer); }
@Override
public boolean equals(@Nullable IPeripheral other) { return other == this; }
}
