package net.sirgrantd.celesthyd.test;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sirgrantd.celesthyd.CelesthydLib;

public record SyncTestAttachmentPayload(int counter) implements CustomPacketPayload {
    public static final Type<SyncTestAttachmentPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(CelesthydLib.MOD_ID, "sync_test_attachment"));
            
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTestAttachmentPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SyncTestAttachmentPayload::counter,
            SyncTestAttachmentPayload::new);

    @Override
    public Type<SyncTestAttachmentPayload> type() {
        return TYPE;
    }
}