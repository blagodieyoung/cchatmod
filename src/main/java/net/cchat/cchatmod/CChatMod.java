package net.cchat.cchatmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

@Mod(CChatMod.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class CChatMod {
    public static final String MOD_ID = "cchatmod";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static CChatModEvents customChatRenderer;
    private boolean isChatOpen = false;
    private boolean isChatScreenReplaced = false;
    private static final Minecraft minecraft = Minecraft.getInstance();
    private boolean isF3Pressed = false;
    private boolean isDPressed = false;

    public CChatMod() {
        LogInterceptor.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onChatMessage);
        MinecraftForge.EVENT_BUS.addListener(this::onRenderGuiOverlay);
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(this::onKeyInput);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        customChatRenderer = CChatModEvents.getInstance();
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = event.getMessage().getString();
        customChatRenderer.addMessage(message, new ResourceLocation(MOD_ID, "textures/gui/default_icon.png"));
        event.setCanceled(true);
    }

    private void onClientTick(TickEvent.ClientTickEvent event) {
        isChatOpen = Minecraft.getInstance().screen instanceof net.minecraft.client.gui.screens.ChatScreen;
        if (event.phase == TickEvent.Phase.END) {
            if (Minecraft.getInstance().screen instanceof net.minecraft.client.gui.screens.ChatScreen && !isChatScreenReplaced) {
                Minecraft.getInstance().setScreen(new CustomChatScreen("", customChatRenderer));
                isChatScreenReplaced = true;
            } else if (!(Minecraft.getInstance().screen instanceof net.minecraft.client.gui.screens.ChatScreen)) {
                isChatScreenReplaced = false;
            }
        }
    }

    private void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.CHAT_PANEL.type()) {
            event.setCanceled(true);
            customChatRenderer.render(event.getPoseStack(),
                    Minecraft.getInstance().getWindow().getGuiScaledWidth(),
                    Minecraft.getInstance().getWindow().getGuiScaledHeight(), isChatOpen);
        }
    }

    private void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == GLFW.GLFW_KEY_F3) {
            isF3Pressed = event.getAction() != GLFW.GLFW_RELEASE;
        }
        if (event.getKey() == GLFW.GLFW_KEY_D) {
            isDPressed = event.getAction() != GLFW.GLFW_RELEASE;
        }
        if (isF3Pressed && isDPressed) {
            CChatModEvents.getInstance().clearChatHistory();
        }
    }
}