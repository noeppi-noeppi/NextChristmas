package io.github.noeppi_noeppi.mods.nextchristmas.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.noeppi_noeppi.mods.nextchristmas.ModItems;
import io.github.noeppi_noeppi.mods.nextchristmas.ModModels;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderSantaHat extends ItemStackTileEntityRenderer {

    public static final RenderSantaHat INSTANCE = new RenderSantaHat();

    public static final ResourceLocation ITEM_MODEL = new ResourceLocation(NextChristmas.getInstance().modid, "item/santa_hat_model");
    public static final ResourceLocation HAT_MODEL = new ResourceLocation(NextChristmas.getInstance().modid, "player/santa_hat");

    private RenderSantaHat() {

    }

    @Override
    public void func_239207_a_(@Nonnull ItemStack stack, @Nonnull ItemCameraTransforms.TransformType type, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        if (stack.getItem() == ModItems.santaHat) {
            if (type != ItemCameraTransforms.TransformType.HEAD && type != ItemCameraTransforms.TransformType.GROUND) {
                RenderType renderType = RenderTypeLookup.func_239219_a_(stack, true);
                IVertexBuilder vertex = ItemRenderer.getEntityGlintVertexBuilder(buffer, renderType, true, stack.hasEffect());
                Minecraft.getInstance().getItemRenderer().renderModel(ModModels.getModel(ITEM_MODEL), stack, light, type == ItemCameraTransforms.TransformType.GUI ? OverlayTexture.NO_OVERLAY : overlay, matrixStack, vertex);
            } else {
                if (type == ItemCameraTransforms.TransformType.HEAD) {
                    matrixStack.translate(0, 0.55, 0);
                } else {
                    matrixStack.translate(0.5, 0.5, 0.5);
                    matrixStack.scale(0.3f, 0.3f, 0.3f);
                    matrixStack.translate(-0.5, 0, -0.5);
                }
                IVertexBuilder vertex = buffer.getBuffer(RenderType.getSolid());
                //noinspection deprecation
                Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
                        .renderModelBrightnessColor(matrixStack.getLast(), vertex, null,
                                ModModels.getModel(HAT_MODEL), 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}
