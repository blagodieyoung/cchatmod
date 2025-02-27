package net.cchat.cchatmod.gui.chat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import java.util.LinkedList;
import java.util.List;

import static net.cchat.cchatmod.CChatMod.MOD_ID;

public class CChatModEvents {
    public static final ResourceLocation DEFAULT_ICON = new ResourceLocation(MOD_ID, "textures/gui/default_icon.png");
    private static final int DISPLAY_TIME_TICKS = 200;
    private static final int MAX_HISTORY_SIZE = 50;
    private static final float chatWidthRatio = 0.4f;

    private final Minecraft minecraft;
    private final Font font;
    private ChatMessage currentMessage = null;
    private long messageEndTick = 0;
    private final LinkedList<ChatMessage> chatHistory = new LinkedList<>();
    private int historyScrollOffset = 0;
    private final int yOffset = -70;

    private static CChatModEvents instance;

    public static CChatModEvents getInstance() {
        if (instance == null) {
            instance = new CChatModEvents(Minecraft.getInstance());
        }
        return instance;
    }

    private CChatModEvents(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.font = minecraft.font;
    }

    public void addMessage(String text, ResourceLocation icon) {
        long currentTick = getCurrentTick();
        currentMessage = new ChatMessage(text, icon != null ? icon : DEFAULT_ICON, currentTick + DISPLAY_TIME_TICKS);
        messageEndTick = currentTick + DISPLAY_TIME_TICKS;
        chatHistory.addLast(currentMessage);

        if (chatHistory.size() > MAX_HISTORY_SIZE) {
            chatHistory.removeFirst();
        }
    }

    public void clearChatHistory() {
        chatHistory.clear();
        historyScrollOffset = 0;
        currentMessage = null;
        messageEndTick = 0;
    }

    public void render(PoseStack poseStack, int screenWidth, int screenHeight, boolean isChatOpen) {
        long currentTick = getCurrentTick();
        int maxWidth = (int) (screenWidth * chatWidthRatio);

        if (isChatOpen) {
            renderChatHistory(poseStack, screenWidth, screenHeight, maxWidth);
        } else if (currentMessage != null && currentTick < messageEndTick) {
            renderMessage(poseStack, currentMessage, screenWidth, screenHeight + yOffset, maxWidth);
        }
    }

    public boolean handleMouseScroll(double delta) {
        adjustScrollOffset(delta > 0 ? -1 : 1);
        return true;
    }

    public void adjustScrollOffset(int delta) {
        historyScrollOffset = Math.max(0, Math.min(historyScrollOffset + delta, chatHistory.size() - 1));
    }

    private void renderChatHistory(PoseStack poseStack, int screenWidth, int screenHeight, int maxWidth) {
        int yPosition = screenHeight + yOffset;
        int maxVisibleMessages = screenHeight / 40;

        for (int i = chatHistory.size() - 1 - historyScrollOffset; i >= 0 && maxVisibleMessages > 0; i--, maxVisibleMessages--) {
            ChatMessage message = chatHistory.get(i);
            renderMessage(poseStack, message, screenWidth, yPosition, maxWidth);
            yPosition -= calculateMessageHeight(message, maxWidth) + 10;

            if (yPosition < 0) break;
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

    private long getCurrentTick() {
        return minecraft.level != null ? minecraft.level.getGameTime() : 0;
    }

    private static class ChatMessage {
        private final String text;
        private final ResourceLocation icon;
        private final long endTick;

        public ChatMessage(String text, ResourceLocation icon, long endTick) {
            this.text = text;
            this.icon = icon;
            this.endTick = endTick;
        }

        public String getText() {
            return text;
        }

        public ResourceLocation getIcon() {
            return icon;
        }

        public long getEndTick() {
            return endTick;
        }
    }
}