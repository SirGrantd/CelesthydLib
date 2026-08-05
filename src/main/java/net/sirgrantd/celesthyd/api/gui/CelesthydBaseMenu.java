package net.sirgrantd.celesthyd.api.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sirgrantd.celesthyd.internal.network.gui.SyncMenuDataPayload;

public abstract class CelesthydBaseMenu extends AbstractContainerMenu {

    private final Map<String, Integer> menuData = new HashMap<>();

    protected CelesthydBaseMenu(MenuType<?> type, int containerId) {
        super(type, containerId);
    }

    public void updateDataFromNetwork(String key, int value) {
        this.menuData.put(key, value);
    }

    public int getData(String key) {
        return this.menuData.getOrDefault(key, 0);
    }

    public void setData(String key, int value, Player player) {
        this.menuData.put(key, value);

        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new SyncMenuDataPayload(this.containerId, key, value));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
