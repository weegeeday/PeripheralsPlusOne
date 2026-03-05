package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class SynthPacket {

public SynthPacket() {}

public SynthPacket(String text, String voice, Float pitch, Float pitchRange, Float pitchShift,
                   Float rate, Float volume, net.minecraft.core.BlockPos pos, int unused,
                   dan200.computercraft.api.turtle.TurtleSide side, java.util.UUID eventId) {}

public static void encode(SynthPacket pkt, FriendlyByteBuf buf) {}

public static SynthPacket decode(FriendlyByteBuf buf) {
return new SynthPacket();
}

public static void handle(SynthPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
