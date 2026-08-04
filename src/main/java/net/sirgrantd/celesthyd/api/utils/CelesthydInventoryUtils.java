package net.sirgrantd.celesthyd.api.utils;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
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

    public static int getAvailableSpaceForItem(Container container, Item item) {
        int availableSpace = 0;
        int defaultMaxStack = item.getDefaultMaxStackSize();

        int limit = (container instanceof Inventory)
                ? 36
                : container.getContainerSize();

        for (int i = 0; i < limit; i++) {
            ItemStack stack = container.getItem(i);

            if (stack.isEmpty()) {
                availableSpace += defaultMaxStack;
            } else if (stack.getItem() == item) {
                availableSpace += (stack.getMaxStackSize() - stack.getCount());
            }
        }

        return availableSpace;
    }

    public static void addItemsFromInventory(Player player, Item item, int quantity) {
        ItemStack stackToAdd = new ItemStack(item, quantity);
        player.getInventory().placeItemBackInInventory(stackToAdd);
    }

    public static void removeItemsFromInventory(Player player, Item item, int quantity) {
        player.getInventory().clearOrCountMatchingItems(
                stack -> stack.getItem() == item,
                quantity,
                player.inventoryMenu.getCraftSlots());
    }
}
