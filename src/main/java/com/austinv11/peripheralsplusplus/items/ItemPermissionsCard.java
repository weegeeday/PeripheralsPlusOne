package com.austinv11.peripheralsplusplus.items;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemPermissionsCard extends ItemPPP {

public ItemPermissionsCard(Properties props) {
super(props.stacksTo(1));
}

@Override
public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
ItemStack stack = player.getItemInHand(hand);
if (!Config.enablePlayerInterface)
return InteractionResultHolder.pass(stack);

if (!player.isCrouching()) {
if (!world.isClientSide()) {
CompoundTag tag = stack.getTag();
if (tag == null || !tag.contains("profile")) {
CompoundTag nbt = new CompoundTag();
NbtUtils.writeGameProfile(nbt, player.getGameProfile());
stack.getOrCreateTag().put("profile", nbt);
stack.getOrCreateTag().putBoolean("getStacks", false);
stack.getOrCreateTag().putBoolean("withdraw", false);
stack.getOrCreateTag().putBoolean("deposit", false);
player.sendSystemMessage(Component.translatable("peripheralsplusone.chat.permCard.set"));
} else {
player.sendSystemMessage(Component.translatable("peripheralsplusone.chat.permCard.alreadySet"));
return InteractionResultHolder.fail(stack);
}
}
} else {
if (!stack.hasTag() || !stack.getTag().contains("profile")) {
if (!world.isClientSide())
player.sendSystemMessage(Component.translatable("peripheralsplusone.chat.permCard.notSet"));
return InteractionResultHolder.fail(stack);
}
GameProfile profile = NbtUtils.readGameProfile(stack.getTag().getCompound("profile"));
if (profile == null || !profile.getId().equals(player.getGameProfile().getId())) {
if (!world.isClientSide())
player.sendSystemMessage(Component.translatable("peripheralsplusone.chat.permCard.wrongOwner"));
return InteractionResultHolder.fail(stack);
}
// Open GUI for permissions configuration
}
return InteractionResultHolder.success(stack);
}

@Override
public Component getName(ItemStack stack) {
if (stack.hasTag() && stack.getTag().contains("profile")) {
GameProfile profile = NbtUtils.readGameProfile(stack.getTag().getCompound("profile"));
if (profile != null && profile.getName() != null)
return Component.translatable("item.peripheralsplusplus.permissions_card")
.append(" - " + profile.getName());
}
return super.getName(stack);
}
}
