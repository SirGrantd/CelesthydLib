package net.sirgrantd.celesthyd.api.utils;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CelesthydInventoryUtils {

    private CelesthydInventoryUtils() {
    }

    public static int countItems(Container container, Item item) {
        int count = 0;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static void removeItemsFromInventory(Player player, Item item, int quantity) {
        player.getInventory().clearOrCountMatchingItems(
                stack -> stack.getItem() == item,
                quantity,
                player.inventoryMenu.getCraftSlots());
    }
}