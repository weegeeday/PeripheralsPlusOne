package com.austinv11.peripheralsplusplus.tiles;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.utils.ChatUtil;
import com.austinv11.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.austinv11.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public class TileEntityChatBox extends BlockEntity implements IPlusPlusPeripheral {

private final HashMap<IComputerAccess, Boolean> computers = new HashMap<>();
private static final int TICKER_INTERVAL = 20;
private int ticker = 0;
private int subticker = 0;
private ITurtleAccess turtle;

public TileEntityChatBox(BlockPos pos, BlockState state) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.CHAT_BOX.get(), pos, state);
}

public TileEntityChatBox(ITurtleAccess turtle) {
super(com.austinv11.peripheralsplusplus.init.ModTileEntities.CHAT_BOX.get(), turtle.getPosition(), turtle.getLevel().getBlockState(turtle.getPosition()));
this.turtle = turtle;
}

public static void serverTick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, TileEntityChatBox self) {
if (self.subticker > 0)
self.subticker--;
if (self.subticker == 0 && self.ticker != 0)
self.ticker = 0;
}

public void tickAsTurtle() {
if (turtle != null)
this.worldPosition = turtle.getPosition();
if (subticker > 0)
subticker--;
if (subticker == 0 && ticker != 0)
ticker = 0;
}

public void onChat(Player player, String message) {
for (IComputerAccess computer : computers.keySet())
computer.queueEvent("chat", new Object[]{player.getName().getString(), message});
}

public void onDeath(Player player, DamageSource source) {
String killer = null;
Entity ent = source.getEntity();
if (ent != null)
killer = ent.getName().getString();
for (IComputerAccess computer : computers.keySet())
computer.queueEvent("death", new Object[]{player.getName().getString(), killer, source.getMsgId()});
}

public void onCommand(ServerPlayer player, String message) {
for (IComputerAccess computer : computers.keySet())
computer.queueEvent("command", new Object[]{player.getName().getString(),
Util.arrayToMap(message.split(" "))});
}

@Override
public String getType() {
return "chatBox";
}

@LuaFunction
public final Object[] say(IArguments args) throws LuaException {
if (!Config.enableChatBox)
throw new LuaException("Chat boxes have been disabled");
String text = args.getString(0);
double range = args.count() > 1 ? args.getDouble(1) : Config.sayRange;
boolean unlimitedY = args.count() > 2 && args.getBoolean(2);
String prefix = args.count() > 3 ? args.getString(3) : null;

if (ticker == Config.sayRate)
throw new LuaException("Please try again later, you are sending messages too often");

String message;
if (Config.logCoords) {
message = ChatUtil.getCoordsPrefix(this) + text;
} else if (prefix != null) {
message = "[" + prefix + "] " + text;
} else {
message = "[@] " + text;
}

double finalRange = (Config.sayRange < 0 || range < 0) ? Double.MAX_VALUE
: Math.min(range, Config.sayRange);

synchronized (this) {
ChatUtil.sendMessage(this, message, finalRange, unlimitedY && Config.allowUnlimitedVertical);
subticker = TICKER_INTERVAL;
ticker++;
}
return new Object[]{true};
}

@LuaFunction
public final Object[] tell(IArguments args) throws LuaException {
if (!Config.enableChatBox)
throw new LuaException("Chat boxes have been disabled");
String ign = args.getString(0);
String text = args.getString(1);
double range = args.count() > 2 ? args.getDouble(2) : (Config.sayRange < 0 ? Double.MAX_VALUE : Config.sayRange);
boolean unlimitedY = args.count() > 3 && args.getBoolean(3);
String prefix = args.count() > 4 ? args.getString(4) : null;

if (ticker == Config.sayRate)
throw new LuaException("Please try again later, you are sending messages too often");
if (prefix != null && Config.logCoords)
throw new LuaException("Coordinate logging is enabled, disable this to enable naming");

String message;
if (Config.logCoords) {
message = ChatUtil.getCoordsPrefix(this) + text;
} else if (prefix != null) {
message = "[" + prefix + "] " + text;
} else {
message = "[@] " + text;
}

synchronized (this) {
subticker = TICKER_INTERVAL;
ticker++;
ChatUtil.sendMessage(ign, this, message, range, unlimitedY && Config.allowUnlimitedVertical);
}
return new Object[]{true};
}

@Override
public void attach(IComputerAccess computer) {
if (computers.isEmpty())
ChatListener.chatBoxMap.put(this, true);
computers.put(computer, true);
}

@Override
public void detach(IComputerAccess computer) {
computers.remove(computer);
if (computers.isEmpty())
ChatListener.chatBoxMap.remove(this);
}

@Override
public boolean equals(IPeripheral other) {
return other == this;
}

public static class ChatListener {
private static final HashMap<TileEntityChatBox, Boolean> chatBoxMap = new HashMap<>();

@SubscribeEvent(priority = EventPriority.LOWEST)
public void onChat(ServerChatEvent event) {
if (!event.isCanceled() && Config.enableChatBox) {
String commandPrefix = Config.chatboxCommandPrefix.trim();
for (TileEntityChatBox box : chatBoxMap.keySet()) {
BlockPos pos = box.getBlockPos();
Vec3 boxPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
double dist = boxPos.distanceTo(event.getPlayer().position());
if (Config.readRange >= 0 && dist > Config.readRange)
continue;
if (!commandPrefix.isEmpty() && !commandPrefix.equals(" ") && event.getMessage().startsWith(commandPrefix)) {
event.setCanceled(true);
box.onCommand(event.getPlayer(), event.getMessage().replace(commandPrefix, ""));
} else {
box.onChat(event.getPlayer(), event.getMessage());
}
}
}
}

@SubscribeEvent(priority = EventPriority.LOWEST)
public void onDeath(LivingDeathEvent event) {
if (!event.isCanceled() && Config.enableChatBox) {
if (event.getEntity() instanceof Player player) {
for (TileEntityChatBox box : chatBoxMap.keySet()) {
BlockPos pos = box.getBlockPos();
Vec3 boxPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
if (Config.readRange < 0 || boxPos.distanceTo(event.getEntity().position()) <= Config.readRange)
box.onDeath(player, event.getSource());
}
}
}
}
}
}
