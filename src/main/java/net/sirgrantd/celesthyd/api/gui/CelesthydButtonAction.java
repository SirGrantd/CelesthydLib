package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface CelesthydButtonAction {
    CustomPacketPayload createPacket(int x, int y, int z, boolean isShiftDown);

    default void sendToServer(Player player, boolean isShiftDown) {
        if (player == null)
            return;

        var pos = player.blockPosition();
        CustomPacketPayload payload = this.createPacket(pos.getX(), pos.getY(), pos.getZ(), isShiftDown);

        if (Minecraft.getInstance().getConnection() != null) {
            Minecraft.getInstance().getConnection().send(payload);
        }
    }
}
