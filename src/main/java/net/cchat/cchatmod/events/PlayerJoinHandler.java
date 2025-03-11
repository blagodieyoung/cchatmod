package net.cchat.cchatmod.events;

import net.cchat.cchatmod.network.CurrencySyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import static net.cchat.cchatmod.CChatMod.*;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerJoinHandler {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new CurrencySyncPacket(currency.getCurrency()));
            });
        }
    }
}
