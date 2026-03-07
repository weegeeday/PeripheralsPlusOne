package com.austinv11.peripheralsplusplus.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemBlockTurtle extends BlockItem {

private final Random rng = new Random();

public ItemBlockTurtle(Block block, Item.Properties props) {
super(block, props);
}

@Override
public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
int desc = 0;
if (item.hasTag() && item.getTag().contains("desc")) {
desc = item.getTag().getInt("desc");
} else {
desc = rng.nextInt(10) + 1;
item.getOrCreateTag().putInt("desc", desc);
}
tooltip.add(Component.translatable("peripheralsplusone.description.turtle." + desc));
}
}
