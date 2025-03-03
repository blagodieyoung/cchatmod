package net.cchat.cchatmod.gui.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import java.util.LinkedList;

public class ChatHistoryManager {
    private final int maxHistorySize;
    private final int displayTimeTicks;
    private final Minecraft minecraft;

    private ChatMessage currentMessage = null;
    private long messageEndTick = 0;
    private final LinkedList<ChatMessage> chatHistory = new LinkedList<>();
    private int historyScrollOffset = 0;

    public ChatHistoryManager(int maxHistorySize, int displayTimeTicks, Minecraft minecraft) {
        this.maxHistorySize = maxHistorySize;
        this.displayTimeTicks = displayTimeTicks;
        this.minecraft = minecraft;
    }

    public void addMessage(String text, ResourceLocation icon) {
        long currentTick = getCurrentTick();
        currentMessage = new ChatMessage(text, icon, currentTick + displayTimeTicks);
        messageEndTick = currentTick + displayTimeTicks;
        chatHistory.addLast(currentMessage);

        if (chatHistory.size() > maxHistorySize) {
            chatHistory.removeFirst();
        }
    }

    public void clearHistory() {
        chatHistory.clear();
        historyScrollOffset = 0;
        currentMessage = null;
        messageEndTick = 0;
    }

    public void adjustScrollOffset(int delta) {
        historyScrollOffset = Math.max(0, Math.min(historyScrollOffset + delta, chatHistory.size() - 1));
    }

    public LinkedList<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public int getHistoryScrollOffset() {
        return historyScrollOffset;
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
