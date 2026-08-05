package net.sirgrantd.celesthyd.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.gui.CelesthydBaseMenu;

public class TestGuiMenu extends CelesthydBaseMenu {

    public TestGuiMenu(int containerId, Inventory playerInventory) {
        super(TestGuiRegistries.TEST_MENU_TYPE, containerId);
    }

    @Override
    public boolean stillValid(Player player) {
        return true; // O menu pode permanecer aberto
    }
}
