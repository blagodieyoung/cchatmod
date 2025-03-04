package net.cchat.cchatmod.gui.chat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;

import static net.cchat.cchatmod.CChatMod.MOD_ID;

public class CChatModEvents {
    public static final ResourceLocation DEFAULT_ICON = new ResourceLocation(MOD_ID, "textures/gui/default_icon.png");

    private static final int DISPLAY_TIME_TICKS = 200;
    private static final int MAX_HISTORY_SIZE = 50;
    private static final float CHAT_WIDTH_RATIO = 0.4f;
    private static final int Y_OFFSET = -70;

    private final Minecraft minecraft;
    private final Font font;

    private final ChatHistoryManager chatHistoryManager;
    private final ChatRenderer chatRenderer;

    private static CChatModEvents instance;

    public static synchronized CChatModEvents getInstance() {
        if (instance == null) {
            instance = new CChatModEvents(Minecraft.getInstance());
        }
        return instance;
    }

    private CChatModEvents(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.font = minecraft.font;
        this.chatHistoryManager = new ChatHistoryManager(MAX_HISTORY_SIZE, DISPLAY_TIME_TICKS, minecraft);
        this.chatRenderer = new ChatRenderer(minecraft, font, CHAT_WIDTH_RATIO, Y_OFFSET);
    }

    public void addMessage(String text, ResourceLocation icon) {
        chatHistoryManager.addMessage(text, icon != null ? icon : DEFAULT_ICON);
    }

    public void clearChatHistory() {
        chatHistoryManager.clearHistory();
    }

    public void render(PoseStack poseStack, int screenWidth, int screenHeight, boolean isChatOpen) {
        chatRenderer.render(poseStack, screenWidth, screenHeight, isChatOpen, chatHistoryManager);
    }

    public boolean handleMouseScroll(double delta) {
        chatHistoryManager.adjustScrollOffset(delta > 0 ? -1 : 1);
        return true;
    }

    public void adjustScrollOffset(int delta) {
        chatHistoryManager.adjustScrollOffset(delta);
    }
}