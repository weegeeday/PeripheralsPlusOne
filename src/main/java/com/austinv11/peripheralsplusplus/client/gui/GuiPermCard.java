package com.austinv11.peripheralsplusplus.client.gui;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * GUI for viewing/configuring a permissions card.
 */
public class GuiPermCard extends Screen {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("peripheralsplusone", "textures/gui/perm_card.png");
    private static final int IMAGE_WIDTH = 176;
    private static final int IMAGE_HEIGHT = 120;

    private final ItemStack card;
    private int x;
    private int y;

    public GuiPermCard(ItemStack card) {
        super(Component.translatable("gui.peripheralsplusone.perm_card"));
        this.card = card;
    }

    @Override
    protected void init() {
        super.init();
        this.x = (this.width - IMAGE_WIDTH) / 2;
        this.y = (this.height - IMAGE_HEIGHT) / 2;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        graphics.blit(TEXTURE, x, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

        // Show the owner name if set
        if (card.hasTag() && card.getTag().contains("profile")) {
            GameProfile profile = NbtUtils.readGameProfile(card.getTag().getCompound("profile"));
            if (profile != null && profile.getName() != null) {
                graphics.drawString(this.font,
                        Component.translatable("peripheralsplusone.gui.perm_card.owner", profile.getName()),
                        x + 8, y + 20, 0x404040, false);
            }
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

