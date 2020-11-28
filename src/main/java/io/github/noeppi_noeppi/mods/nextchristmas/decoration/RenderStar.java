package io.github.noeppi_noeppi.mods.nextchristmas.decoration;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.noeppi_noeppi.libx.block.tesr.HorizontalRotatedTesr;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import io.github.noeppi_noeppi.libx.render.ClientTickHandler;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;
import java.awt.*;

public class RenderStar extends HorizontalRotatedTesr<TileEntityBase> {

    public static final RenderType STAR_TYPE = RenderType.getEntitySolid(new ResourceLocation("libx", "textures/white.png"));

    public RenderStar(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected void doRender(@Nonnull TileEntityBase tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        matrixStack.push();
        matrixStack.translate(0.5, 1, 0);
        matrixStack.rotate(Vector3f.ZP.rotationDegrees((float) (10 * Math.sin(tile.getPos().getX() + (0.78237825 * tile.getPos().getZ()) + (1.18508472 * tile.getPos().getZ()) + (ClientTickHandler.ticksInGame + partialTicks) / 29))));
        matrixStack.translate(-0.5, -1, 0);

        int color1 = Color.HSBtoRGB((44 + (10 * (float) Math.sin(tile.getPos().getZ() + (0.97234293 * tile.getPos().getX()) + (1.06225672 * tile.getPos().getZ()) + (ClientTickHandler.ticksInGame + partialTicks) / 19))) / 360f, 93/100f, 97/100f);
        float r1 = (color1 >>> 16 & 255) / 255f;
        float g1 = (color1 >>> 8 & 255) / 255f;
        float b1 = (color1 & 255) / 255f;

        int color2 = Color.HSBtoRGB((44 + (10 * (float) -Math.sin(tile.getPos().getZ() + (0.97234293 * tile.getPos().getX()) + (1.06225672 * tile.getPos().getZ()) + (ClientTickHandler.ticksInGame + partialTicks) / 19))) / 360f, 93/100f, 97/100f);
        float r2 = (color2 >>> 16 & 255) / 255f;
        float g2 = (color2 >>> 8 & 255) / 255f;
        float b2 = (color2 & 255) / 255f;

        matrixStack.translate(0.5, 0.5, 0.5);
        matrixStack.scale(0.25f, 0.25f, 0.25f);

        for (int i = 0; i < 5; i++) {
            this.renderStarPart(matrixStack, buffer, 15728880, OverlayTexture.NO_OVERLAY, r1, g1, b1, r2, g2, b2);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(360/5f));
        }

        matrixStack.pop();
    }

    @SuppressWarnings("SameParameterValue")
    private void renderStarPart(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay, float r1, float g1, float b1, float r2, float g2, float b2) {

        Matrix4f model = matrixStack.getLast().getMatrix();
        Matrix3f normal = matrixStack.getLast().getNormal();

        float ys = (float) Math.cos(Math.toRadians(360/5d));
        float xs = (float) -Math.sin(Math.toRadians(360/5d));
        float z = 0.5f;
        float h = 3;

        IVertexBuilder vertex = buffer.getBuffer(STAR_TYPE);
        vertex.pos(model, 0, 0, z).color(r1, g1, b1, 1).tex(0, 0).overlay(overlay).lightmap(light).normal(normal, 0, 0, z).endVertex();
        vertex.pos(model, 0, h, 0).color(r1, g1, b1, 1).tex(0, 1).overlay(overlay).lightmap(light).normal(normal, 0, h, 0).endVertex();
        vertex.pos(model, xs, ys, 0).color(r2, g2, b2, 1).tex(1, 0).overlay(overlay).lightmap(light).normal(normal, xs, ys, 0).endVertex();
        vertex.pos(model, xs, ys, 0).color(r2, g2, b2, 1).tex(1, 1).overlay(overlay).lightmap(light).normal(normal, xs, xs, 0).endVertex();

        vertex = buffer.getBuffer(STAR_TYPE);
        vertex.pos(model, 0, h, 0).color(r1, g1, b1, 1).tex(0, 1).overlay(overlay).lightmap(light).normal(normal, 0, h, 0).endVertex();
        vertex.pos(model, 0, 0, z).color(r1, g1, b1, 1).tex(0, 0).overlay(overlay).lightmap(light).normal(normal, 0, 0, z).endVertex();
        vertex.pos(model, -xs, ys, 0).color(r2, g2, b2, 1).tex(1, 0).overlay(overlay).lightmap(light).normal(normal, -xs, ys, 0).endVertex();
        vertex.pos(model, -xs, ys, 0).color(r2, g2, b2, 1).tex(1, 1).overlay(overlay).lightmap(light).normal(normal, -xs, ys, 0).endVertex();

        vertex = buffer.getBuffer(STAR_TYPE);
        vertex.pos(model, 0, h, 0).color(r1, g1, b1, 1).tex(0, 1).overlay(overlay).lightmap(light).normal(normal, 0, h, 0).endVertex();
        vertex.pos(model, 0, 0, -z).color(r1, g1, b1, 1).tex(0, 0).overlay(overlay).lightmap(light).normal(normal, 0, 0, -z).endVertex();
        vertex.pos(model, xs, ys, 0).color(r2, g2, b2, 1).tex(1, 0).overlay(overlay).lightmap(light).normal(normal, xs, ys, 0).endVertex();
        vertex.pos(model, xs, ys, 0).color(r2, g2, b2, 1).tex(1, 1).overlay(overlay).lightmap(light).normal(normal, xs, ys, 0).endVertex();

        vertex = buffer.getBuffer(STAR_TYPE);
        vertex.pos(model, 0, 0, -z).color(r1, g1, b1, 1).tex(0, 0).overlay(overlay).lightmap(light).normal(normal, 0, 0, -z).endVertex();
        vertex.pos(model, 0, h, 0).color(r1, g1, b1, 1).tex(0, 1).overlay(overlay).lightmap(light).normal(normal, 0, h, 0).endVertex();
        vertex.pos(model, -xs, ys, 0).color(r2, g2, b2, 1).tex(1, 0).overlay(overlay).lightmap(light).normal(normal, -xs, ys, 0).endVertex();
        vertex.pos(model, -xs, ys, 0).color(r2, g2, b2, 1).tex(1, 1).overlay(overlay).lightmap(light).normal(normal, -xs, ys, 0).endVertex();
    }
}
