package io.github.noeppi_noeppi.mods.nextchristmas.biscuit;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.libx.block.tesr.HorizontalRotatedTesr;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;

import javax.annotation.Nonnull;

public class RenderBakingSheet extends HorizontalRotatedTesr<TileEntityBase> {

    private final BlockBakingSheet block;

    public RenderBakingSheet(TileEntityRendererDispatcher rendererDispatcher, BlockBakingSheet block) {
        super(rendererDispatcher);
        this.block = block;
    }

    @Override
    protected void doRender(@Nonnull TileEntityBase tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        ItemStack fillStack = this.block.getFillStack();
        IBakedModel model = this.block.getFillModel(tile.getWorld());
        if (!fillStack.isEmpty()) {
            if (this.block.isBig()) {
                matrixStack.push();// -2 +0.25 +2
                matrixStack.translate(6 / 16d, 0.5 / 16d, 9 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(1.3f, 1.3f, 1.3f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();
            } else {
                matrixStack.push();
                matrixStack.translate(3 / 16d, 0.5 / 16d, 6.5 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(0.55f, 0.6f, 0.55f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();

                matrixStack.push();
                matrixStack.translate(7 / 16d, 0.5 / 16d, 6.5 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(0.55f, 0.6f, 0.55f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();

                matrixStack.push();
                matrixStack.translate(11 / 16d, 0.5 / 16d, 6.5 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(0.55f, 0.6f, 0.55f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();

                matrixStack.push();
                matrixStack.translate(3 / 16d, 0.5 / 16d, 11.5 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(0.55f, 0.6f, 0.55f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();

                matrixStack.push();
                matrixStack.translate(7 / 16d, 0.5 / 16d, 11.5 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(0.55f, 0.6f, 0.55f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();

                matrixStack.push();
                matrixStack.translate(11 / 16d, 0.5 / 16d, 11.5 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(0.55f, 0.6f, 0.55f);
                Minecraft.getInstance().getItemRenderer().renderItem(fillStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, model);
                matrixStack.pop();
            }
        }
    }
}