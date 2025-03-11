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
        dispatcher.register(
                Commands.literal("currency")
                        .then(Commands.literal("add")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            int amount = IntegerArgumentType.getInteger(context, "amount");
                                            CommandSourceStack source = context.getSource();
                                            if (source.getEntity() instanceof ServerPlayer player) {
                                                player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                                                    currency.addCurrency(amount);
                                                    player.sendSystemMessage(Component.literal(ChatFormatting.GREEN + "Вам добавлено " + amount + " валюты. Текущий баланс: " + currency.getCurrency()));
                                                    CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new CurrencySyncPacket(currency.getCurrency()));
                                                });
                                            }
                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("remove")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            int amount = IntegerArgumentType.getInteger(context, "amount");
                                            CommandSourceStack source = context.getSource();
                                            if (source.getEntity() instanceof ServerPlayer player) {
                                                player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                                                    currency.subtractCurrency(amount);
                                                    player.sendSystemMessage(Component.literal(ChatFormatting.RED + "С вас списано " + amount + " валюты. Текущий баланс: " + currency.getCurrency()));
                                                    CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new CurrencySyncPacket(currency.getCurrency()));
                                                });
                                            }
                                            return 1;
                                        })
                                )
                                .then(Commands.literal("all")  // Добавляем вариант "/currency remove all"
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            if (source.getEntity() instanceof ServerPlayer player) {
                                                player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                                                    currency.setCurrency(0);
                                                    player.sendSystemMessage(Component.literal(ChatFormatting.RED + "Ваш баланс обнулён."));
                                                    CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new CurrencySyncPacket(0));
                                                });
                                            }
                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("set")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            int amount = IntegerArgumentType.getInteger(context, "amount");
                                            CommandSourceStack source = context.getSource();
                                            if (source.getEntity() instanceof ServerPlayer player) {
                                                player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                                                    currency.setCurrency(amount);
                                                    player.sendSystemMessage(Component.literal(ChatFormatting.AQUA + "Ваш баланс установлен на " + amount + " валюты."));
                                                    CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new CurrencySyncPacket(currency.getCurrency()));
                                                });
                                            }
                                            return 1;
                                        })
                                )
                        )
        );
    }
}
