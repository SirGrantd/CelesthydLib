package net.sirgrantd.celesthyd.internal.network.toast;

import net.minecraft.client.Minecraft;

public class ToastHelper {
    public static void display(ShowToastPayload p) {
        Minecraft.getInstance().getToasts().addToast(
                new CelesthydToast(p.title(), p.subtitle(), p.icon()));
    }
}