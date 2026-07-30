package net.sirgrantd.celesthyd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.sirgrantd.celesthyd.internal.registry.LibRegistries;

@Mod("celesthyd")
public class CelesthydLib {
    public static final Logger LOGGER = LogManager.getLogger(CelesthydLib.class);
    public static final String MOD_ID = "celesthyd";

    public CelesthydLib(IEventBus eventBus, ModContainer modContainer) {
        LibRegistries.LOOT_MODIFIERS.register(eventBus);
    }
}
