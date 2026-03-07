package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Stub packet - to be fully implemented in a future phase */
public class CommandPacket {

public CommandPacket() {}

public static void encode(CommandPacket pkt, FriendlyByteBuf buf) {}

public static CommandPacket decode(FriendlyByteBuf buf) {
return new CommandPacket();
}

public static void handle(CommandPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
ctxSupplier.get().setPacketHandled(true);
}
}
