package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class PermCardChangePacket {

public PermCardChangePacket() {}

public static void encode(PermCardChangePacket pkt, FriendlyByteBuf buf) {}

public static PermCardChangePacket decode(FriendlyByteBuf buf) {
return new PermCardChangePacket();
}

public static void handle(PermCardChangePacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
