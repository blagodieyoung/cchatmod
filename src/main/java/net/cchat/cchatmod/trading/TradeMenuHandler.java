package net.cchat.cchatmod.trading;

import net.minecraft.client.Minecraft;
import java.util.Arrays;

public class TradeMenuHandler {
    public static void newTradeMenu(TradeOffer... offers) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new TradeMenuScreen(Arrays.asList(offers)));
    }
}
