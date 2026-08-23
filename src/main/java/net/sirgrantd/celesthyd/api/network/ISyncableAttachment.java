package net.sirgrantd.celesthyd.api.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface ISyncableAttachment extends INBTSerializable<CompoundTag> {
    void syncToClient(ServerPlayer player);
}