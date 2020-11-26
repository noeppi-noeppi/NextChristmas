package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class SledgeRender extends EntityRenderer<Sledge> {

    protected final SledgeModel model = new SledgeModel();
    protected final SledgeElytraModel wings = new SledgeElytraModel();

    public SledgeRender(EntityRendererManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.6F;
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull Sledge entity) {
        return entity.getSledgeType().texture;
    }

    @Override
    public void render(@Nonnull Sledge sledge, float entityYaw, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light) {
        matrixStack.push();
        matrixStack.translate(0.0D, 0.375D, 0.0D);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        float timeSinceHit = sledge.getTimeSinceHit() - partialTicks;
        float damage = sledge.getDamageTaken() - partialTicks;
        if (damage < 0.0F) {
            damage = 0.0F;
        }

        if (timeSinceHit > 0.0F) {
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.sin(timeSinceHit) * timeSinceHit * damage / 10 * sledge.getForwardDirection()));
        }

        matrixStack.translate(0, 2.625, 0);
        matrixStack.scale(2, -2, 2);

        matrixStack.push();
        this.model.setRotationAngles(sledge, partialTicks, 0, sledge.ticksExisted, 0, 0);
        IVertexBuilder vertex = buffer.getBuffer(this.model.getRenderType(this.getEntityTexture(sledge)));
        this.model.render(matrixStack, vertex, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrixStack.pop();

        if (sledge.isWinged()) {
            matrixStack.push();
            matrixStack.translate(0, 1.2, 0);
            if (sledge.isFlying()) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
                matrixStack.scale(0.5f, 0.5f, 0.5f);
            } else {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(75));
                matrixStack.scale(0.3f, 0.3f, 0.3f);
            }
            this.wings.setRotationAngles(sledge, partialTicks, 0, sledge.ticksExisted, 0, 0);
            vertex = buffer.getBuffer(this.wings.getRenderType(ElytraLayer.TEXTURE_ELYTRA));
            this.wings.render(matrixStack, vertex, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            matrixStack.pop();
        }

        if (sledge.isChested()) {
            matrixStack.push();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
            matrixStack.translate(-0.2, 1.3, -0.63);
            matrixStack.scale(0.4f, -0.4f, 0.4f);
            //noinspection deprecation
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(Blocks.CHEST.getDefaultState(), matrixStack, buffer, light, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }

        matrixStack.pop();

        super.render(sledge, entityYaw, partialTicks, matrixStack, buffer, light);
    }
}
