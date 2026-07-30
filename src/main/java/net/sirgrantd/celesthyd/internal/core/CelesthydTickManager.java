package net.sirgrantd.celesthyd.internal.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.sirgrantd.celesthyd.CelesthydLib;

@EventBusSubscriber(modid = CelesthydLib.MOD_ID)
public class CelesthydTickManager {

    private static final ConcurrentLinkedQueue<TickTask> WORK_QUEUE = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tickDelay, Runnable action) {
        WORK_QUEUE.add(new TickTask(action, tickDelay));
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (WORK_QUEUE.isEmpty())
            return;

        List<TickTask> toRemove = new ArrayList<>();

        for (TickTask task : WORK_QUEUE) {
            task.tickDelay--;
            if (task.tickDelay <= 0) {
                toRemove.add(task);
                task.action.run();
            }
        }

        WORK_QUEUE.removeAll(toRemove);
    }

    private static class TickTask {
        private final Runnable action;
        private int tickDelay;

        public TickTask(Runnable action, int tickDelay) {
            this.action = action;
            this.tickDelay = tickDelay;
        }
    }
}
