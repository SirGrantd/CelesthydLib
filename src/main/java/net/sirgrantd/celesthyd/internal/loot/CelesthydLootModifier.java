package net.sirgrantd.celesthyd.internal.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.sirgrantd.celesthyd.api.loot.CelesthydLootEntry;

import java.util.List;
import java.util.Random;

public record CelesthydLootModifier(List<LootItemCondition> conditions, List<CelesthydLootEntry> entries,
        boolean rollAll) implements IGlobalLootModifier {

    public static final MapCodec<CelesthydLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of())
                    .forGetter(CelesthydLootModifier::conditions),
            CelesthydLootEntry.CODEC.codec().listOf().fieldOf("items").forGetter(CelesthydLootModifier::entries),
            Codec.BOOL.optionalFieldOf("roll_all", true).forGetter(CelesthydLootModifier::rollAll))
            .apply(inst, CelesthydLootModifier::new));

    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition : conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }

        if (entries.isEmpty())
            return generatedLoot;

        Random random = new Random();

        if (rollAll) {
            for (CelesthydLootEntry entry : entries) {
                rollAndAdd(entry, generatedLoot, random);
            }
        } else {
            CelesthydLootEntry entry = entries.get(random.nextInt(entries.size()));
            rollAndAdd(entry, generatedLoot, random);
        }

        return generatedLoot;
    }

    private void rollAndAdd(CelesthydLootEntry entry, ObjectArrayList<ItemStack> loot, Random random) {
        if (random.nextFloat() <= entry.chance()) {
            int count = entry.min() + random.nextInt(entry.max() - entry.min() + 1);
            loot.add(new ItemStack(entry.item(), count));
        }
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    public int priority() {
        return 0;
    }
}