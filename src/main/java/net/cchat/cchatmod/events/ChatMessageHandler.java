package net.cchat.cchatmod.events;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

import static net.cchat.cchatmod.CChatMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ChatMessageHandler {
    @SubscribeEvent
    public void onChatMessage(ServerChatEvent.Submitted event) {
        String username = event.getUsername();
        String message = event.getMessage().getString();
        Minecraft.getInstance().execute(() -> {
            CChatModEvents.getInstance().addMessage("[" + username + "]: " + message, new ResourceLocation(MOD_ID, "textures/gui/default_icon.png"));
        });
    }
}
