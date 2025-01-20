package net.cchat.cchatmod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CChatCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(CChatCommand.class);
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("showmes")
                .then(Commands.argument("path", StringArgumentType.string())
                        .then(Commands.argument("message", StringArgumentType.greedyString())
                                .executes(context -> {
                                    String path = StringArgumentType.getString(context, "path");
                                    String message = StringArgumentType.getString(context, "message");
                                    ResourceLocation iconPath = new ResourceLocation("storytime" + "/textures/npc_icons/", path);
                                    CChatModEvents eventsHandler = CChatModEvents.getInstance();
                                    eventsHandler.addMessage(message, iconPath);
                                    return 1;
                                })
                        )
                )
        );
    }
}
