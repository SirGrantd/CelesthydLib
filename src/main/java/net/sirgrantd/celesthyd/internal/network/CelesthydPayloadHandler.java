package net.sirgrantd.celesthyd.internal.network;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CelesthydPayloadHandler {

    @FunctionalInterface
    public interface ServerAction<T extends CustomPacketPayload> {
        void execute(T payload, Player player, IPayloadContext context);
    }

    @FunctionalInterface
    public interface ClientAction<T extends CustomPacketPayload> {
        void execute(T payload, IPayloadContext context);
    }

    public static <T extends CustomPacketPayload> void handleServerBound(
            T payload, IPayloadContext context, ServerAction<T> action) {
        if (context.flow() != PacketFlow.SERVERBOUND)
            return;

        context.enqueueWork(() -> {
            action.execute(payload, context.player(), context);
        }).exceptionally(e -> {
            context.connection().disconnect(Component.literal("Network Error: " + e.getMessage()));
            return null;
        });
    }

    public static <T extends CustomPacketPayload> void handleClientBound(T payload, IPayloadContext context,
            ClientAction<T> action) {
        if (context.flow() != PacketFlow.CLIENTBOUND)
            return;

        context.enqueueWork(() -> {
            action.execute(payload, context);
        }).exceptionally(e -> {
            context.connection().disconnect(Component.literal("Network Error: " + e.getMessage()));
            return null;
        });
    }
}
