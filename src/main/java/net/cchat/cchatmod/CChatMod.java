package net.cchat.cchatmod;

import net.cchat.cchatmod.events.ChatMessageHandler;
import net.cchat.cchatmod.events.ClientTickHandler;
import net.cchat.cchatmod.events.KeyInputHandler;
import net.cchat.cchatmod.events.RenderGuiOverlayHandler;
import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.mojang.logging.LogUtils;
import net.cchat.cchatmod.core.LogInterceptor;
import net.cchat.cchatmod.core.ModKeyBindings;
import net.cchat.cchatmod.data.tasks.TaskManager;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CChatMod.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class CChatMod {
    public static final String MOD_ID = "cchatmod";
    public static final TaskManager TASK_MANAGER = new TaskManager();

    public CChatMod() {
        LogInterceptor.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(new ChatMessageHandler());
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGuiOverlayHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());

        TASK_MANAGER.loadTasks();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        CChatModEvents.getInstance();
    }
}