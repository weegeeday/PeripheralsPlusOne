package com.austinv11.peripheralsplusplus.items;

import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class ItemSmartHelmet extends ArmorItem {

public ItemSmartHelmet(Properties props) {
super(ArmorMaterials.IRON, Type.HELMET, props);
}

@Override
public String getArmorTexture(ItemStack stack, net.minecraft.world.entity.Entity entity,
EquipmentSlot slot, String type) {
return Reference.MOD_ID + ":textures/models/armor/smart_helmet.png";
}
}
