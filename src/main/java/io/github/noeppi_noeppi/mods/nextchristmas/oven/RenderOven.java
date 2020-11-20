package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.noeppi_noeppi.libx.block.tesr.HorizontalRotatedTesr;
import io.github.noeppi_noeppi.mods.nextchristmas.ModModels;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import io.github.noeppi_noeppi.mods.nextchristmas.biscuit.BlockBakingSheet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class RenderOven extends HorizontalRotatedTesr<TileOven> {

    public static final ResourceLocation DOOR_MODEL = new ResourceLocation(NextChristmas.getInstance().modid, "block/oven_door");

    public RenderOven(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    protected void doRender(@Nonnull TileOven tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        matrixStack.push();

        if (tile.getOpenTicks() > 0) {
            float pticks = tile.getOpenTicks() >= 20 ? 0 : partialTicks;
            if (tile.getPlayersWatching() <= 0) {
                pticks = -pticks;
            }
            matrixStack.translate(0, 1.25 / 16d / 4, 12.5 / 16d / 4);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-3 * (tile.getOpenTicks() + pticks)));
            matrixStack.translate(0, -1.25 / 16d / 4, -12.5 / 16d / 4);
        }

        IVertexBuilder vertexBuffer = buffer.getBuffer(RenderTypeLookup.func_239220_a_(tile.getBlockState(), false));
        //noinspection deprecation
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
                .renderModelBrightnessColor(matrixStack.getLast(), vertexBuffer, tile.getBlockState(),
                        ModModels.getModel(DOOR_MODEL), 1, 1, 1, light, OverlayTexture.NO_OVERLAY);

        matrixStack.pop();

        matrixStack.push();
        matrixStack.translate(0, 4/16d, 0);

        renderContent(tile.getInventory(), TileOven.SLOT_IN2, TileOven.SLOT_OUT2, matrixStack, buffer, partialTicks, light, overlay);

        matrixStack.translate(0, 3/16d, 0);

        renderContent(tile.getInventory(), TileOven.SLOT_IN1, TileOven.SLOT_OUT1, matrixStack, buffer, partialTicks, light, overlay);

        matrixStack.pop();
    }

    private static void renderContent(IItemHandlerModifiable inv, int slotIn, int slotOut, MatrixStack matrixStack, IRenderTypeBuffer buffer, float partialTicks, int light, int overlay) {
        ItemStack stack = inv.getStackInSlot(slotOut);
        if (stack.isEmpty()) {
            stack = inv.getStackInSlot(slotIn);
        }
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof BlockItem) {
                matrixStack.push();

                Block block = ((BlockItem) stack.getItem()).getBlock();
                if (block instanceof BlockBakingSheet) {
                    ItemStackTileEntityRenderer teisr = stack.getItem().getItemStackTileEntityRenderer();
                    if (teisr == ItemStackTileEntityRenderer.instance) {
                        //noinspection deprecation
                        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(block.getDefaultState(), matrixStack, buffer, light, overlay);
                    } else {
                        teisr.func_239207_a_(stack, ItemCameraTransforms.TransformType.NONE, matrixStack, buffer, light, overlay);
                    }
                } else {
                    matrixStack.translate(3/16d, 0, 1/16d);
                    matrixStack.scale(10/16f, 10/16f, 10/16f);

                    //noinspection deprecation
                    Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(block.getDefaultState(), matrixStack, buffer, light, overlay);
                }

                matrixStack.pop();
            } else {
                matrixStack.push();
                matrixStack.translate(6 / 16d, 0.5 / 16d, 9 / 16d);
                matrixStack.rotate(new Quaternion(90, 0, -90, true));
                matrixStack.scale(1.3f, 1.3f, 1.3f);

                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, matrixStack, buffer);

                matrixStack.pop();
            }
        }
    }
}
