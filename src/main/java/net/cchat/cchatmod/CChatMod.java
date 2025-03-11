package net.cchat.cchatmod;

import net.cchat.cchatmod.capability.ICurrency;
import net.cchat.cchatmod.core.ModKeyBindings;
import net.cchat.cchatmod.events.*;
import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.cchat.cchatmod.core.LogInterceptor;
import net.cchat.cchatmod.data.tasks.TaskManager;
import net.cchat.cchatmod.network.CurrencySyncPacket;
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
    public static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> Integer.toString(PROTOCOL_VERSION),
            s -> s.equals(Integer.toString(PROTOCOL_VERSION)),
            s -> s.equals(Integer.toString(PROTOCOL_VERSION))
    );
    static {
        CHANNEL.registerMessage(0, CurrencySyncPacket.class, CurrencySyncPacket::encode, CurrencySyncPacket::new, CurrencySyncPacket::handle);
    }
    public static final Capability<ICurrency> CURRENCY_CAPABILITY = CapabilityManager.get(new CapabilityToken<ICurrency>() {});

    public CChatMod() {
        LogInterceptor.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(new ChatMessageHandler());
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGuiOverlayHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerJoinHandler());

        TASK_MANAGER.loadTasks();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModKeyBindings::registerKeys);
        CChatModEvents.getInstance();
    }

    public static net.minecraft.client.player.LocalPlayer getClientPlayer() {
        return net.minecraft.client.Minecraft.getInstance().player;
    }
}
