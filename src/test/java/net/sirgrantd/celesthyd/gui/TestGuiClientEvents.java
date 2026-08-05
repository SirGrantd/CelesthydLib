package net.sirgrantd.celesthyd.gui;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.sirgrantd.celesthyd.CelesthydLib;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID, value = Dist.CLIENT)
public class TestGuiClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(TestGuiRegistries.TEST_MENU_TYPE, TestGuiScreen::new);
    }
}
