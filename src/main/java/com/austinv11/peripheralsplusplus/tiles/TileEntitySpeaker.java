package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.network.SynthPacket;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

public class TileEntitySpeaker extends BlockEntity implements IPlusPlusPeripheral {

private ITurtleAccess turtle;
private TurtleSide side = null;
private final List<IComputerAccess> computers = new ArrayList<>();
private final Map<UUID, Long> pendingEvents = new HashMap<>();

public TileEntitySpeaker(BlockPos pos, BlockState state) {
super(null, pos, state);
}

public TileEntitySpeaker(ITurtleAccess turtle, TurtleSide side) {
super(null, turtle.getPosition(), turtle.getLevel().getBlockState(turtle.getPosition()));
this.turtle = turtle;
this.side = side;
}

public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, TileEntitySpeaker self) {
if (self.turtle != null)
self.worldPosition = self.turtle.getPosition();
synchronized (self) {
for (Map.Entry<UUID, Long> entry : new ArrayList<>(self.pendingEvents.entrySet())) {
if (System.currentTimeMillis() - entry.getValue() > 30000) {
self.onSpeechCompletion("", entry.getKey());
break;
}
}
}
}

@Override
public String getType() {
return "speaker";
}

@LuaFunction(mainThread = false)
public final Object[] speak(IArguments args) throws LuaException {
return synthesize(args);
}

@LuaFunction(mainThread = false)
public final Object[] synthesize(IArguments args) throws LuaException {
if (!Config.enableSpeaker)
throw new LuaException("Speakers have been disabled");
String text = args.getString(0);
double range = args.count() > 1 ? args.getDouble(1) : (Config.speechRange < 0 ? Double.MAX_VALUE : Config.speechRange);
String voice = args.count() > 2 ? args.getString(2) : "kevin16";
Float pitch = args.count() > 3 ? (float) args.getDouble(3) : null;
Float pitchRange = args.count() > 4 ? (float) args.getDouble(4) : null;
Float pitchShift = args.count() > 5 ? (float) args.getDouble(5) : null;
Float rateVal = args.count() > 6 ? (float) args.getDouble(6) : null;
Float volume = args.count() > 7 ? (float) args.getDouble(7) : null;

UUID eventId = UUID.randomUUID();
while (pendingEvents.containsKey(eventId)) eventId = UUID.randomUUID();
pendingEvents.put(eventId, System.currentTimeMillis());

BlockPos pos = getBlockPos();
PeripheralsPlusPlus.NETWORK.send(
PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
pos.getX(), pos.getY(), pos.getZ(), range, null)),
new SynthPacket(text, voice, pitch, pitchRange, pitchShift, rateVal, volume, pos, 0, side, eventId));

return new Object[]{eventId.toString()};
}

@Override
public void attach(IComputerAccess computer) {
computers.add(computer);
}

@Override
public void detach(IComputerAccess computer) {
computers.remove(computer);
}

@Override
public boolean equals(IPeripheral other) {
return this == other;
}

public void onSpeechCompletion(String text, UUID eventId) {
synchronized (this) {
if (!pendingEvents.containsKey(eventId))
return;
pendingEvents.remove(eventId);
}
for (IComputerAccess computer : computers)
computer.queueEvent("synthComplete", new Object[]{text, eventId});
}
}
