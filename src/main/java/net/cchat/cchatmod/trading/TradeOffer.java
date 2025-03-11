package net.cchat.cchatmod.trading;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

public class TradeOffer {
    private final ItemStack item;
    private final int price;
    private final Component displayName;

    public TradeOffer(ItemStack item, int price, Component displayName) {
        this.item = item;
        this.price = price;
        this.displayName = displayName;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public Component getDisplayName() {
        return displayName;
    }
}
