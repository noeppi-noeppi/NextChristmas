package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.libx.render.RenderHelper;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class ScreenOven extends ContainerScreen<ContainerOven> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(NextChristmas.getInstance().modid, "textures/gui/oven.png");

    public ScreenOven(ContainerOven container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderHelper.resetColor();
        //noinspection ConstantConditions
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        this.blit(matrixStack, (this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);

        if (this.container.tile.getBurnTime() > 0) {
            int burnTime = Math.max(1, Math.round(14 * this.container.tile.getBurnTime()));
            this.blit(matrixStack, this.guiLeft + 23, this.guiTop + 48 - burnTime, 176, 14 - burnTime, 14, burnTime);
        }

        if (this.container.tile.getCookTime1() > 0) {
            int cookTime = Math.max(1, Math.round(22 * this.container.tile.getCookTime1()));
            this.blit(matrixStack, this.guiLeft + 80, this.guiTop + 20, 176, 14, cookTime, 16);
        }

        if (this.container.tile.getCookTime2() > 0) {
            int cookTime = Math.max(1, Math.round(22 * this.container.tile.getCookTime2()));
            this.blit(matrixStack, this.guiLeft + 80, this.guiTop + 51, 176, 14, cookTime, 16);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int x, int y) {
        String title = this.title.getString();
        this.font.drawString(matrixStack, title, (float) (this.xSize / 2 - this.font.getStringWidth(title) / 2), 5, 0x404040);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8, 74, 0x404040);
    }
}
