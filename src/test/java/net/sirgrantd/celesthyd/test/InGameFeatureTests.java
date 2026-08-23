package net.sirgrantd.celesthyd.test;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.sirgrantd.celesthyd.CelesthydLib;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.celesthyd.gui.TestGuiMenu;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID)
public class InGameFeatureTests {

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        // Assegura que o teste parta apenas da lógica do servidor e na mão principal
        if (!player.level().isClientSide() && event.getHand() == InteractionHand.MAIN_HAND) {

            if (player.getItemInHand(event.getHand()).is(Items.REDSTONE)) {
                ServerPlayer serverPlayer = (ServerPlayer) player;

                // ==========================================
                // TESTE 3: GUI GENÉRICA (Sincronização)
                // ==========================================

                // 1. O servidor abre o menu para o jogador
                serverPlayer.openMenu(new SimpleMenuProvider(
                        (containerId, inv, p) -> new TestGuiMenu(containerId, inv),
                        Component.literal("Menu de Teste")));

                // 2. O servidor injeta os dados no menu aberto.
                // A CelesthydBaseMenu vai enviar o Payload automaticamente para a Screen!
                if (serverPlayer.containerMenu instanceof TestGuiMenu menu) {
                    menu.setData("energia", 850, serverPlayer);
                }
            }

            // Verifica se o jogador está segurando um graveto
            if (player.getItemInHand(event.getHand()).is(Items.STICK)) {
                ServerPlayer serverPlayer = (ServerPlayer) player;

                // ==========================================
                // TESTE 1: API DE INVENTÁRIO (Contar e Remover)
                // ==========================================
                // Conta os diamantes usando o Container padrão do Vanilla
                int diamondCount = CelesthydApi.Inventory.countItems(serverPlayer.getInventory(), Items.DIAMOND);

                if (diamondCount >= 2) {
                    // Remove 2 diamantes
                    CelesthydApi.Inventory.removeItemsFromInventory(serverPlayer, Items.DIAMOND, 2);
                    serverPlayer.sendSystemMessage(Component.literal("§b[Inventário] §aSucesso! Você tinha "
                            + diamondCount + " diamantes e 2 foram consumidos da sua bolsa."));
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("§b[Inventário] §eAviso: Você só tem "
                            + diamondCount + " diamantes. Pegue pelo menos 2 para testar a função de remoção!"));
                }

                // ==========================================
                // TESTE 2: API DE TICK E TOAST (Rede)
                // ==========================================
                serverPlayer.sendSystemMessage(Component.literal("§7Aguarde 40 ticks para o teste visual..."));

                CelesthydApi.queueServerWork(40, () -> {
                    ResourceLocation icon = ResourceLocation.withDefaultNamespace("textures/item/diamond.png");
                    CelesthydApi.sendToast(
                            serverPlayer,
                            Component.literal("Teste Concluído"),
                            Component.literal("Tudo funcionando perfeitamente!"),
                            icon);
                });
            }
        }
    }
}
