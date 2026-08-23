package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class CelesthydBaseScreen<T extends CelesthydBaseMenu> extends AbstractContainerScreen<T> {

    private final ResourceLocation backgroundTexture;

    private final int textureWidth;
    private final int textureHeight;

    private boolean renderDefaultLabels = true;

    public CelesthydBaseScreen(T menu, Inventory playerInventory, Component title, ResourceLocation backgroundTexture,
            int windowWidth, int windowHeight, int textureWidth, int textureHeight) {
        super(menu, playerInventory, title);
        this.imageWidth = windowWidth;
        this.imageHeight = windowHeight;
        this.backgroundTexture = backgroundTexture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void setRenderDefaultLabels(boolean render) {
        this.renderDefaultLabels = render;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                this.backgroundTexture,
                this.leftPos,
                this.topPos,
                0, 0,
                this.imageWidth,
                this.imageHeight,
                this.textureWidth,
                this.textureHeight
        );
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.renderDefaultLabels) {
            super.renderLabels(guiGraphics, mouseX, mouseY);
        }
    }
}
