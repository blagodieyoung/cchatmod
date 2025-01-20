package net.cchat.cchatmod.mixins;

/*import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;*/

//@Mixin(ChatScreen.class)
public abstract class MixinChatScreen {
    //@Shadow(remap = true)
    /*protected EditBox input; // Поле ввода текста

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int customWidth = 200; // Ширина текстового поля
        int customHeight = 14; // Высота текстового поля
        int posX = (screenWidth - customWidth) / 2; // Центровка по горизонтали
        int posY = screenHeight - 70; // Положение чуть выше инвентаря
        try {
            ci.cancel();
            if (minecraft == null || this.input == null) {
                return; // Проверяем на null, чтобы избежать вылетов
            }
            // Перемещаем поле ввода
            this.input.setWidth(customWidth - 10); // Уменьшаем ширину с учётом отступа
            this.input.x = posX + 3; // Смещаем по горизонтали
            this.input.y = posY + 3; // Смещаем по вертикали

            int backgroundColor = 0xAA000000;
            GuiComponent.fill(poseStack, posX, posY, posX + customWidth, posY + customHeight, backgroundColor);
            this.input.render(poseStack, mouseX, mouseY, partialTicks);
        } catch (Exception e) {
            System.err.println("Error rendering chat screen: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
}