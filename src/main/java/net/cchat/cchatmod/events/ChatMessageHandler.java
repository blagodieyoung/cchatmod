package net.cchat.cchatmod.events;

import net.cchat.cchatmod.CChatMod;
import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;

public class ChatMessageHandler {
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = event.getMessage().getString();
        Minecraft.getInstance().execute(() -> {
            CChatModEvents.getInstance().addMessage(message, new ResourceLocation(CChatMod.MOD_ID, "textures/gui/default_icon.png"));
        });
    }
}
