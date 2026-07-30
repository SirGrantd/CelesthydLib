package net.sirgrantd.celesthyd.gui;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.sirgrantd.celesthyd.CelesthydLib;
import net.sirgrantd.celesthyd.api.gui.CelesthydButtonAction;
import net.sirgrantd.celesthyd.api.gui.CelesthydButton;
import net.sirgrantd.celesthyd.test.ModifyTestAttachmentPayload;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    private static final WidgetSprites DUMMY_SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(CelesthydLib.MOD_ID, "button"),
            Identifier.fromNamespaceAndPath(CelesthydLib.MOD_ID, "button_highlighted"));

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {

            // Botão Esquerdo envia pacote com "+1"
            CelesthydButtonAction leftClickAction = (x, y, z) -> new ModifyTestAttachmentPayload(1);
            
            // Botão Direito envia pacote com "-1"
            CelesthydButtonAction rightClickAction = (x, y, z) -> new ModifyTestAttachmentPayload(-1);

            CelesthydButton testButton = new CelesthydButton(
                    inventoryScreen,
                    5, -25,
                    inventoryScreen.getLeftPos(),
                    inventoryScreen.getTopPos(),
                    20, 20,
                    DUMMY_SPRITES,
                    leftClickAction,
                    rightClickAction,
                    () -> SoundEvents.UI_BUTTON_CLICK.value(),
                    Component.literal("Esq=+1 | Dir=-1 (Testar Attachment)"));

            event.addListener(testButton);
        }
    }
}