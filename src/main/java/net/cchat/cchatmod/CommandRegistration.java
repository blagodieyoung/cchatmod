package net.cchat.cchatmod;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CChatMod.MOD_ID)
public class CommandRegistration {
    @SubscribeEvent
    public static void onCommandRegistration(RegisterCommandsEvent event) {
        CChatCommand.register(event.getDispatcher());
    }
}
