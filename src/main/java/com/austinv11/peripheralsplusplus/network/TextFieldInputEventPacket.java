package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class TextFieldInputEventPacket {

public TextFieldInputEventPacket() {}

public static void encode(TextFieldInputEventPacket pkt, FriendlyByteBuf buf) {}

public static TextFieldInputEventPacket decode(FriendlyByteBuf buf) {
return new TextFieldInputEventPacket();
}

public static void handle(TextFieldInputEventPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
