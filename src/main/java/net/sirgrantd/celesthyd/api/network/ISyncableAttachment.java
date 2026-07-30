package net.sirgrantd.celesthyd.api.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

public interface ISyncableAttachment extends ValueIOSerializable {
    void syncToClient(ServerPlayer player);
}