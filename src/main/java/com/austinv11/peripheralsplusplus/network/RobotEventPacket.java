package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class RobotEventPacket {

public RobotEventPacket() {}

public static void encode(RobotEventPacket pkt, FriendlyByteBuf buf) {}

public static RobotEventPacket decode(FriendlyByteBuf buf) {
return new RobotEventPacket();
}

public static void handle(RobotEventPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
