package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class GuiPacket {

public GuiPacket() {}

public static void encode(GuiPacket pkt, FriendlyByteBuf buf) {}

public static GuiPacket decode(FriendlyByteBuf buf) {
return new GuiPacket();
}

public static void handle(GuiPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
