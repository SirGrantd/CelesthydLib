package net.sirgrantd.celesthyd.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.sirgrantd.celesthyd.CelesthydLib;
import net.sirgrantd.celesthyd.api.gui.CelesthydImage;
import net.sirgrantd.celesthyd.api.gui.CelesthydText;

@EventBusSubscriber({ Dist.CLIENT })
public class DisplayClient {
    private static final Identifier DUMMY_TEXTURE = Identifier.fromNamespaceAndPath(CelesthydLib.MOD_ID, "textures/gui/sprites/button_highlighted.png");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        boolean isInventory = screen instanceof InventoryScreen;

        if (isInventory) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            String text = "Hello World!";

            CelesthydImage sgImage = new CelesthydImage(gui, 50, -25, DUMMY_TEXTURE);
            sgImage.extractContents(event.getGuiGraphics(), 20, 20);

            CelesthydText sgText = new CelesthydText(gui, 100, -25, text);
            sgText.extractContents(event.getGuiGraphics());
        }
    }
}