package net.sirgrantd.celesthyd.api;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sirgrantd.celesthyd.api.network.ISyncableAttachment;
import net.sirgrantd.celesthyd.internal.core.CelesthydTickManager;
import net.sirgrantd.celesthyd.internal.network.toast.ShowToastPayload;
import net.sirgrantd.celesthyd.internal.sync.CelesthydAttachmentSync;
import net.sirgrantd.celesthyd.internal.sync.CelesthydConfigSync;
import net.sirgrantd.celesthyd.api.utils.CelesthydInventoryUtils;

public final class CelesthydApi {

    private CelesthydApi() {
    }

    public static void queueServerWork(int tickDelay, Runnable action) {
        CelesthydTickManager.queueServerWork(tickDelay, action);
    }

    public static void registerConfigSync(Supplier<CustomPacketPayload> payloadSupplier) {
        CelesthydConfigSync.registerConfigPayload(payloadSupplier);
    }

    public static void registerAutoSyncAttachment(
            Supplier<? extends AttachmentType<? extends ISyncableAttachment>> attachmentSupplier) {
        CelesthydAttachmentSync.registerForAutoSync(attachmentSupplier);
    }

    public static void sendToast(ServerPlayer player, Component title, Component subtitle, ResourceLocation icon) {
        PacketDistributor.sendToPlayer(player, new ShowToastPayload(title, subtitle, Optional.ofNullable(icon)));
    }

    public static final class Inventory {
        private Inventory() {
        }

        public static int countItems(Container container, Item item) {
            return CelesthydInventoryUtils.countItems(container, item);
        }

        public static int getAvailableSpaceForItem(Container container, Item item) {
            return CelesthydInventoryUtils.getAvailableSpaceForItem(container, item);
        }

        public static void addItemsFromInventory(Player player, Item item, int quantity) {
            CelesthydInventoryUtils.addItemsFromInventory(player, item, quantity);
        }

        public static void removeItemsFromInventory(Player player,
                Item item, int quantity) {
            CelesthydInventoryUtils.removeItemsFromInventory(player, item, quantity);
        }
    }
}
