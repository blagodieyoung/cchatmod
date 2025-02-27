package net.cchat.cchatmod.events;

import net.cchat.cchatmod.CChatMod;
import net.cchat.cchatmod.core.ModKeyBindings;
import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.cchat.cchatmod.gui.screens.TaskScreen;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    private boolean isF3Pressed = false;
    private boolean isDPressed = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == GLFW.GLFW_KEY_F3) {
            isF3Pressed = event.getAction() != GLFW.GLFW_RELEASE;
        }
        if (event.getKey() == GLFW.GLFW_KEY_D) {
            isDPressed = event.getAction() != GLFW.GLFW_RELEASE;
        }
        if (isF3Pressed && isDPressed) {
            CChatModEvents.getInstance().clearChatHistory();
        }

        if (ModKeyBindings.OPEN_TASK_SCREEN.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new TaskScreen(CChatMod.TASK_MANAGER));
        }
    }
}