package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public abstract class CelesthydBaseScreen<T extends CelesthydBaseMenu> extends AbstractContainerScreen<T> {

    private final Identifier backgroundTexture;

    // NOVO: Variáveis para guardar a resolução real do arquivo PNG
    private final int textureWidth;
    private final int textureHeight;

    private boolean renderDefaultLabels = true;

    // NOVO: Construtor agora pede os tamanhos da Janela E os tamanhos do Arquivo
    // separadamente
    public CelesthydBaseScreen(T menu, Inventory playerInventory, Component title, Identifier backgroundTexture,
            int windowWidth, int windowHeight, int textureWidth, int textureHeight) {
        super(menu, playerInventory, title, windowWidth, windowHeight);
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
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);

        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                this.backgroundTexture,
                this.leftPos,
                this.topPos,
                0, 0,
                this.imageWidth, // Tamanho da caixa centralizada na tela
                this.imageHeight, // Tamanho da caixa centralizada na tela
                this.textureWidth, // Resolução real do arquivo PNG (ex: 256)
                this.textureHeight // Resolução real do arquivo PNG (ex: 256)
        );
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (this.renderDefaultLabels) {
            super.extractLabels(graphics, mouseX, mouseY);
        }
    }
}
