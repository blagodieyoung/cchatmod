package net.cchat.cchatmod.events;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.cchat.cchatmod.gui.screens.CustomChatScreen;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;

public class ClientTickHandler {
    private boolean isChatScreenReplaced = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean isChatOpen = minecraft.screen instanceof net.minecraft.client.gui.screens.ChatScreen;
        if (event.phase == TickEvent.Phase.END) {
            if (isChatOpen && !isChatScreenReplaced && (minecraft.player == null || !minecraft.player.isSleeping())) {
                minecraft.setScreen(new CustomChatScreen("", CChatModEvents.getInstance()));
                isChatScreenReplaced = true;
            } else if (!isChatOpen) {
                isChatScreenReplaced = false;
            }
        }
    }
}