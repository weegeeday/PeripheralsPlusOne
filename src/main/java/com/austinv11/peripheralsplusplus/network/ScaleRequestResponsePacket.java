package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class ScaleRequestResponsePacket {

public ScaleRequestResponsePacket() {}

public static void encode(ScaleRequestResponsePacket pkt, FriendlyByteBuf buf) {}

public static ScaleRequestResponsePacket decode(FriendlyByteBuf buf) {
return new ScaleRequestResponsePacket();
}

public static void handle(ScaleRequestResponsePacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
