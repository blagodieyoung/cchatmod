package net.cchat.cchatmod.gui.chat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

import static net.cchat.cchatmod.CChatMod.MOD_ID;

public class ChatBackgroundRenderer {
    private static final ResourceLocation CHAT_TOP = new ResourceLocation(MOD_ID, "textures/gui/chat_background_up.png");
    private static final ResourceLocation CHAT_BOTTOM = new ResourceLocation(MOD_ID, "textures/gui/chat_background_down.png");

    public static void drawBackground(PoseStack poseStack, int screenWidth, int screenHeight) {
        double ratio = 0.5;
        int offset = 5;
        int backgroundWidth = (int)(screenWidth * ratio);
        int centerX = (screenWidth - backgroundWidth) / 2;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderTexture(0, CHAT_TOP);
        GuiComponent.blit(poseStack, centerX, 0, 0, 0, backgroundWidth, 20, backgroundWidth, 20);
        RenderSystem.setShaderTexture(0, CHAT_BOTTOM);
        GuiComponent.blit(poseStack, centerX, screenHeight - 20, 0, 0, backgroundWidth, 20, backgroundWidth, 20);

        fillTransparent(poseStack, centerX + offset, 20, centerX + backgroundWidth - offset, screenHeight - 20, 0x70000000);

        RenderSystem.disableBlend();
    }

    private static void fillTransparent(PoseStack poseStack, int x1, int y1, int x2, int y2, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GuiComponent.fill(poseStack, x1, y1, x2, y2, color);
        RenderSystem.disableBlend();
    }
}
