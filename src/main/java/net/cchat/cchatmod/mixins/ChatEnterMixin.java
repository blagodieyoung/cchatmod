package net.cchat.cchatmod.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatEnterMixin {
    @Shadow(remap = true)
    protected EditBox input;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int customWidth = 150;
        int customHeight = 22;
        int posX = (screenWidth - customWidth) / 2;
        int posY = screenHeight - 50;

        ci.cancel();
        if (minecraft == null || this.input == null) {
            return;
        }
        this.input.setWidth(customWidth - 10);
        this.input.x = posX + 3;
        this.input.y = posY + 7;
        GuiComponent.fill(poseStack, posX, posY, posX + customWidth, posY + customHeight, 0x70000000);
        this.input.render(poseStack, mouseX, mouseY, partialTicks);
    }
}
