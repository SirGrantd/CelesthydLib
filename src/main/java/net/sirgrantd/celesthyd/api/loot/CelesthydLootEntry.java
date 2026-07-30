package net.sirgrantd.celesthyd.api.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public record CelesthydLootEntry(Item item, float chance, int min, int max) {
    public static final MapCodec<CelesthydLootEntry> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(CelesthydLootEntry::item),
            Codec.FLOAT.optionalFieldOf("chance", 1.0f).forGetter(CelesthydLootEntry::chance),
            Codec.INT.optionalFieldOf("min", 1).forGetter(CelesthydLootEntry::min),
            Codec.INT.optionalFieldOf("max", 1).forGetter(CelesthydLootEntry::max))
            .apply(inst, CelesthydLootEntry::new));
}