package net.cchat.cchatmod.trading;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.cchat.cchatmod.capability.ICurrency;

import java.util.List;

import static net.cchat.cchatmod.CChatMod.CURRENCY_CAPABILITY;


public class TradeMenuScreen extends Screen {
    private final List<TradeOffer> offers;

    protected TradeMenuScreen(List<TradeOffer> offers) {
        super(Component.literal("Trade Menu"));
        this.offers = offers;
    }

    @Override
    protected void init() {
        int buttonWidth = 150;
        int buttonHeight = 20;
        int startY = this.height / 4;
        int padding = 5;
        int index = 0;
        for (TradeOffer offer : offers) {
            int yPos = startY + index * (buttonHeight + padding);
            addRenderableWidget(new TradeOfferButton(this.width / 2 - buttonWidth / 2, yPos, buttonWidth, buttonHeight, offer, button -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.getCapability(CURRENCY_CAPABILITY).ifPresent(currency -> {
                        if (currency.getCurrency() >= offer.getPrice()) {
                            currency.subtractCurrency(offer.getPrice());
                            if (!mc.player.getInventory().add(offer.getItem().copy())) {
                                mc.player.drop(offer.getItem().copy(), false);
                            }
                        } else {
                            mc.player.displayClientMessage(Component.literal("Недостаточно валюты!"), true);
                        }
                    });
                }
            }));
            index++;
        }
        addRenderableWidget(new Button(this.width / 2 - buttonWidth / 2, startY + index * (buttonHeight + padding) + 10, buttonWidth, buttonHeight, Component.literal("Закрыть"), button -> {
            Minecraft.getInstance().setScreen(null);
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }
}
