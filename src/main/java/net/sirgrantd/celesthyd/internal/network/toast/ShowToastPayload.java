package net.sirgrantd.celesthyd.internal.network.toast;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sirgrantd.celesthyd.CelesthydLib;

import java.util.Optional;

public record ShowToastPayload(Component title, Component subtitle, Optional<ResourceLocation> icon)
        implements CustomPacketPayload {

    public static final Type<ShowToastPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(CelesthydLib.MOD_ID, "show_toast"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ShowToastPayload> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.STREAM_CODEC, ShowToastPayload::title,
            ComponentSerialization.STREAM_CODEC, ShowToastPayload::subtitle,
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs::optional), ShowToastPayload::icon,
            ShowToastPayload::new);

    @Override
    public Type<ShowToastPayload> type() {
        return TYPE;
    }
}