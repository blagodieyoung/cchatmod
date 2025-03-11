package net.cchat.cchatmod;

import net.cchat.cchatmod.core.ModKeyBindings;
import net.cchat.cchatmod.events.*;
import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.cchat.cchatmod.core.LogInterceptor;
import net.cchat.cchatmod.data.tasks.TaskManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModKeyBindings::registerKeys);
        CChatModEvents.getInstance();
    }
}
