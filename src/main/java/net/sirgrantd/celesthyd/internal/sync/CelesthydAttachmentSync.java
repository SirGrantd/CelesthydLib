package net.sirgrantd.celesthyd.internal.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.sirgrantd.celesthyd.api.network.ISyncableAttachment;

@EventBusSubscriber
public class CelesthydAttachmentSync {
    private static final List<Supplier<? extends AttachmentType<? extends ISyncableAttachment>>> MANAGED_ATTACHMENTS = new ArrayList<>();

    public static void registerForAutoSync(
            Supplier<? extends AttachmentType<? extends ISyncableAttachment>> attachmentSupplier) {
        MANAGED_ATTACHMENTS.add(attachmentSupplier);
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        syncAll(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncAll(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncAll(event.getEntity());
    }

    private static void syncAll(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            for (Supplier<? extends AttachmentType<? extends ISyncableAttachment>> supplier : MANAGED_ATTACHMENTS) {
                AttachmentType<? extends ISyncableAttachment> attachmentType = supplier.get();
                if (player.hasData(attachmentType)) {
                    player.getData(attachmentType).syncToClient(player);
                }
            }
        }
    }
}