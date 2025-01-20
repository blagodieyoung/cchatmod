package net.cchat.cchatmod;

import net.minecraft.resources.ResourceLocation;

public class CChatAPI {
    public static void sendMessage(String text, ResourceLocation icon) {
        CChatModEvents.getInstance().addMessage(text, icon);
    }
}
