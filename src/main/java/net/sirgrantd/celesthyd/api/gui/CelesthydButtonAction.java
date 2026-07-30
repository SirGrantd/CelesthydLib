package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface CelesthydButtonAction {
    CustomPacketPayload createPacket(int x, int y, int z);

    default void sendToServer(Player player) {
        if (player == null)
            return;

        var pos = player.blockPosition();
        CustomPacketPayload payload = this.createPacket(pos.getX(), pos.getY(), pos.getZ());

        if (Minecraft.getInstance().getConnection() != null) {
            Minecraft.getInstance().getConnection().send(payload);
        }
    }
}