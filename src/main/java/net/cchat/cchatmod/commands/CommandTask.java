package net.cchat.cchatmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.cchat.cchatmod.gui.chat.CChatModEvents;
import net.cchat.cchatmod.network.CurrencySyncPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import static net.cchat.cchatmod.CChatMod.CHANNEL;
import static net.cchat.cchatmod.CChatMod.CURRENCY_CAPABILITY;

public class CommandTask {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("clearchat")
                        .executes(context -> {
                            CChatModEvents.getInstance().clearChatHistory();
                            return 1;
                        })
        );
    }
}
