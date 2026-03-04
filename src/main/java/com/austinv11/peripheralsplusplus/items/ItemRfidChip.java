package com.austinv11.peripheralsplusplus.items;

import com.austinv11.peripheralsplusplus.capabilities.rfid.CapabilityRfid;
import com.austinv11.peripheralsplusplus.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemRfidChip extends ItemPPP {

public ItemRfidChip(Properties props) {
super(props);
}

@Override
public boolean interactLivingEntity(ItemStack item, Player player, LivingEntity target, InteractionHand hand) {
if (player.level().isClientSide()) return true;
target.getCapability(CapabilityRfid.CAPABILITY).ifPresent(tagHolder -> {
if (!item.is(ModItems.RFID_CHIP.get()) || item.getCount() < 1) return;
if (!tagHolder.hasBeenProdded()) return;
if (tagHolder.getTag().isEmpty()) {
ItemStack tag = item.copy();
tag.setCount(1);
tagHolder.setTag(tag);
tagHolder.setProdded(false);
item.shrink(1);
}
});
return true;
}
}
