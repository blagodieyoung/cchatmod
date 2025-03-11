package net.cchat.cchatmod.trading;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class TradeOfferButton extends Button {
    private final TradeOffer offer;

    public TradeOfferButton(int x, int y, int width, int height, TradeOffer offer, OnPress onPress) {
        super(x, y, width, height, Component.empty(), onPress);
        this.offer = offer;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);

        Font font = Minecraft.getInstance().font;
        String text = offer.getDisplayName().getString() + " - Price: " + offer.getPrice();
        font.draw(poseStack, text, this.x + 22, this.y + (this.height - 8) / 2, 0xFFFFFF);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(offer.getItem(), this.x + 2, this.y + 2);
    }
}
