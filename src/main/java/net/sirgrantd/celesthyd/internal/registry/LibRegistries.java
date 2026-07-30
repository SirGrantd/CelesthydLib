package net.sirgrantd.celesthyd.internal.registry;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sirgrantd.celesthyd.CelesthydLib;
import net.sirgrantd.celesthyd.internal.loot.CelesthydLootModifier;
import net.sirgrantd.celesthyd.internal.network.CelesthydNetworkRegistry;
import net.sirgrantd.celesthyd.internal.network.CelesthydPayloadHandler;
import net.sirgrantd.celesthyd.internal.network.toast.ShowToastPayload;
import net.sirgrantd.celesthyd.internal.network.toast.ToastHelper;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID)
public class LibRegistries {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister
            .create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CelesthydLib.MOD_ID);

    static {
        LOOT_MODIFIERS.register("add_item", () -> CelesthydLootModifier.CODEC);
    }

    @SubscribeEvent
    public static void registerLibPayloads(RegisterPayloadHandlersEvent event) {
        CelesthydNetworkRegistry registry = new CelesthydNetworkRegistry(event, CelesthydLib.MOD_ID);

        registry.registerClientBound(
                ShowToastPayload.TYPE,
                ShowToastPayload.STREAM_CODEC,
                (payload, context) -> CelesthydPayloadHandler.handleClientBound(payload, context, (p, ctx) -> {
                    // Delegamos para a classe segura!
                    ToastHelper.display(p);
                }));
    }
}