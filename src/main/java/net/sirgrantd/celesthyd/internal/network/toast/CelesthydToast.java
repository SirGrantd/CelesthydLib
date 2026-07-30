package net.sirgrantd.celesthyd.internal.network.toast;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class CelesthydToast implements Toast {

    private final Component title;
    private final Component subtitle;
    private final Optional<Identifier> icon;
    private long firstDrawTime;

    public CelesthydToast(Component title, Component subtitle, Optional<Identifier> icon) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.firstDrawTime = 0L;
    }

    @Override
    public Toast.Visibility getWantedVisibility() {
        if (this.firstDrawTime == 0L) {
            return Toast.Visibility.SHOW;
        }

        return (System.currentTimeMillis() - this.firstDrawTime) >= 5000L ? Toast.Visibility.HIDE
                : Toast.Visibility.SHOW;
    }

    @Override
    public void update(ToastManager toastManager, long time) {
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, Font font, long time) {
        if (this.firstDrawTime == 0L) {
            this.firstDrawTime = System.currentTimeMillis();
        }

        extractor.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.withDefaultNamespace("toast/advancement"), 0, 0,
                this.width(), this.height());

        extractor.text(font, this.title, 30, 7, 0xFFFFFFFF, false);
        extractor.text(font, this.subtitle, 30, 18, 0xFFAAAAAA, false);

        this.icon.ifPresent(id -> {
            extractor.blit(RenderPipelines.GUI_TEXTURED, id, 8, 8, 0, 0, 16, 16, 16, 16);
        });
    }
}