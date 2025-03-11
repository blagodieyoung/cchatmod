package net.cchat.cchatmod.gui.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.LinkedList;

public class ChatHistoryManager {
    private final int maxHistorySize;
    private final int displayTimeTicks;
    private final Minecraft minecraft;

    private ChatMessage currentMessage = null;
    private long messageEndTick = 0;
    private final LinkedList<ChatMessage> chatHistory = new LinkedList<>();
    private int scrollPixelOffset = 0;
    private static final int SCROLL_SPEED = 10;
    private long currentTick = 0;

    public ChatHistoryManager(int maxHistorySize, int displayTimeTicks, Minecraft minecraft) {
        this.maxHistorySize = maxHistorySize;
        this.displayTimeTicks = displayTimeTicks;
        this.minecraft = minecraft;
    }

    public synchronized void addMessage(Component text, ResourceLocation icon) {
        long currentTick = getCurrentTick();
        currentMessage = new ChatMessage(text, icon, currentTick + displayTimeTicks);
        messageEndTick = currentTick + displayTimeTicks;
        chatHistory.addLast(currentMessage);

        if (chatHistory.size() > maxHistorySize) {
            chatHistory.removeFirst();
        }
    }

    public synchronized void clearHistory() {
        chatHistory.clear();
        scrollPixelOffset = 0;
        currentMessage = null;
        messageEndTick = 0;
    }

    public synchronized void adjustScrollOffset(int delta) {
        scrollPixelOffset += delta * SCROLL_SPEED;
    }

    public synchronized LinkedList<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public int getScrollPixelOffset() {
        return scrollPixelOffset;
    }

    public synchronized void setScrollPixelOffset(int offset) {
        this.scrollPixelOffset = offset;
    }

    public ChatMessage getCurrentMessage() {
        return currentMessage;
    }

    public long getMessageEndTick() {
        return messageEndTick;
    }

    public long getCurrentTick() {
        return minecraft.level != null ? minecraft.level.getGameTime() : 0;
    }

    public static class ChatMessage {
        private final Component text;
        private final ResourceLocation icon;

        public ChatMessage(Component text, ResourceLocation icon, long endTick) {
            this.text = text;
            this.icon = icon;
        }

        public Component getText() {
            return text;
        }

        public ResourceLocation getIcon() {
            return icon;
        }
    }
}
