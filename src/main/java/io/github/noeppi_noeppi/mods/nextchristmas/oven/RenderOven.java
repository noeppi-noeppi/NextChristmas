package io.github.noeppi_noeppi.mods.nextchristmas.oven;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.noeppi_noeppi.libx.block.tesr.HorizontalRotatedTesr;
import io.github.noeppi_noeppi.mods.nextchristmas.ModModels;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class RenderOven extends HorizontalRotatedTesr<TileOven> {

    public static final ResourceLocation DOOR_MODEL = new ResourceLocation(NextChristmas.getInstance().modid, "block/oven_door");

    public RenderOven(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
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
    }
}
