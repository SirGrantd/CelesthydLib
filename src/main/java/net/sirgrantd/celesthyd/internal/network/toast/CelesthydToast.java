package net.sirgrantd.celesthyd.internal.network.toast;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class CelesthydToast implements Toast {

    private final Component title;
    private final Component subtitle;
    private final Optional<ResourceLocation> icon;

    public CelesthydToast(Component title, Component subtitle, Optional<ResourceLocation> icon) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        guiGraphics.blitSprite(ResourceLocation.withDefaultNamespace("toast/advancement"), 0, 0,
                this.width(), this.height());

        guiGraphics.drawString(toastComponent.getMinecraft().font, this.title, 30, 7, 0xFFFFFFFF, false);
        guiGraphics.drawString(toastComponent.getMinecraft().font, this.subtitle, 30, 18, 0xFFAAAAAA, false);

        this.icon.ifPresent(id -> {
            guiGraphics.blit(id, 8, 8, 0, 0, 16, 16, 16, 16);
        });

        return timeSinceLastVisible >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }
}