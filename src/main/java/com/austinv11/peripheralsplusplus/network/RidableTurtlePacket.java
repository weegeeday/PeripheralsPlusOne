package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class RidableTurtlePacket {

public RidableTurtlePacket() {}

public static void encode(RidableTurtlePacket pkt, FriendlyByteBuf buf) {}

public static RidableTurtlePacket decode(FriendlyByteBuf buf) {
return new RidableTurtlePacket();
}

public static void handle(RidableTurtlePacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
