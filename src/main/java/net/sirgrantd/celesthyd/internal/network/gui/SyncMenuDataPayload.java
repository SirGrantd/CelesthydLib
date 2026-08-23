package net.sirgrantd.celesthyd.internal.network.gui;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sirgrantd.celesthyd.CelesthydLib;

public record SyncMenuDataPayload(int containerId, String key, int value) implements CustomPacketPayload {

    public static final Type<SyncMenuDataPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(CelesthydLib.MOD_ID, "sync_menu_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMenuDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SyncMenuDataPayload::containerId,
            ByteBufCodecs.STRING_UTF8, SyncMenuDataPayload::key,
            ByteBufCodecs.VAR_INT, SyncMenuDataPayload::value,
            SyncMenuDataPayload::new);

    @Override
    public Type<SyncMenuDataPayload> type() {
        return TYPE;
    }
}
