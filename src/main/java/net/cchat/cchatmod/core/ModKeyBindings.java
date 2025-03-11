package net.cchat.cchatmod.core;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModKeyBindings {
    private static final List<KeyMapping> KEY_BINDINGS = new ArrayList<>();

    public static final KeyMapping OPEN_TASK_SCREEN = new KeyMapping(
            "key.cchatmod.opentasks",
            GLFW.GLFW_KEY_M,
            "key.categories.cchatmod.custom"
    );

    private static KeyMapping register(KeyMapping keyMapping) {
        KEY_BINDINGS.add(keyMapping);
        return keyMapping;
    }

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        for (KeyMapping key : KEY_BINDINGS) {
            event.register(key);
        }
    }
}
