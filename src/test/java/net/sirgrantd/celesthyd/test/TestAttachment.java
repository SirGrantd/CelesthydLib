package net.sirgrantd.celesthyd.test;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sirgrantd.celesthyd.api.network.ISyncableAttachment;

public class TestAttachment implements ISyncableAttachment {

    private int counter;

    public TestAttachment() {
        this.counter = 0;
    }

    public void addCounter(int amount) {
        this.counter += amount;
    }

    public int getCounter() {
        return this.counter;
    }

    @Override
    public void syncToClient(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new SyncTestAttachmentPayload(this.counter));
    }

    @Override
    public void serialize(ValueOutput output) {
        output.putInt("counter", this.counter);
    }

    @Override
    public void deserialize(ValueInput input) {
        this.counter = input.getIntOr("counter", 0);
    }
}