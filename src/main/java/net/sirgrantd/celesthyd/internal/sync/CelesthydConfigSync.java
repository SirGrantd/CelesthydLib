package net.sirgrantd.celesthyd.internal.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class CelesthydConfigSync {

    private static final List<Supplier<CustomPacketPayload>> SYNC_PAYLOADS = new ArrayList<>();

    public static void registerConfigPayload(Supplier<CustomPacketPayload> payloadSupplier) {
        SYNC_PAYLOADS.add(payloadSupplier);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            for (Supplier<CustomPacketPayload> payloadSupplier : SYNC_PAYLOADS) {
                PacketDistributor.sendToPlayer(player, payloadSupplier.get());
            }
        }
    }
}
