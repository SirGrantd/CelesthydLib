package net.sirgrantd.celesthyd.gui;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.sirgrantd.celesthyd.CelesthydLib;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID) // Sem o "bus", usando o padrão moderno!
public class TestGuiRegistries {

        // Criamos o tipo do menu de forma direta e estática
        public static final MenuType<TestGuiMenu> TEST_MENU_TYPE = IMenuTypeExtension
                        .create((containerId, inventory, buf) -> new TestGuiMenu(containerId, inventory));

        // O EventBusSubscriber vai rodar isso aqui automaticamente sem precisar da
        // classe principal
        @SubscribeEvent
        public static void onRegisterMenus(RegisterEvent event) {
                event.register(Registries.MENU, helper -> {
                        helper.register(ResourceLocation.fromNamespaceAndPath(CelesthydLib.MOD_ID, "test_menu"),
                                        TEST_MENU_TYPE);
                });
        }
}
