package net.sirgrantd.celesthyd.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.sirgrantd.celesthyd.api.gui.CelesthydBaseScreen;
import net.sirgrantd.celesthyd.api.gui.CelesthydText;

public class TestGuiScreen extends CelesthydBaseScreen<TestGuiMenu> {

    // Usaremos a textura padrão de um Dispenser para fins de teste
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/dispenser.png");

    public TestGuiScreen(TestGuiMenu menu, Inventory playerInventory, Component title) {
        // windowWidth=176, windowHeight=166, textureWidth=256, textureHeight=256
        super(menu, playerInventory, title, TEXTURE, 176, 166, 256, 256);

        this.setRenderDefaultLabels(false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // A MÁGICA: Pegamos o valor da variável que o servidor enviou usando nossa
        // classe genérica
        int energia = this.menu.getData("energia");

        // Renderizamos o texto usando os utilitários da sua biblioteca
        CelesthydText energiaTexto = new CelesthydText(this, 50, 30, "§eEnergia: " + energia);
        energiaTexto.render(guiGraphics);
    }
}
