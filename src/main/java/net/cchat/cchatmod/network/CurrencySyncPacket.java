package net.cchat.cchatmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

import static net.cchat.cchatmod.CChatMod.CURRENCY_CAPABILITY;

public class CurrencySyncPacket {
    private final int currency;
    public CurrencySyncPacket(int currency) {
        this.currency = currency;
    }
    public CurrencySyncPacket(FriendlyByteBuf buf) {
        this.currency = buf.readInt();
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.currency);
    }
    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (net.minecraft.client.Minecraft.getInstance().player != null) {
                net.minecraft.client.Minecraft.getInstance().player.getCapability(CURRENCY_CAPABILITY).ifPresent(cap -> {
                    cap.setCurrency(this.currency);
                });
            }
        });
        return true;
    }
}