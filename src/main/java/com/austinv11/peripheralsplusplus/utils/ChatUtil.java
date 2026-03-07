package com.austinv11.peripheralsplusplus.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

public static void sendMessage(BlockEntity te, String text, double range, boolean unlimitedY) {
if (range == Double.MAX_VALUE) {
for (ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
for (Player player : serverLevel.players())
if (player instanceof ServerPlayer sp)
sendChatPacket(sp, text);
} else if (unlimitedY) {
BlockPos pos = te.getBlockPos();
for (Player player : te.getLevel().players()) {
Vec3 playerPos = new Vec3(player.getX(), pos.getY(), player.getZ());
if (playerPos.distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ())) > range)
continue;
if (player instanceof ServerPlayer sp)
sendChatPacket(sp, text);
}
} else {
BlockPos pos = te.getBlockPos();
Level level = te.getLevel();
double rangeSq = range * range;
for (Player player : level.players()) {
if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= rangeSq)
if (player instanceof ServerPlayer sp)
sendChatPacket(sp, text);
}
}
}

public static boolean sendMessage(String ign, BlockEntity te, String text, double range, boolean unlimitedY) {
Player player = getPlayer(ign, range == Double.MAX_VALUE ? null : te.getLevel());
if (player != null) {
BlockPos pos = te.getBlockPos();
Vec3 playerPos = new Vec3(player.getX(),
unlimitedY ? pos.getY() : player.getY(),
player.getZ());
if (playerPos.distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ())) > range)
return false;
if (player instanceof ServerPlayer sp)
sendChatPacket(sp, text);
return true;
}
return false;
}

private static void sendChatPacket(ServerPlayer player, String text) {
// Use the ChatPacket network message
com.austinv11.peripheralsplusplus.PeripheralsPlusPlus.NETWORK.send(
net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player),
new com.austinv11.peripheralsplusplus.network.ChatPacket(text));
}

private static Player getPlayer(String ign, Level level) {
List<Player> players = new ArrayList<>();
if (level == null)
for (ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
players.addAll(serverLevel.players());
else
players = new ArrayList<>(level.players());
for (Player p : players) {
if (p.getName().getString().equalsIgnoreCase(ign))
return p;
}
return null;
}

public static String getCoordsPrefix(BlockEntity te) {
BlockPos pos = te.getBlockPos();
return "[#" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "] ";
}

public static String getCoordsPrefix(Entity ent) {
return "[#" + ent.getX() + "," + ent.getY() + "," + ent.getZ() + "] ";
}
}
