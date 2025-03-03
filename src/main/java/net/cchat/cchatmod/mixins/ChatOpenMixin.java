package net.cchat.cchatmod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class ChatOpenMixin {
    @Unique
    private boolean wasSleeping = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        Minecraft mc = Minecraft.getInstance();
        if (player.isSleeping()) {
            if (!wasSleeping && mc.screen == null) {
                mc.setScreen(new ChatScreen(""));
            }
            wasSleeping = true;
        } else {
            if (wasSleeping && mc.screen instanceof ChatScreen) {
                mc.setScreen(null);
            }
            wasSleeping = false;
        }
    }
}
