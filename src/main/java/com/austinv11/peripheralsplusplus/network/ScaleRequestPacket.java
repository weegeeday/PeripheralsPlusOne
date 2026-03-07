package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class ScaleRequestPacket {

public ScaleRequestPacket() {}

public static void encode(ScaleRequestPacket pkt, FriendlyByteBuf buf) {}

public static ScaleRequestPacket decode(FriendlyByteBuf buf) {
return new ScaleRequestPacket();
}

public static void handle(ScaleRequestPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
