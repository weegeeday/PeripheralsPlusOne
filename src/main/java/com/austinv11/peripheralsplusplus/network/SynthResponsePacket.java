package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class SynthResponsePacket {

public SynthResponsePacket() {}

public static void encode(SynthResponsePacket pkt, FriendlyByteBuf buf) {}

public static SynthResponsePacket decode(FriendlyByteBuf buf) {
return new SynthResponsePacket();
}

public static void handle(SynthResponsePacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
