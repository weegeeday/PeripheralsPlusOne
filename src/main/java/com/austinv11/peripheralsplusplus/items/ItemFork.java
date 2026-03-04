package com.austinv11.peripheralsplusplus.items;

import com.austinv11.peripheralsplusplus.capabilities.rfid.CapabilityRfid;
import com.austinv11.peripheralsplusplus.capabilities.rfid.RfidTagHolder;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFork extends ItemPPP {

public ItemFork(Properties props) {
super(props.stacksTo(1));
}

@Override
public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
tooltip.add(Component.translatable("peripheralsplusone.description.fork"));
}

@Override
public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
if (!attacker.level().isClientSide())
chipEntity(target, attacker);
return true;
}

@Override
public boolean interactLivingEntity(ItemStack stack, Player player, LivingEntity target, net.minecraft.world.InteractionHand hand) {
if (!player.level().isClientSide())
chipEntity(target, player);
return true;
}

private void chipEntity(LivingEntity target, LivingEntity attacker) {
target.getCapability(CapabilityRfid.CAPABILITY).ifPresent(tagHolder -> {
tagHolder.setProdded(true);
target.hurt(target.level().damageSources().generic(), 1.0f);
if (!tagHolder.getTag().isEmpty()) {
ItemEntity tagItem = new ItemEntity(target.level(), target.getX(), target.getY(), target.getZ(),
tagHolder.getTag());
tagHolder.setTag(ItemStack.EMPTY);
target.level().addFreshEntity(tagItem);
}
});
}
}
