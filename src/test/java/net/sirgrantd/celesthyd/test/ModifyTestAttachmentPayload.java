package net.sirgrantd.celesthyd.test;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.sirgrantd.celesthyd.CelesthydLib;

public record ModifyTestAttachmentPayload(int amount) implements CustomPacketPayload {
    public static final Type<ModifyTestAttachmentPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(CelesthydLib.MOD_ID, "modify_test_attachment"));
            
    public static final StreamCodec<RegistryFriendlyByteBuf, ModifyTestAttachmentPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ModifyTestAttachmentPayload::amount,
            ModifyTestAttachmentPayload::new);

    @Override
    public Type<ModifyTestAttachmentPayload> type() {
        return TYPE;
    }
}