package net.sirgrantd.celesthyd.test;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sirgrantd.celesthyd.api.network.ISyncableAttachment;

public class TestAttachment implements ISyncableAttachment {

    private int counter;

    public TestAttachment() {
        this.counter = 0;
    }

    public TestAttachment(int counter) {
        this.counter = counter;
    }

    public void addCounter(int amount) {
        this.counter += amount;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void syncToClient(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new SyncTestAttachmentPayload(this.counter));
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("counter", this.counter);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.counter = tag.getInt("counter");
    }
}
