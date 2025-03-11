package net.cchat.cchatmod.events;

import net.cchat.cchatmod.CChatMod;
import net.cchat.cchatmod.capability.CurrencyProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.cchat.cchatmod.CChatMod.CURRENCY_CAPABILITY;
import static net.cchat.cchatmod.CChatMod.MOD_ID;

public class CapabilityEventHandler {
    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(MOD_ID, "currency"), new CurrencyProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(CURRENCY_CAPABILITY).ifPresent(oldCap -> {
            event.getEntity().getCapability(CURRENCY_CAPABILITY).ifPresent(newCap -> {
                newCap.setCurrency(oldCap.getCurrency());
            });
        });
    }
}
