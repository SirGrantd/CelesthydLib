package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class CelesthydButton extends ImageButton {

    private final AbstractContainerScreen<?> parentGui;
    private final int xOffset;
    private final int yOffset;
    private final CelesthydButtonAction leftClickAction;
    private final CelesthydButtonAction rightClickAction;

    private final Supplier<SoundEvent> clickSoundSupplier;

    private final Supplier<Component> tooltipText;
    private final Supplier<Component> tooltipLongText;

    public CelesthydButton(
            AbstractContainerScreen<?> parentGui,
            int xOffset,
            int yOffset,
            int xIn,
            int yIn,
            int widthIn,
            int heightIn,
            WidgetSprites sprites,
            CelesthydButtonAction leftClickAction,
            CelesthydButtonAction rightClickAction,
            Supplier<SoundEvent> clickSoundSupplier,
            Supplier<Component> tooltipText,
            Supplier<Component> tooltipTextControl) {
        super(xIn, yIn, widthIn, heightIn, sprites, (btn) -> {
        });

        this.parentGui = parentGui;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.leftClickAction = leftClickAction;
        this.rightClickAction = rightClickAction;
        this.clickSoundSupplier = clickSoundSupplier;

        this.tooltipText = tooltipText != null ? tooltipText : () -> Component.literal("");
        this.tooltipLongText = tooltipTextControl != null ? tooltipTextControl : () -> tooltipText.get();

        this.setTooltip(Tooltip.create(tooltipText.get()));
    }

    @Override
    public void playDownSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(clickSoundSupplier.get(), 1.25F));
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.setX(parentGui.getGuiLeft() + xOffset);
        this.setY(parentGui.getGuiTop() + yOffset);

        if (Screen.hasControlDown()) {
            this.setTooltip(Tooltip.create(this.tooltipLongText.get()));
        } else {
            this.setTooltip(Tooltip.create(this.tooltipText.get()));
        }

        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isActive() || !this.visible || !this.isMouseOver(mouseX, mouseY)) {
            return false;
        }

        CelesthydButtonAction actionToRun = switch (button) {
            case 0 -> this.leftClickAction;
            case 1 -> this.rightClickAction;
            default -> null;
        };

        if (actionToRun != null) {
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            actionToRun.sendToServer(this.parentGui.getMinecraft().player, Screen.hasShiftDown());
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
