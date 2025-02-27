package net.cchat.cchatmod.gui.screens;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraft.client.gui.screens.ChatScreen;

public class CustomChatScreen extends ChatScreen {
    private final CChatModEvents chatModEvents;

    public CustomChatScreen(String p_95579_, CChatModEvents chatModEvents) {
        super(p_95579_);
        this.chatModEvents = chatModEvents;
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta != 0) {
            chatModEvents.adjustScrollOffset((int) delta);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
}