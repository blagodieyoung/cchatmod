package net.cchat.cchatmod.api;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraft.resources.ResourceLocation;

public class CChatAPI {
    public static void sendMessage(String text, ResourceLocation icon) {
        CChatModEvents.getInstance().addMessage(text, icon);
    }
}
