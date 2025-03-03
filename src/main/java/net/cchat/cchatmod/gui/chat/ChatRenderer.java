package net.cchat.cchatmod.gui.chat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cchat.cchatmod.gui.chat.ChatHistoryManager.ChatMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import static net.cchat.cchatmod.CChatMod.MOD_ID;

import java.util.LinkedList;
import java.util.List;

public class ChatRenderer {
    private final Minecraft minecraft;
    private final Font font;
    private final float chatWidthRatio;
    private final int yOffset;

    public ChatRenderer(Minecraft minecraft, Font font, float chatWidthRatio, int yOffset) {
        this.minecraft = minecraft;
        this.font = font;
        this.chatWidthRatio = chatWidthRatio;
        this.yOffset = yOffset;
    }

    public void render(PoseStack poseStack, int screenWidth, int screenHeight, boolean isChatOpen, ChatHistoryManager chatHistoryManager) {
        if (isChatOpen) {
            poseStack.pushPose();
            ChatBackgroundRenderer.drawBackground(poseStack, screenWidth, screenHeight);
            poseStack.popPose();
        }

        poseStack.pushPose();
        poseStack.translate(0, 0, 100);
        long currentTick = chatHistoryManager.getCurrentTick();
        int maxWidth = (int) (screenWidth * chatWidthRatio);
        if (isChatOpen) {
            renderChatHistory(poseStack, screenWidth, screenHeight, maxWidth, chatHistoryManager);
        } else if (chatHistoryManager.getCurrentMessage() != null && currentTick < chatHistoryManager.getMessageEndTick()) {
            renderMessage(poseStack, chatHistoryManager.getCurrentMessage(), screenWidth, screenHeight + yOffset, maxWidth);
        }
        poseStack.popPose();
    }

    private void renderChatHistory(PoseStack poseStack, int screenWidth, int screenHeight, int maxWidth, ChatHistoryManager chatHistoryManager) {
        int topBoundary = (int) (screenHeight * 0.12f);
        int yPosition = screenHeight + yOffset;
        int maxVisibleMessages = screenHeight / 40;
        int scrollOffset = chatHistoryManager.getHistoryScrollOffset();
        LinkedList<ChatMessage> history = chatHistoryManager.getChatHistory();

        for (int i = history.size() - 1 - scrollOffset; i >= 0 && maxVisibleMessages > 0; i--, maxVisibleMessages--) {
            ChatMessage message = history.get(i);
            renderMessage(poseStack, message, screenWidth, yPosition, maxWidth);
            yPosition -= calculateMessageHeight(message, maxWidth) + 10;
            if (yPosition - calculateMessageHeight(message, maxWidth) < topBoundary) break;
        }
    }

    private void renderMessage(PoseStack poseStack, ChatMessage message, int screenWidth, int yPosition, int maxWidth) {
        List<FormattedCharSequence> lines = font.split(Component.literal(message.getText()), maxWidth);
        int panelWidth = calculatePanelWidth(lines);
        int panelHeight = calculatePanelHeight(lines);
        int xPosition = (screenWidth - panelWidth) / 2;

        drawBackground(poseStack, xPosition, yPosition, panelWidth, panelHeight);
        drawIcon(poseStack, message.getIcon(), xPosition, yPosition, panelHeight);
        drawText(poseStack, lines, xPosition, yPosition, panelHeight);
    }

    private void drawBackground(PoseStack poseStack, int xPosition, int yPosition, int width, int height) {
        GuiComponent.fill(poseStack, xPosition - 2, yPosition - height - 2, xPosition + width + 2, yPosition + 2, 0x80000000);
    }

    private void drawIcon(PoseStack poseStack, ResourceLocation icon, int xPosition, int yPosition, int panelHeight) {
        int iconX = xPosition + 5;
        int iconY = yPosition - panelHeight + (panelHeight - 16) / 2;
        RenderSystem.setShaderTexture(0, icon);
        GuiComponent.blit(poseStack, iconX, iconY, 0, 0, 16, 16, 16, 16);
    }

    private void drawText(PoseStack poseStack, List<FormattedCharSequence> lines, int xPosition, int yPosition, int panelHeight) {
        int textX = xPosition + 27;
        int totalTextHeight = lines.size() * 12;
        int textY = yPosition - panelHeight + (panelHeight - totalTextHeight) / 2 + 2;
        for (FormattedCharSequence line : lines) {
            font.draw(poseStack, line, textX, textY, 0xFFFFFF);
            textY += 12;
        }
    }

    private int calculatePanelWidth(List<FormattedCharSequence> lines) {
        return lines.stream().mapToInt(font::width).max().orElse(0) + 30;
    }

    private int calculatePanelHeight(List<FormattedCharSequence> lines) {
        return lines.size() * 12 + 10;
    }

    private int calculateMessageHeight(ChatMessage message, int maxWidth) {
        List<FormattedCharSequence> lines = font.split(Component.literal(message.getText()), maxWidth);
        return calculatePanelHeight(lines);
    }
}