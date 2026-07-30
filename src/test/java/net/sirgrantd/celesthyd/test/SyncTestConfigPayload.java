package net.sirgrantd.celesthyd.test;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.sirgrantd.celesthyd.CelesthydLib;

public record SyncTestConfigPayload(String configData) implements CustomPacketPayload {
    public static final Type<SyncTestConfigPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(CelesthydLib.MOD_ID, "sync_test_config"));
            
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTestConfigPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SyncTestConfigPayload::configData,
            SyncTestConfigPayload::new);

    @Override
    public Type<SyncTestConfigPayload> type() {
        return TYPE;
    }
}