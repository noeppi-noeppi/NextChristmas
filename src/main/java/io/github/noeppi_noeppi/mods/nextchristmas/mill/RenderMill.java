package io.github.noeppi_noeppi.mods.nextchristmas.mill;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.noeppi_noeppi.libx.block.tesr.HorizontalRotatedTesr;
import io.github.noeppi_noeppi.libx.render.ClientTickHandler;
import io.github.noeppi_noeppi.mods.nextchristmas.ModModels;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class RenderMill extends HorizontalRotatedTesr<TileMill> {

    public static final ResourceLocation CRANK_MODEL = new ResourceLocation(NextChristmas.getInstance().modid, "block/grain_mill_crank");

    public RenderMill(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected void doRender(@Nonnull TileMill tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        matrixStack.push();

        if (tile.getMillTicks() > 0) {
            matrixStack.translate(8 / 16d, 12 / 16d, 0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(360 * Math.min(TileMill.MILL_TICKS_MAX, tile.getMillTicks() + partialTicks) / TileMill.MILL_TICKS_MAX));
            matrixStack.translate(-8 / 16d, -12 / 16d, 0);
        }

        IVertexBuilder vertexBuffer = buffer.getBuffer(RenderTypeLookup.func_239220_a_(tile.getBlockState(), false));
        //noinspection deprecation
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
                .renderModelBrightnessColor(matrixStack.getLast(), vertexBuffer, tile.getBlockState(),
                        ModModels.getModel(CRANK_MODEL), 1, 1, 1, light, OverlayTexture.NO_OVERLAY);

        matrixStack.pop();

        ItemStack renderStack = tile.getInventory().getStackInSlot(TileMill.SLOT_IN);
        if (tile.getRecipe() != null && tile.getProgress() > 0.5) {
            renderStack = tile.getRecipe().getRecipeOutput();
        }

        matrixStack.push();
        matrixStack.translate(0.5, ((14/16d) * (1 - tile.getProgress())) + (4/16d), 0.5);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(ClientTickHandler.ticksInGame + partialTicks));
        if (tile.getProgress() > 0.2f) {
            matrixStack.scale(0.4f, 0.4f, 0.4f);
        } else {
            matrixStack.scale(0.7f, 0.7f, 0.7f);
        }
        matrixStack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        Minecraft.getInstance().getItemRenderer().renderItem(renderStack, ItemCameraTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.translate(0.5, 2 / 16d, 0.35);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(30));
        matrixStack.scale(0.8f, 0.8f, 0.8f);
        Minecraft.getInstance().getItemRenderer().renderItem(tile.getInventory().getStackInSlot(TileMill.SLOT_OUT), ItemCameraTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        matrixStack.pop();
    }
}
