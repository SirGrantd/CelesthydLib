package net.sirgrantd.celesthyd.test;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.sirgrantd.celesthyd.CelesthydLib;
import net.sirgrantd.celesthyd.api.CelesthydApi;

import java.util.function.Supplier;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID)
public class TestRegistries {

    private static final AttachmentType<TestAttachment> ATTACHMENT = AttachmentType.serializable(TestAttachment::new)
            .copyOnDeath().build();

    public static final Supplier<AttachmentType<TestAttachment>> TEST_ATTACHMENT = () -> ATTACHMENT;

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, helper -> {
            helper.register(Identifier.fromNamespaceAndPath(CelesthydLib.MOD_ID, "test_attachment"), ATTACHMENT);
        });
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CelesthydApi.registerConfigSync(() -> new SyncTestConfigPayload("Configuração Dummy Lida do Servidor!"));

            CelesthydApi.registerAutoSyncAttachment(TEST_ATTACHMENT);
        });
    }
}