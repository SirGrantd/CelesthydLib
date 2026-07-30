package net.sirgrantd.celesthyd.internal.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class CelesthydNetworkRegistry {

    private final PayloadRegistrar registrar;

    public CelesthydNetworkRegistry(RegisterPayloadHandlersEvent event, String modId) {
        this.registrar = event.registrar(modId);
    }

    public <T extends CustomPacketPayload> CelesthydNetworkRegistry registerServerBound(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            IPayloadHandler<T> handler) {
        this.registrar.playToServer(type, codec, handler);
        return this;
    }

    public <T extends CustomPacketPayload> CelesthydNetworkRegistry registerClientBound(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            IPayloadHandler<T> handler) {
        this.registrar.playToClient(type, codec, handler);
        return this;
    }

    public <T extends CustomPacketPayload> CelesthydNetworkRegistry registerBidirectional(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            IPayloadHandler<T> handler) {
        this.registrar.playBidirectional(type, codec, handler);
        return this;
    }
}
