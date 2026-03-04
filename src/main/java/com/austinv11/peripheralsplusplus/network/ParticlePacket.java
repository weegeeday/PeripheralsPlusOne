package com.austinv11.peripheralsplusplus.network;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticlePacket {

public final String name;
public final double x, y, z, xVel, yVel, zVel;

public ParticlePacket(String name, double x, double y, double z, double xVel, double yVel, double zVel) {
this.name = name; this.x = x; this.y = y; this.z = z;
this.xVel = xVel; this.yVel = yVel; this.zVel = zVel;
}

public static void encode(ParticlePacket pkt, FriendlyByteBuf buf) {
buf.writeUtf(pkt.name);
buf.writeDouble(pkt.x); buf.writeDouble(pkt.y); buf.writeDouble(pkt.z);
buf.writeDouble(pkt.xVel); buf.writeDouble(pkt.yVel); buf.writeDouble(pkt.zVel);
}

public static ParticlePacket decode(FriendlyByteBuf buf) {
return new ParticlePacket(buf.readUtf(),
buf.readDouble(), buf.readDouble(), buf.readDouble(),
buf.readDouble(), buf.readDouble(), buf.readDouble());
}

public static void handle(ParticlePacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
NetworkEvent.Context ctx = ctxSupplier.get();
ctx.enqueueWork(() -> {
net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
if (mc.level != null) {
// "portal" -> ParticleTypes.PORTAL, else default smoke
var particle = "portal".equals(pkt.name) ? ParticleTypes.PORTAL : ParticleTypes.SMOKE;
mc.level.addParticle(particle, pkt.x, pkt.y, pkt.z, pkt.xVel, pkt.yVel, pkt.zVel);
}
});
ctx.setPacketHandled(true);
}
}
