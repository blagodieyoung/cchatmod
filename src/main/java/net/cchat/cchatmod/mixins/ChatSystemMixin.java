package net.cchat.cchatmod.mixins;

import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.cchat.cchatmod.CChatMod.MOD_ID;

@Mixin(ChatComponent.class)
public class ChatSystemMixin {
    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;)V", at = @At("HEAD"), cancellable = true)
    private void onAddMessage(Component message, CallbackInfo ci) {
        Minecraft.getInstance().execute(() -> {
            CChatModEvents.getInstance().addMessage(message.getString(), new ResourceLocation(MOD_ID, "textures/gui/system_icon.png"));
        });
    }
}