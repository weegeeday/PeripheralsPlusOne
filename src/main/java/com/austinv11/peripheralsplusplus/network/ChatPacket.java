package com.austinv11.peripheralsplusplus.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChatPacket {

public final String text;

public ChatPacket(String text) {
this.text = text;
}

public static void encode(ChatPacket pkt, FriendlyByteBuf buf) {
buf.writeUtf(pkt.text);
}

public static ChatPacket decode(FriendlyByteBuf buf) {
return new ChatPacket(buf.readUtf());
}

public static void handle(ChatPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
NetworkEvent.Context ctx = ctxSupplier.get();
ctx.enqueueWork(() -> {
net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
if (mc.player != null)
mc.player.displayClientMessage(net.minecraft.network.chat.Component.literal(pkt.text), false);
});
ctx.setPacketHandled(true);
}
}
