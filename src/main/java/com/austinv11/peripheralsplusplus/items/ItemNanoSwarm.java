package com.austinv11.peripheralsplusplus.items;

import com.austinv11.peripheralsplusplus.capabilities.nano.CapabilityNanoBot;
import com.austinv11.peripheralsplusplus.capabilities.nano.NanoBotHolder;
import com.austinv11.peripheralsplusplus.entities.EntityNanoBotSwarm;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAntenna;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class ItemNanoSwarm extends ItemPPP {

public ItemNanoSwarm(Properties props) {
super(props.stacksTo(16));
}

@Override
public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
ItemStack stack = player.getItemInHand(hand);
if (stack.hasTag() && stack.getTag().contains("identifier")) {
if (!world.isClientSide()) {
EntityNanoBotSwarm swarm = new EntityNanoBotSwarm(world, player);
swarm.antennaIdentifier = UUID.fromString(stack.getTag().getString("identifier"));
if (stack.getTag().contains("label"))
swarm.label = stack.getTag().getString("label");
swarm.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.1f, 6);
world.addFreshEntity(swarm);
}
stack.shrink(1);
return InteractionResultHolder.success(stack);
}
return InteractionResultHolder.fail(stack);
}

public static void addSwarmForEntity(EntityNanoBotSwarm swarm, Entity hit) {
if (TileEntityAntenna.ANTENNA_REGISTRY.containsKey(swarm.antennaIdentifier)) {
TileEntityAntenna antenna = TileEntityAntenna.ANTENNA_REGISTRY.get(swarm.antennaIdentifier);
antenna.registerEntity(hit);
hit.getCapability(CapabilityNanoBot.CAPABILITY).ifPresent(props -> {
props.setBots(props.getBots() + Config.numberOfInstructions);
props.setAntenna(swarm.antennaIdentifier);
});
}
}

public static boolean doInstruction(UUID identifier, Entity performer, boolean allowIfDead, int cost) {
if (!performer.isRemoved() || allowIfDead) {
if (TileEntityAntenna.ANTENNA_REGISTRY.containsKey(identifier)) {
TileEntityAntenna antenna = TileEntityAntenna.ANTENNA_REGISTRY.get(identifier);
if (antenna.isEntityRegistered(performer)) {
NanoBotHolder[] holder = {null};
performer.getCapability(CapabilityNanoBot.CAPABILITY).ifPresent(p -> holder[0] = p);
if (holder[0] == null || holder[0].getBots() < cost) return false;
holder[0].setBots(holder[0].getBots() - cost);
if (holder[0].getBots() <= 0) antenna.removeEntity(performer);
return true;
}
}
}
return false;
}
}
