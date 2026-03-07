package com.austinv11.peripheralsplusplus.utils;

import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

public class FakeTurtlePlayer extends FakePlayer {
private static final GameProfile s_profile = new GameProfile(UUID.fromString("0d0c4ca0-4ff1-11e4-916c-0800200c9a66"), "ComputerCraft");

public FakeTurtlePlayer(ServerLevel world) {
super(world, s_profile);
}

public FakeTurtlePlayer(ITurtleAccess turtle) {
this((ServerLevel) turtle.getLevel());
BlockPos position = turtle.getPosition();
setPos(position.getX() + 0.5D, position.getY() + 0.5D, position.getZ() + 0.5D);
}

@Override
public float getEyeHeight(Pose pose) {
return 0.0F;
}
}
