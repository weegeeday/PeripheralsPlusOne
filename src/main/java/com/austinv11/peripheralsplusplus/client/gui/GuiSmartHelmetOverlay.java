package com.austinv11.peripheralsplusplus.client.gui;

import com.austinv11.peripheralsplusplus.items.ItemSmartHelmet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

/**
 * HUD overlay rendered when the player is wearing the Smart Helmet.
 * Registered via RegisterGuiOverlaysEvent in PeripheralsPlusPlus.
 */
public class GuiSmartHelmetOverlay implements IGuiOverlay {

    public static final GuiSmartHelmetOverlay INSTANCE = new GuiSmartHelmetOverlay();

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        Player player = mc.player;
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.getItem() instanceof ItemSmartHelmet) {
                renderHelmetHud(graphics, mc, armor, screenWidth);
                break;
            }
        }
    }

    private void renderHelmetHud(GuiGraphics graphics, Minecraft mc, ItemStack helmet, int screenWidth) {
        // Show a small indicator when the helmet is active (has a linked antenna)
        if (!helmet.hasTag() || !helmet.getTag().contains("identifier")) return;
        graphics.drawString(mc.font, "[\u25A0 PPO]", screenWidth - 40, 4, 0x00FF00, true);
    }
}

