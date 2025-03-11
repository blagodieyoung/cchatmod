package net.cchat.cchatmod.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import static net.cchat.cchatmod.CChatMod.CURRENCY_CAPABILITY;
import static net.cchat.cchatmod.CChatMod.MOD_ID;

public class CurrencyProvider implements ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(MOD_ID, "currency");

    private final LazyOptional<ICurrency> instance = LazyOptional.of(CurrencyStorage::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CURRENCY_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Currency", instance.orElse(new CurrencyStorage()).getCurrency());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.ifPresent(storage -> storage.setCurrency(nbt.getInt("Currency")));
    }
}