package com.austinv11.peripheralsplusplus.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.austinv11.peripheralsplusplus.items.ItemSmartHelmet;

/**
 * HUD overlay rendered when the player is wearing the Smart Helmet.
 */
public class GuiSmartHelmetOverlay implements LayeredDraw.Layer {

    public static final GuiSmartHelmetOverlay INSTANCE = new GuiSmartHelmetOverlay();

    @Override
    public void render(GuiGraphics graphics, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        Player player = mc.player;
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.getItem() instanceof ItemSmartHelmet) {
                renderHelmetHud(graphics, mc, armor, partialTick);
                break;
            }
        }
    }

    private void renderHelmetHud(GuiGraphics graphics, Minecraft mc, ItemStack helmet, float partialTick) {
        // Placeholder HUD - shows a small indicator that the helmet is active
        if (!helmet.hasTag() || !helmet.getTag().contains("identifier")) return;
        int screenW = mc.getWindow().getGuiScaledWidth();
        graphics.drawString(mc.font, "[\u25A0 PPO]", screenW - 40, 4, 0x00FF00, true);
    }
}

