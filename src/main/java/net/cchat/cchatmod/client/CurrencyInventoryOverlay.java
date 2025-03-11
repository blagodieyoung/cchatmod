package net.cchat.cchatmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.cchat.cchatmod.CChatMod.CURRENCY_CAPABILITY;
import static net.cchat.cchatmod.CChatMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class CurrencyInventoryOverlay {
    private static final ResourceLocation CURRENCY_ICON = new ResourceLocation(MOD_ID, "textures/gui/system_icon.png");
    private static final ResourceLocation CUSTOM_BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/chat_background_up2.png");
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof InventoryScreen)) {
            return;
        }

        int guiWidth = 176;
        int guiHeight = 166;
        int left = (mc.getWindow().getGuiScaledWidth() - guiWidth) / 2;
        int top = (mc.getWindow().getGuiScaledHeight() - guiHeight) / 2;

        int x = left + 10;
        int y = top - 14;

        PoseStack poseStack = event.getPoseStack();

        RenderSystem.setShaderTexture(0, CUSTOM_BACKGROUND);
        GuiComponent.blit(poseStack, x - 4, y - 4, 0, 0, 120, 20, 120, 20);
        RenderSystem.setShaderTexture(0, CURRENCY_ICON);
        GuiComponent.blit(poseStack, x, y - 4, 0, 0, 16, 16, 16, 16);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        if (mc.player != null) {
            mc.player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                String text = "Баланс: " + currency.getCurrency();
                mc.font.draw(poseStack, text, x + 20, y, -1);
            });
        }
    }
}
