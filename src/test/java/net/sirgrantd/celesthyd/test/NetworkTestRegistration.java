package net.sirgrantd.celesthyd.test;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.sirgrantd.celesthyd.CelesthydLib;

import net.sirgrantd.celesthyd.internal.network.CelesthydNetworkRegistry;
import net.sirgrantd.celesthyd.internal.network.CelesthydPayloadHandler;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID)
public class NetworkTestRegistration {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        CelesthydNetworkRegistry registry = new CelesthydNetworkRegistry(event, CelesthydLib.MOD_ID);

        registry.registerClientBound(
                SyncTestConfigPayload.TYPE,
                SyncTestConfigPayload.STREAM_CODEC,
                (payload, context) -> CelesthydPayloadHandler.handleClientBound(payload, context, (p, ctx) -> {
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player
                                .sendSystemMessage(Component.literal("§e[Config Sync] §f" + p.configData()));
                    }
                }));

        registry.registerClientBound(
                SyncTestAttachmentPayload.TYPE,
                SyncTestAttachmentPayload.STREAM_CODEC,
                (payload, context) -> CelesthydPayloadHandler.handleClientBound(payload, context, (p, ctx) -> {
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.sendSystemMessage(
                                Component.literal(
                                        "§b[Attachment Sync] §fO seu contador no cliente agora é: " + p.counter()));
                    }
                }));

        registry.registerServerBound(
                ModifyTestAttachmentPayload.TYPE,
                ModifyTestAttachmentPayload.STREAM_CODEC,
                (payload, context) -> CelesthydPayloadHandler.handleServerBound(payload, context, (p, player, ctx) -> {
                    if (player instanceof ServerPlayer serverPlayer) {
                        TestAttachment attachment = serverPlayer.getData(TestRegistries.TEST_ATTACHMENT.get());

                        attachment.addCounter(p.amount());
                        serverPlayer.setData(TestRegistries.TEST_ATTACHMENT.get(), attachment);

                        serverPlayer.sendSystemMessage(
                                Component.literal("§a[Servidor] §fVocê " + (p.amount() > 0 ? "adicionou" : "removeu")
                                        + " 1. Total atualizado no servidor: " + attachment.getCounter()));

                        attachment.syncToClient(serverPlayer);
                    }
                }));
    }
}