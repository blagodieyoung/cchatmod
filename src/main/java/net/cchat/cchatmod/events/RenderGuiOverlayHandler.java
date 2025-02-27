package net.cchat.cchatmod.events;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraft.client.Minecraft;

public class RenderGuiOverlayHandler {
    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.CHAT_PANEL.type()) {
            event.setCanceled(true);
            Minecraft minecraft = Minecraft.getInstance();
            boolean isChatOpen = minecraft.screen instanceof net.minecraft.client.gui.screens.ChatScreen;
            CChatModEvents.getInstance().render(event.getPoseStack(),
                    minecraft.getWindow().getGuiScaledWidth(),
                    minecraft.getWindow().getGuiScaledHeight(), isChatOpen);
        }
    }
}