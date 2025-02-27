package net.cchat.cchatmod.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "cchatmod", value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class NotificationOverlayHandler {
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        PoseStack poseStack = event.getPoseStack();
        Minecraft mc = Minecraft.getInstance();
        NotificationManager.renderNotifications(poseStack, mc.font, event.getPartialTick());
    }
}
