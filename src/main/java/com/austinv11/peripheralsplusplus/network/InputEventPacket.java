package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class InputEventPacket {

public InputEventPacket() {}

public static void encode(InputEventPacket pkt, FriendlyByteBuf buf) {}

public static InputEventPacket decode(FriendlyByteBuf buf) {
return new InputEventPacket();
}

public static void handle(InputEventPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
