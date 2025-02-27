package net.cchat.cchatmod.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AnimatedButton extends Button {
    private float hoverProgress = 0.0f;
    private static final float ANIMATION_SPEED = 0.1f;

    private final int baseColor;
    private final int hoverColor;

    private boolean active = false;

    public AnimatedButton(int x, int y, int width, int height, Component title, OnPress onPress,
                          int baseColor, int hoverColor) {
        super(x, y, width, height, title, onPress);
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        boolean hovered = this.isHoveredOrFocused();
        if (active) {
            hoverProgress = 1.0f;
        } else {
            if (hovered) {
                hoverProgress = Math.min(1.0f, hoverProgress + ANIMATION_SPEED);
            } else {
                hoverProgress = Math.max(0.0f, hoverProgress - ANIMATION_SPEED);
            }
        }
        int currentColor = blendColors(baseColor, hoverColor, hoverProgress);
        Screen.fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, currentColor);
        drawRectOutline(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0xFFCCCCCC);
        Screen.drawCenteredString(poseStack, Minecraft.getInstance().font, this.getMessage(),
                this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFFFF);
    }

    private int blendColors(int colorA, int colorB, float t) {
        int aA = (colorA >> 24) & 0xFF;
        int rA = (colorA >> 16) & 0xFF;
        int gA = (colorA >> 8) & 0xFF;
        int bA = colorA & 0xFF;
        int aB = (colorB >> 24) & 0xFF;
        int rB = (colorB >> 16) & 0xFF;
        int gB = (colorB >> 8) & 0xFF;
        int bB = colorB & 0xFF;
        int a = (int)(aA + (aB - aA) * t);
        int r = (int)(rA + (rB - rA) * t);
        int g = (int)(gA + (gB - gA) * t);
        int b = (int)(bA + (bB - bA) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private void drawRectOutline(PoseStack poseStack, int x1, int y1, int x2, int y2, int color) {
        Screen.fill(poseStack, x1, y1, x2, y1 + 1, color);
        Screen.fill(poseStack, x1, y2 - 1, x2, y2, color);
        Screen.fill(poseStack, x1, y1, x1 + 1, y2, color);
        Screen.fill(poseStack, x2 - 1, y1, x2, y2, color);
    }
}
